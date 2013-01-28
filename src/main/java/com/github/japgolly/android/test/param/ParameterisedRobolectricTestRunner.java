package com.github.japgolly.android.test.param;

import java.util.List;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.param.ParamTest;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Allows parameterised tests (via {@link ParamTest}) in conjunction with Robolectric.
 * 
 * @since 05/12/2012
 */
public class ParameterisedRobolectricTestRunner extends RobolectricTestRunner {

	public ParameterisedRobolectricTestRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		return TestRunnerMixin.computeTestMethods(getTestClass(), super.computeTestMethods());
	}

	@Override
	protected Statement methodInvoker(FrameworkMethod method, Object test) {
		Statement stmt = TestRunnerMixin.methodInvoker(method, test);
		return (stmt != null) ? stmt : super.methodInvoker(method, test);
	}
}
