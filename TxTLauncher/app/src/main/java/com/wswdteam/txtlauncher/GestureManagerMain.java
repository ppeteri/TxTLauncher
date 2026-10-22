package com.wswdteam.txtlauncher;

import static com.wswdteam.txtlauncher.MainActivity.touchOnIconObject;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SWIPE_MAX_OFF_PATH;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SWIPE_MIN_DISTANCE;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SWIPE_MIN_VELOCITY;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class GestureManagerMain {
    public interface Callback {
        //void onSwipe();
        void onLongPress();
        void onLeft();
        void onRight();
        void onUp();
        void onDown();
        void onDoubleTap();
    }

    public static GestureDetector create(Activity activity, GestureManagerMain.Callback callback) {
        return new GestureDetector(activity, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(@NonNull MotionEvent event) {
                callback.onLongPress();
                super.onLongPress(event);
            }
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent event) {
                callback.onDoubleTap();
                super.onDoubleTap(event);
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null) return false;
                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();
                float absDx = Math.abs(dx);
                float absDy = Math.abs(dy);
                int sminvel = SWIPE_MIN_VELOCITY;
                int smindis = SWIPE_MIN_DISTANCE;
                int smaxoff = SWIPE_MAX_OFF_PATH;
                if (touchOnIconObject) {
                    sminvel = sminvel / 3;
                    smindis = smindis / 3;
                    smaxoff = smaxoff / 3;
                }
                if (Math.max(Math.abs(velocityX), Math.abs(velocityY)) < sminvel) {
                    return false;
                }
                if (absDy > smindis && absDy > absDx && absDx < smaxoff) {
                    if (dy < 0) {
                        callback.onUp();
                    } else {
                        callback.onDown();
                    }
                    return true;
                }
                if (absDx > smindis && absDx > absDy && absDy < smaxoff) {
                    if (dx > 0) {
                        callback.onRight();
                    } else {
                        callback.onLeft();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
