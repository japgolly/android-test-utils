package com.github.japgolly.android.test.param;

import java.lang.reflect.Method;

import org.junit.param.Params;
import org.junit.runners.model.FrameworkMethod;

public class ParameterizedFrameworkMethod extends FrameworkMethod {

	private String methodName;
	private String[] untypedArgs;

	public ParameterizedFrameworkMethod(Method method, String[] untypedArgs) {

		super(method);

		if (method == null)
			throw new NullArgumentException("method");

		if (untypedArgs == null)
			throw new NullArgumentException("untypedArgs");

		if (untypedArgs.length == 0)
			throw new IndexOutOfBoundsException("The untyped arguments array cannot be empty!");

		this.untypedArgs = untypedArgs;

		StringBuilder sbName = new StringBuilder();

		sbName.append(method.getName());
		sbName.append("[ ");

		sbName.append(getArgPrintableValue(untypedArgs[0]));

		for (int i = 1; i < untypedArgs.length; i++) {

			sbName.append(", ");
			sbName.append(getArgPrintableValue(untypedArgs[i]));
		}

		sbName.append(" ]");

		methodName = sbName.toString();
	}

	private String getArgPrintableValue(String arg) {

		String printableValue = arg;

		if (arg != null) {

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < arg.length(); i++) {

				char ch = arg.charAt(i);

				switch (ch) {

				case '\n':
					sb.append("\\n");
					break;

				case '\r':
					sb.append("\\r");
					break;

				case '\t':
					sb.append("\\t");
					break;

				case '\b':
					sb.append("\\b");
					break;

				case '\f':
					sb.append("\\f");
					break;

				case '\'':
					sb.append("\\'");
					break;

				case '\"':
					sb.append("\\\"");
					break;

				case '\\':
					sb.append("\\\\");
					break;

				default:
					sb.append(ch);
					break;
				}
			}

			printableValue = sb.toString();
		}

