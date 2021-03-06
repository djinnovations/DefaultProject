package co.djphy.glance.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import co.djphy.glance.R;
import co.djphy.glance.customviews.FloatOverlayView;
import co.djphy.glance.modules.appupdater.MyAppRaterUpdateHelper;
import co.djphy.glance.uiutils.ColoredSnackbar;
import co.djphy.glance.uiutils.WindowUtils;

public abstract class AppCoreActivity extends AppCompatActivity {

    private String TAG = "AppCoreActivity";

    protected ProgressBar progressBar;
    public abstract ProgressBar getProgressBar();
    public abstract Activity getSelf();
    public abstract View getViewForLayoutAccess();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    protected void startProgress() {
        Runnable runnable = new Runnable() {
            public void run() {
                AppCoreActivity.this.progressBar = getProgressBar();
                if (progressBar != null)
                    progressBar.setVisibility(View.VISIBLE);
                //displayTransOverlay(false);
                displayFloatingOverlay();
            }
        };
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    protected void stopProgress() {
        Runnable runnable = new Runnable() {
            public void run() {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                //dismissTransOverlay();
                removeFloatingOverlay();
            }
        };
        new Handler(Looper.getMainLooper()).post(runnable);
    }


    protected void checkIfAppUpdated() {
        MyAppRaterUpdateHelper updateHelper = MyAppRaterUpdateHelper.getInstance();
        updateHelper.checkForUpdates(this);
        updateHelper.rateApp(this);
    }

    protected AjaxCallback getAjaxCallback(final int id) {
        AjaxCallback<Object> ajaxCallback = new AjaxCallback<Object>() {
            public void callback(String url, Object json, AjaxStatus status) {
                serverCallEnds(id, url, json, status);
            }
        };
        return ajaxCallback;
    }


    protected AjaxCallback getAjaxCallback(final int id, Object object) {
        AjaxCallback<Object> ajaxCallback = new AjaxCallback<Object>() {
            public void callback(String url, Object json, AjaxStatus status) {
                serverCallEnds(object, id, url, json, status);
            }
        };
        return ajaxCallback;
    }


    private AQuery aQuery;

    protected AQuery getAQuery() {
        if (aQuery == null)
            aQuery = new AQuery(this);
        return aQuery;
    }

    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-" + TAG + ": " + url);
        Log.d(TAG, "response-" + TAG + ": " + json);
        stopProgress();
    }

    public void serverCallEnds(Object data, int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-" + TAG + ": " + url);
        Log.d(TAG, "response-" + TAG + ": " + json);
        stopProgress();
    }


    protected Dialog transDialog;

    @Deprecated
    protected void displayTransOverlay(final boolean isShowProgressBar) {
        Runnable runnable = new Runnable() {
            public void run() {
                View view = LayoutInflater.from(AppCoreActivity.this).inflate(R.layout.transparent_overlay, null);
                if (transDialog == null)
                    transDialog = WindowUtils.getInstance().displayDialogNoTitle(AppCoreActivity.this, view);
                transDialog.show();
                if (isShowProgressBar)
                    view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                else view.findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        };
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Deprecated
    protected void dismissTransOverlay() {
        Runnable runnable = new Runnable() {
            public void run() {
                if (transDialog != null)
                    transDialog.dismiss();
            }
        };
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    protected FloatOverlayView overlayView;

    protected void displayFloatingOverlay(){
        synchronized (this) {
            /*if (overlayView != null)
                return;
            if (transDialog != null) {
                if (transDialog.isShowing())
                    return;
            }*/
            Runnable runnable = new Runnable() {
                public void run() {
                    if (overlayView == null) {
                        overlayView = new FloatOverlayView(AppCoreActivity.this);
                        addContentView(overlayView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                                , ViewGroup.LayoutParams.MATCH_PARENT));
                        overlayView.bringToFront();
                        overlayView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                return true;
                            }
                        });
                        overlayView.invalidate();
                    }
                }
            };
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    protected void removeFloatingOverlay(){
        synchronized (this) {
            if (overlayView == null)
                return;
            Runnable runnable = new Runnable() {
                public void run() {
                    if (overlayView != null) {
                        ((ViewGroup) overlayView.getParent()).removeView(overlayView);
                        overlayView = null;
                    }
                }
            };
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    protected void promptBeforeExit(){
        final Snackbar snackbar = Snackbar.make(getViewForLayoutAccess(), "Sure you want to exit?", Snackbar.LENGTH_SHORT);
        snackbar.setAction("Yes", new View.OnClickListener() {
            public void onClick(View v) {
                snackbar.dismiss();
                onExitYesClick();
            }
        });
        ColoredSnackbar.alert(snackbar).show();
    }

    protected void onExitYesClick(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    protected abstract void onGarbageCollection();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onGarbageCollection();
    }
}
