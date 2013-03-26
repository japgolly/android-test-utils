package com.github.japgolly.android.test.fest;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.fest.assertions.api.Assertions.assertThat;

import android.view.View;

import com.xtremelabs.robolectric.shadows.ShadowView;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.api.IntegerAssert;

/**
 * Fluent asserts for view testing.
 * 
 * @since 29/11/2012
 */
public abstract class AbstractViewAssert<S extends AbstractViewAssert<S, V>, V extends ShadowView>
		extends AbstractAssert<S, V> {

	protected AbstractViewAssert(V actual, Class<?> selfType) {
		super(actual, selfType);
	}

	// -----------------------------------------------------------------------------------------------------------------

	private IntegerAssert assertVisibility(String shouldSuffix) {
		final int vis = actual.getVisibility();
		final String actualText =
				vis == View.VISIBLE ? "VISIBLE" : (vis == View.INVISIBLE ? "INVISIBLE" : (vis == View.GONE ? "GONE"
						: String.valueOf(vis)));
		View realView = actual.findViewById(actual.getId());
		return Assertions.assertThat(vis).overridingErrorMessage("View should %s, not %s. [%s]", shouldSuffix,
				actualText, realView);
	}

	public S isVisible(boolean visible) {
		return visible ? isVisible() : isNotVisible();
	}

	public S isVisible() {
		assertVisibility("be visible").isEqualTo(View.VISIBLE);
		return myself;
	}

	public S isNotVisible() {
		assertVisibility("be not visible").isNotEqualTo(View.VISIBLE);
		return myself;
	}

	public S isInvisible() {
		assertVisibility("be INVISIBLE").isEqualTo(View.INVISIBLE);
		return myself;
	}

	public S isGone() {
		assertVisibility("be GONE").isEqualTo(View.GONE);
		return myself;
	}

	public S isEnabled() {
		assertThat(actual.isEnabled()).overridingErrorMessage("View should be enabled.").isTrue();
		return myself;
	}

	public S isNotEnabled() {
		assertThat(actual.isEnabled()).overridingErrorMessage("View shouldn't be enabled.").isFalse();
		return myself;
	}

	public S isSelected() {
		assertThat(actual.isSelected()).overridingErrorMessage("View should be selected.").isTrue();
		return myself;
	}

	public S isNotSelected() {
		assertThat(actual.isSelected()).overridingErrorMessage("View shouldn't be selected.").isFalse();
		return myself;
	}

	public S isShown() {
		assertThat(actual.isShown()).overridingErrorMessage("View should be shown.").isTrue();
		return myself;
	}

	public S isNotShown() {
		assertThat(actual.isShown()).overridingErrorMessage("View shouldn't be shown.").isFalse();
		return myself;
	}

	public S isFocusable() {
		assertThat(actual.isFocusable()).overridingErrorMessage("View should be focusable.").isTrue();
		return myself;
	}

	public S isNotFocusable() {
		assertThat(actual.isFocusable()).overridingErrorMessage("View shouldn't be focusable.").isFalse();
		return myself;
	}

	public S isFocused() {
		assertThat(actual.isFocused()).overridingErrorMessage("View should be focused.").isTrue();
		return myself;
	}

	public S isNotFocused() {
		assertThat(actual.isFocused()).overridingErrorMessage("View shouldn't be focused.").isFalse();
		return myself;
	}

	public S hasBackgroundResId(int id) {
		final int actualId = shadowOf(actual.getBackground()).getLoadedFromResourceId();
		Assertions.assertThat(fmtResId(actualId)).isEqualTo(fmtResId(id));
		return myself;
	}

	private static String fmtResId(int id) {
		return String.format("0x%08x", id);
	}

	public S hasBackgroundColor(int color) {
		Assertions.assertThat(actual.getBackgroundColor()).isEqualTo(color);
		return myself;
	}
}