		return printableValue;
	}

	@Override
	public String getName() {

		return methodName;
	}

	/**
	 * Returns the {@link String} array of untyped arguments supplied at the constructor.
	 * 
	 * @return {@link String} array of untyped arguments.
	 */
	public String[] getUntypedArgs() {

		return untypedArgs;
	}

	/**
	 * Returns an {@link Object} array with the supplied arguments converted to their respective types. Their types are
	 * extracted from the corresponding method parameters.
	 * 
	 * @return {@link Object} array.
	 * @throws ClassNotFoundException If the supplied class name for a {@link Class} argument cannot be found!
	 * @throws IllegalArgumentException If the method supplied at the constructor does not accept arguments, if the
	 *             arguments and the parameters length does not match and if any argument cannot be converted to the
	 *             expected type.
	 */
	public Object[] getTypedArgs() throws ClassNotFoundException {

		Method method = getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		int paramsLength = parameterTypes.length;

		if (paramsLength == 0)
			throw new IllegalArgumentException("The method \"" + method.getName() + "\" does not accept arguments!");

		int argumentsLength = untypedArgs.length;

		if (paramsLength != argumentsLength) {

			throw new IllegalArgumentException("The method \"" + method.getName() + "\" parameters length is \""
					+ paramsLength + "\" but the supplied arguments length is \"" + argumentsLength + "\"!");
		}

		Object[] typedValues = new Object[paramsLength];

		for (int i = 0; i < untypedArgs.length; i++) {

			String strValue = untypedArgs[i];
			Class<?> paramType = parameterTypes[i];

			if (Object.class.isAssignableFrom(paramType) && (strValue == null || strValue.equals(Params.NULL))) {

				typedValues[i] = null;

			} else {

				if (paramType.equals(long.class) || paramType.equals(Long.class)) {

					typedValues[i] = parseToLong(i, strValue);

				} else if (paramType.equals(short.class) || paramType.equals(Short.class)) {

					typedValues[i] = parseToShort(i, strValue);

				} else if (paramType.equals(int.class) || paramType.equals(Integer.class)) {

					typedValues[i] = parseToInt(i, strValue);

				} else if (paramType.equals(boolean.class) || paramType.equals(Boolean.class)) {

					typedValues[i] = parseToBoolean(i, strValue);

				} else if (paramType.equals(char.class) || paramType.equals(Character.class)) {

					typedValues[i] = parseToChar(i, strValue);

				} else if (paramType.equals(float.class) || paramType.equals(Float.class)) {

					typedValues[i] = parseToFloat(i, strValue);

				} else if (paramType.equals(double.class) || paramType.equals(Double.class)) {

					typedValues[i] = parseToDouble(i, strValue);

				} else if (paramType.equals(byte.class) || paramType.equals(Byte.class)) {

					typedValues[i] = parseToByte(i, strValue);

				} else if (paramType.equals(String.class)) {

					typedValues[i] = strValue;

				} else if (paramType.equals(Class.class)) {

					typedValues[i] = Class.forName(strValue);

				} else if (paramType.isArray()) {

					String[] splittedValues = strValue.split("\\|");

					if (paramType.equals(String[].class)) {

						typedValues[i] = splittedValues;

					} else if (paramType.equals(int[].class)) {

						typedValues[i] = parseToIntArray(splittedValues);

					} else if (paramType.equals(Integer[].class)) {

						typedValues[i] = parseToIntegerArray(splittedValues);

					} else if (paramType.equals(Boolean[].class)) {

						typedValues[i] = parseToBooleanArray(splittedValues);

					} else if (paramType.equals(Double[].class)) {

						typedValues[i] = parseToDoubleArray(splittedValues);
					}
				}
			}
		}

		return typedValues;
	}

	protected Boolean[] parseToBooleanArray(String[] splittedValues) {

		Boolean[] booleanArray = new Boolean[splittedValues.length];

		for (int j = 0; j < splittedValues.length; j++) {

			booleanArray[j] = parseToBoolean(j, splittedValues[j]);
		}

		return booleanArray;
	}

	protected Double[] parseToDoubleArray(String[] splittedValues) {

		Double[] doubleArray = new Double[splittedValues.length];

		for (int j = 0; j < splittedValues.length; j++) {

			doubleArray[j] = parseToDouble(j, splittedValues[j]);
		}

		return doubleArray;
	}

	protected Integer[] parseToIntegerArray(String[] splittedValues) {

		Integer[] integerArray = new Integer[splittedValues.length];

		for (int j = 0; j < splittedValues.length; j++) {

			integerArray[j] = parseToInt(j, splittedValues[j]);
		}
		return integerArray;
	}

	protected int[] parseToIntArray(String[] splittedValues) {

		int[] intArray = new int[splittedValues.length];

		for (int j = 0; j < splittedValues.length; j++) {

			intArray[j] = parseToInt(j, splittedValues[j]);
		}

		return intArray;
	}

	protected char parseToChar(int i, String strValue) {

		if (strValue != null && strValue.length() > 1)
			throw new IllegalArgumentException("The value \"" + strValue + "\" supplied for the argument at idx \"" + i
					+ "\" cannot be parsed to char because it contains more than one character!");

		try {

			return strValue.charAt(0);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "char", ex));
		}
	}

	protected boolean parseToBoolean(int i, String strValue) {

		try {

			return BooleanHelper.parse(strValue);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "boolean", ex));
		}
	}

	protected Byte parseToByte(int i, String strValue) {

		try {

			return Byte.parseByte(strValue);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "byte", ex));
		}
	}

	protected Double parseToDouble(int i, String strValue) {

		try {

			return Double.parseDouble(strValue);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "double", ex));
		}
	}

	protected Float parseToFloat(int i, String strValue) {

		try {

			return Float.parseFloat(strValue);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "float", ex));
		}
	}

	protected int parseToInt(int i, String strValue) {

		try {

			return Integer.parseInt(strValue);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "int", ex));
		}
	}

	protected long parseToLong(int i, String strValue) {

		try {

			return Long.parseLong(strValue);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "long", ex));
		}
	}

	protected short parseToShort(int i, String strValue) {

		try {

			return Short.parseShort(strValue);

		} catch (Exception ex) {

			throw new IllegalArgumentException(getCannotParseToTypeMessage(strValue, i, "short", ex));
		}
	}

	private String getCannotParseToTypeMessage(String strValue, int argIdx, String type, Exception parseEx) {

		throw new IllegalArgumentException("The value \"" + strValue + "\" supplied for the argument at idx \""
				+ argIdx + "\" cannot be parsed to " + type + "! Check the inner exception for details.", parseEx);
	}
}
