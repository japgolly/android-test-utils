package com.github.japgolly.android.test.param;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Pattern;

public class BooleanHelper {

	public static final String TRUE_BOOLEAN_REGEX = "^(1|true|yes|on)$";
	public static final String FALSE_BOOLEAN_REGEX = "^(0|false|no|off)$";

	public static final Pattern TRUE_BOOLEAN_PATTERN = Pattern.compile(TRUE_BOOLEAN_REGEX, CASE_INSENSITIVE);
	public static final Pattern FALSE_BOOLEAN_PATTERN = Pattern.compile(FALSE_BOOLEAN_REGEX, CASE_INSENSITIVE);

	public static boolean parse(String str) {

		if (str == null) {
			throw new NullArgumentException("str");
		}

		boolean ret = false;

		if (TRUE_BOOLEAN_PATTERN.matcher(str).matches()) {
			ret = true;
		} else if (!FALSE_BOOLEAN_PATTERN.matcher(str).matches()) {
			throw new IllegalArgumentException("The value \"" + str + "\" is not a valid boolean value!");
		}

		return ret;
	}
}