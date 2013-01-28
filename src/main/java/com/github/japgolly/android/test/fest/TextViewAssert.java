package com.github.japgolly.android.test.fest;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;

import android.widget.TextView;

import java.util.regex.Pattern;

import com.xtremelabs.robolectric.shadows.ShadowTextView;

import org.fest.assertions.api.Assertions;

/**
 * Fluent asserts for textview testing.
 * 
 * @since 29/11/2012
 */
public class TextViewAssert extends AbstractViewAssert<TextViewAssert, ShadowTextView> {

	public TextViewAssert(TextView actual) {
		this(actual == null ? null : shadowOf(actual));
	}

	public TextViewAssert(ShadowTextView actual) {
		super(actual, TextViewAssert.class);
	}

	public static TextViewAssert assertThat(TextView actual) {
		return new TextViewAssert(actual);
	}

	public static TextViewAssert assertThat(ShadowTextView actual) {
		return new TextViewAssert(actual);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public TextViewAssert hasEmptyText() {
		Assertions.assertThat(actual.getText().toString().isEmpty()).isTrue();
		return myself;
	}

	public TextViewAssert hasExactText(String text) {
		Assertions.assertThat(actual.getText().toString()).isEqualTo(text);
		return myself;
	}

	public TextViewAssert hasTextMatching(String regex) {
		Assertions.assertThat(actual.getText().toString()).matches(regex);
		return myself;
	}

	public TextViewAssert hasTextMatching(Pattern regex) {
		Assertions.assertThat(actual.getText().toString()).matches(regex);
		return myself;
	}

	public TextViewAssert doesNotHaveTextMatching(String regex) {
		Assertions.assertThat(actual.getText().toString()).doesNotMatch(regex);
		return myself;
	}

	public TextViewAssert doesNotHaveTextMatching(Pattern regex) {
		Assertions.assertThat(actual.getText().toString()).doesNotMatch(regex);
		return myself;
	}

	public TextViewAssert containsText(String text) {
		Assertions.assertThat(actual.getText().toString()).contains(text);
		return myself;
	}

	public TextViewAssert doesNotContainText(String text) {
		Assertions.assertThat(actual.getText().toString()).doesNotContain(text);
		return myself;
	}

	public TextViewAssert hasGravity(int gravity) {
		Assertions.assertThat(actual.getGravity()).isEqualTo(gravity);
		return myself;
	}

	public TextViewAssert isNotItalic() {
		Assertions.assertThat(actual.getTypeface().isItalic()).isFalse();
		return myself;
	}

	public TextViewAssert isNotBold() {
		Assertions.assertThat(actual.getTypeface().isBold()).isFalse();
		return myself;
	}

	public TextViewAssert isItalic() {
		Assertions.assertThat(actual.getTypeface().isItalic()).isTrue();
		return myself;
	}

	public TextViewAssert isBold() {
		Assertions.assertThat(actual.getTypeface().isBold()).isTrue();
		return myself;
	}
}
