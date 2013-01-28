package com.github.japgolly.android.test.fest;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;

import android.view.View;

import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 * Fluent asserts for view testing.
 * 
 * @since 29/11/2012
 */
public class ViewAssert extends AbstractViewAssert<ViewAssert, ShadowView> {

	public ViewAssert(View actual) {
		this(actual == null ? null : shadowOf(actual));
	}

	public ViewAssert(ShadowView actual) {
		super(actual, ViewAssert.class);
	}

	public static ViewAssert assertThat(View actual) {
		return new ViewAssert(actual);
	}

	public static ViewAssert assertThat(ShadowView actual) {
		return new ViewAssert(actual);
	}
}
