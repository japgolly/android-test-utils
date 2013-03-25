package com.github.japgolly.android.test.fest;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;

import android.app.Activity;
import android.content.Intent;

import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowActivity.IntentForResult;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

/**
 * Fluent asserts for activity testing.
 * 
 * @since 16/11/2012
 */
public class ActivityAssert extends AbstractAssert<ActivityAssert, ShadowActivity> {

	public ActivityAssert(Activity actual) {
		this(actual == null ? null : shadowOf(actual));
	}

	public ActivityAssert(ShadowActivity actual) {
		super(actual, ActivityAssert.class);
	}

	public static ActivityAssert assertThat(Activity actual) {
		return new ActivityAssert(actual);
	}

	public static ActivityAssert assertThat(ShadowActivity actual) {
		return new ActivityAssert(actual);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public ActivityAssert finished() {
		isNotNull();
		Assertions.assertThat(actual.isFinishing()).overridingErrorMessage("Expected activity to be finished").isTrue();
		return this;
	}

	public ActivityAssert isNotFinished() {
		isNotNull();
		Assertions.assertThat(actual.isFinishing()).overridingErrorMessage("Expected activity not to be finished")
				.isFalse();
		return this;
	}

	public ActivityAssert withResultCode(int code) {
		isNotNull();
		Assertions
				.assertThat(actual.getResultCode())
				.overridingErrorMessage("Expected activity result code to be <%d> but was <%d>", code,
						actual.getResultCode()) //
				.isEqualTo(code);
		return this;
	}

	public ActivityAssert didntStartActivity() {
		Assertions.assertThat(actual.getNextStartedActivity()).isNull();
		return this;
	}

	public Intent startedActivity() {
		isNotNull();
		Intent intent = actual.getNextStartedActivity();
		Assertions.assertThat(intent).overridingErrorMessage("No activity started.").isNotNull();
		return intent;
	}

	public Intent startedActivity(Class<? extends Activity> activity) {
		Intent intent = startedActivity();
		Assertions.assertThat(shadowOf(intent).getIntentClass().getCanonicalName()).isEqualTo(
				activity.getCanonicalName());
		return intent;
	}

	public IntentForResult startedActivityForResult() {
		isNotNull();
		IntentForResult i = actual.getNextStartedActivityForResult();
		Assertions.assertThat(i).overridingErrorMessage("No activity started.").isNotNull();
		return i;
	}

	public Intent startedActivityForResult(Class<? extends Activity> activity, int requestCode) {
		IntentForResult i = startedActivityForResult();
		Assertions.assertThat(i.requestCode).isEqualTo(requestCode);
		Assertions.assertThat(shadowOf(i.intent).getIntentClass().getCanonicalName()).isEqualTo(
				activity.getCanonicalName());
		return i.intent;
	}

	public Intent getResultIntent() {
		return actual.getResultIntent();
	}
}
