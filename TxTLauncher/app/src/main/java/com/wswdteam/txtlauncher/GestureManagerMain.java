package com.wswdteam.txtlauncher;

import static com.wswdteam.txtlauncher.MainActivity.touchOnIconObject;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SWIPE_MAX_OFF_PATH;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SWIPE_MIN_DISTANCE;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class GestureManagerMain {
    public interface Callback {
        void onLongPress();
        void onLeft();
        void onRight();
        void onUp();
        void onDown();
        void onDoubleTap();
    }

    public static GestureDetector create(Activity activity, GestureManagerMain.Callback callback) {
        return new GestureDetector(activity, new GestureDetector.SimpleOnGestureListener() {
            private boolean hasFired = false;

            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                hasFired = false; // Új érintésnél reseteljük a jelzőt
                return super.onDown(e);
            }

            @Override
            public void onLongPress(@NonNull MotionEvent event) {
                callback.onLongPress();
                super.onLongPress(event);
            }

            @Override
            public boolean onDoubleTap(@NonNull MotionEvent event) {
                callback.onDoubleTap();
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
                if (e1 == null || hasFired) {
                    return false;
                }

                // Érzékenységi küszöbök beállítása (touchOnIconObject esetén 3x érzékenyebb)
                int smindis = SWIPE_MIN_DISTANCE;
                int smaxoff = SWIPE_MAX_OFF_PATH;
                if (touchOnIconObject) {
                    smindis = smindis / 3;
                    smaxoff = smaxoff / 3;
                }

                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();
                float absDx = Math.abs(dx);
                float absDy = Math.abs(dy);

                // Függőleges mozgás (Up / Down)
                if (absDy > smindis && absDy > absDx && absDx < smaxoff) {
                    hasFired = true;
                    if (dy < 0) {
                        callback.onUp();
                    } else {
                        callback.onDown();
                    }
                    return true;
                }

                // Vízszintes mozgás (Left / Right)
                if (absDx > smindis && absDx > absDy && absDy < smaxoff) {
                    hasFired = true;
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