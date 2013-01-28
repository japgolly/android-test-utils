package com.github.japgolly.android.test.param;

import org.junit.runners.model.Statement;

public class ParameterizedInvokeMethod extends Statement {

	private final ParameterizedFrameworkMethod testMethod;
	private Object target;

	public ParameterizedInvokeMethod(ParameterizedFrameworkMethod testMethod, Object target) {

		if (testMethod == null)
			throw new NullArgumentException("testMethod");

		if (target == null)
			throw new NullArgumentException("target");

		this.testMethod = testMethod;
		this.target = target;
	}

	@Override
	public void evaluate() throws Throwable {

		testMethod.invokeExplosively(target, testMethod.getTypedArgs());
	}
}
