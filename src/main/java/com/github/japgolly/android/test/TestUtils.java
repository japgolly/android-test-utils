package com.github.japgolly.android.test;

import static com.github.japgolly.android.test.fest.Assertions.assertThat;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;

import org.fest.assertions.api.StringAssert;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.github.japgolly.android.test.fest.ActivityAssert;

/**
 * @since 27/11/2012
 */
public class TestUtils {

	/**
	 * Makes {@link View#getGlobalVisibleRect(Rect)} populate its argument with values provided to this method.
	 * 
	 * @param view The view to stub.
	 * @param rect The stub value.
	 */
	public static void stubGlobalVisibleRect(final View view, final Rect rect) {
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				final Rect r = (Rect) invocation.getArguments()[0];
				r.set(rect);
				return true;
			}
		}).when(view).getGlobalVisibleRect(any(Rect.class));
	}

	/**
	 * Makes {@link ListAdapter#getView(int, View, ViewGroup)} return a spied view that is configured to make
	 * {@link View#getGlobalVisibleRect(Rect)} populate its argument with values provided to this method.
	 * 
	 * @param rect The stub value.
	 */
	public static void stubAdapterItemViewRect(ListAdapter adapter, final int position, final Rect rect) {
		doAnswer(new Answer<View>() {
			@Override
			public View answer(InvocationOnMock invocation) throws Throwable {
				View v = (View) invocation.callRealMethod();
				v = spy(v);
				TestUtils.stubGlobalVisibleRect(v, rect);
				return v;
			}
		}).when(adapter).getView(eq(position), any(View.class), any(ViewGroup.class));
	}

	public static MotionEvent mockAction(int action, float x, float y) {
		MotionEvent me = mock(MotionEvent.class);
		when(me.getAction()).thenReturn(action);
		when(me.getRawX()).thenReturn(x);
		when(me.getRawY()).thenReturn(y);
		return me;
	}

	public static ActivityAssert assertWentBack(Activity subject) {
		return assertThat(subject).didntStartActivity().finished();
	}

	public static void clickBack(Activity subject) {
		subject.onKeyUp(KeyEvent.KEYCODE_BACK, null);
	}

	public static void clickActionBarHome(Activity subject) {
		clickActionBarItem(subject, android.R.id.home);
	}

	public static void clickActionBarItem(Activity subject, int menuItemId) {
		subject.onOptionsItemSelected(new TestMenuItem(menuItemId));
	}

	public static ShadowAlertDialog assertAlertDialog() {
		final AlertDialog d = ShadowAlertDialog.getLatestAlertDialog();
		assertThat(d).overridingErrorMessage("AlertDialog not started.").isNotNull();
		ShadowAlertDialog dlg = shadowOf(d);
		assertThat(dlg.isShowing()).isTrue();
		return dlg;
	}

	public static StringAssert assertAlertDialogMessage() {
		return assertThat(assertAlertDialog().getMessage());
	}

	public static void clickAlertDialog(boolean positive) {
		ShadowAlertDialog dlg = assertAlertDialog();
		int button = positive ? AlertDialog.BUTTON_POSITIVE : AlertDialog.BUTTON_NEGATIVE;
		dlg.getButton(button).performClick();
	}
}
