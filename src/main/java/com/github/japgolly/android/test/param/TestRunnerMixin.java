package com.github.japgolly.android.test.param;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.param.ParamTest;
import org.junit.param.Params;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Well... I wish this were a mixin.
 * 
 * @since 05/12/2012
 */
final class TestRunnerMixin {

	static public List<FrameworkMethod> computeTestMethods(TestClass testClass, List<FrameworkMethod> testMethods) {
		try {

			final List<FrameworkMethod> results = new ArrayList<FrameworkMethod>(testMethods.size() << 1);
			results.addAll(testMethods);

			final List<FrameworkMethod> paramTestMethods = testClass.getAnnotatedMethods(ParamTest.class);
			for (FrameworkMethod method : paramTestMethods) {

				final ParamTest paramTest = method.getAnnotation(ParamTest.class);
				final int sizeBefore = results.size();

				// Get @Params from static method specified in paramsProvidedBy
				String paramsMethodName = paramTest.paramsProvidedBy();
				if (!paramsMethodName.isEmpty()) {
					Method paramsMethod = testClass.getJavaClass().getMethod(paramsMethodName);
					if ((paramsMethod.getModifiers() & Modifier.STATIC) == 0) {
						throw new IllegalArgumentException("Method should be static: " + paramsMethod);
					}
					String[][] params = (String[][]) paramsMethod.invoke(null);
					createTestsForParams(results, method, params);
				}

				// Parse given @Params
				createTestsForParams(results, method, paramTest.value());

				// Confirm tests added
				if (sizeBefore == results.size()) {
					throw new IllegalStateException("No params found to test: " + method.getName() + "()");
				}
			}

			return results;

		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException(e);
		}
	}

	private static void createTestsForParams(final List<FrameworkMethod> results, FrameworkMethod method,
			String[][] params) {

		for (String[] param : params) {
			ParameterizedFrameworkMethod paramMethod = new ParameterizedFrameworkMethod(method.getMethod(), param);
			results.add(paramMethod);
		}
	}

	private static void createTestsForParams(final List<FrameworkMethod> results, FrameworkMethod method,
			Params[] params) {

		for (Params param : params) {
			ParameterizedFrameworkMethod paramMethod =
					new ParameterizedFrameworkMethod(method.getMethod(), param.value());
			results.add(paramMethod);
		}
	}

	static public Statement methodInvoker(FrameworkMethod method, Object test) {
		if (method instanceof ParameterizedFrameworkMethod) {
			return new ParameterizedInvokeMethod((ParameterizedFrameworkMethod) method, test);
		}
		return null;
	}

}
