package com.github.japgolly.android.test.param;

import java.util.List;

import org.junit.param.ParamTest;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Allows parameterised tests (via {@link ParamTest}). Originally copied from JBrisk.
 * 
 * @since 05/12/2012
 * @see http://codemadesimple.wordpress.com/2012/01/17/paramtest_with_junit/
 */
public class ParameterisedTestRunner extends BlockJUnit4ClassRunner {

	public ParameterisedTestRunner(Class<?> clazz) throws InitializationError {
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
