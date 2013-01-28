package org.junit.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// THIS NEEDS TO BE UNDER org.junit SO THAT ROBOLECTRIC DOESN'T RE-LOAD IT USING ITS CUSTOM CLASSLOADER

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamTest {

	Params[] value() default {};

	String paramsProvidedBy() default "";
}
