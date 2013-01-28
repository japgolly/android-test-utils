package com.github.japgolly.android.test.fest;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowTextView;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 * @since 16/11/2012
 */
public class Assertions extends org.fest.assertions.api.Assertions {

	public static ActivityAssert assertThat(Activity actual) {
		return new ActivityAssert(actual);
	}

	public static ActivityAssert assertThat(ShadowActivity actual) {
		return new ActivityAssert(actual);
	}

	public static ViewAssert assertThat(View actual) {
		return new ViewAssert(actual);
	}

	public static ViewAssert assertThat(ShadowView actual) {
		return new ViewAssert(actual);
	}

	public static TextViewAssert assertThat(TextView actual) {
		return new TextViewAssert(actual);
	}

	public static TextViewAssert assertThat(ShadowTextView actual) {
		return new TextViewAssert(actual);
	}
}
