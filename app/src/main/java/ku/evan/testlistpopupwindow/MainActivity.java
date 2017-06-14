package ku.evan.testlistpopupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * This is a demo app to indicate the crash reported at https://issuetracker.google.com/issues/37323898.
 *
 * Steps to reproduce:
 * Launch the app and tap on the "Hello World!" text view and then tap on one of the items in the ListPopupWindow.
 *
 * Frequency:
 * 100% on Android 7.1.2 only, not on Android 7.1.1 or lower
 *
 * Expected output:
 * No crashing, the popup window should reappear when user taps on one of the items in the popup list
 *
 * Current output:
 * The app crashes with NPE on Android 7.1.2
 *
 * Crash log:
 *    FATAL EXCEPTION: main
 *    Process: ku.evan.testlistpopupwindow, PID: 7422
 *    java.lang.NullPointerException: Attempt to invoke virtual method 'android.transition.Transition android.transition.Transition.removeListener(android.transition.Transition$TransitionListener)' on a null object reference
 *        at android.widget.PopupWindow$PopupDecorView$3.onTransitionEnd(PopupWindow.java:2380)
 *        at android.widget.PopupWindow$PopupDecorView.cancelTransitions(PopupWindow.java:2414)
 *        at android.widget.PopupWindow.preparePopup(PopupWindow.java:1289)
 *        at android.widget.PopupWindow.showAsDropDown(PopupWindow.java:1227)
 *        at android.support.v7.widget.AppCompatPopupWindow.showAsDropDown(AppCompatPopupWindow.java:105)
 *        at android.support.v4.widget.PopupWindowCompatKitKat.showAsDropDown(PopupWindowCompatKitKat.java:33)
 *        at android.support.v4.widget.PopupWindowCompat$KitKatPopupWindowImpl.showAsDropDown(PopupWindowCompat.java:129)
 *        at android.support.v4.widget.PopupWindowCompat.showAsDropDown(PopupWindowCompat.java:206)
 *        at android.support.v7.widget.ListPopupWindow.show(ListPopupWindow.java:721)
 *        at ku.evan.testlistpopupwindow.MainActivity.onItemClick(MainActivity.java:45)
 *        at android.widget.AdapterView.performItemClick(AdapterView.java:310)
 *        at android.widget.AbsListView.performItemClick(AbsListView.java:1164)
 *        at android.widget.AbsListView$PerformClick.run(AbsListView.java:3132)
 *        at android.widget.AbsListView$3.run(AbsListView.java:4047)
 *        at android.os.Handler.handleCallback(Handler.java:751)
 *        at android.os.Handler.dispatchMessage(Handler.java:95)
 *        at android.os.Looper.loop(Looper.java:154)
 *        at android.app.ActivityThread.main(ActivityThread.java:6121)
 *        at java.lang.reflect.Method.invoke(Native Method)
 *        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:889)
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String[] items = { "One", "Two", "Three", "Four", "Five" };

    private ListPopupWindow mListPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        View textView = findViewById(R.id.text_view);
        textView.setOnClickListener(this);

        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        mListPopupWindow.setAnchorView(textView);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view:
                mListPopupWindow.show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListPopupWindow.dismiss();
        // TODO: this line causes the app to crash on Android !!!!
        mListPopupWindow.show();
    }
}
