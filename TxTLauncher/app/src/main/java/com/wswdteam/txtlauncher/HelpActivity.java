package com.wswdteam.txtlauncher;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
import static com.wswdteam.txtlauncher.R.*;
import static com.wswdteam.txtlauncher.R.id.helpText1;
import static com.wswdteam.txtlauncher.R.id.helpText2;
import static com.wswdteam.txtlauncher.R.id.helpText3;
import static com.wswdteam.txtlauncher.R.id.helpText4;
import static com.wswdteam.txtlauncher.R.id.helpVersion;
import static com.wswdteam.txtlauncher.R.id.helpWeb;
import static com.wswdteam.txtlauncher.TxTLauncherApp.TXT_APP_NAME;
import static com.wswdteam.txtlauncher.TxTLauncherApp.TXT_VERSION;
import static com.wswdteam.txtlauncher.TxTLauncherApp.TXT_WEB_PAGE;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultBackGroundColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultFontSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultPlusFontSizeTitle;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultTextColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.syslog;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


//
// Beállítások nézet
//
public class HelpActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.activity_help);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.helpLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gestureDetector = GestureManagerMain.create(this, new GestureManagerMain.Callback() {
            @Override
            public void onLeft() {
                finish();
            }

            @Override
            public void onRight() {
                finish();
            }

            @Override
            public void onUp() {
            }

            @Override
            public void onDown() {
            }

            @Override
            public void onDoubleTap() {
                finish();
            }

            @Override
            public void onLongPress() {
                finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    //
    // Beállítások indítása
    //
    public void onStart() {
        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, anim.enter_from_top, anim.exit_to_bottom);
        // - overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.enter_from_left, R.anim.exit_to_right);
        super.onStart();
        syslog(getString(string.started_activity) + ": "+ this.getClass().getSimpleName());
    }


    //
    // Beállítások leállítása
    //
    public void onStop() {
        super.onStop();
    }



    //
    // Beállítások leállítása
    //
    public void onResume() {
        super.onResume();
        this.getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
        TextView ver = findViewById(helpVersion);
        ver.setText(String.format("%s: %s %s", TXT_APP_NAME, TXT_VERSION, getString(string.licence)));
        TextView web = findViewById(helpWeb);
        web.setText(String.format("%s", TXT_WEB_PAGE));

        TextView help = findViewById(helpText1);
        help.setText(getString(string.help_text_1));
        help = findViewById(helpText2);
        help.setText(getString(string.help_text_2));
        help = findViewById(helpText3);
        help.setText(getString(string.help_text_3));
        help = findViewById(helpText4);
        help.setText(getString(string.help_text_4));

        // teljes háttér színe
        getWindow().getDecorView().setBackgroundColor(defaultBackGroundColor);

        TextView tv = findViewById(id.helpViewTitle);
        tv.setTextColor(defaultTextColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultFontSize + defaultPlusFontSizeTitle);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable appI = getDrawable(drawable.arrow_back);
        if (appI != null) {
            int ts = (int) defaultFontSize + (int) defaultPlusFontSizeTitle;
            appI.setBounds(0, 0, ts, ts);
            appI.setTint(defaultTextColor);
            tv.setCompoundDrawables(appI, null, null, null);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setCompoundDrawables(appI, null, null, null);
        }
        tv = findViewById(helpVersion);
        tv.setTextColor(defaultTextColor);
        tv = findViewById(helpWeb);
        tv.setTextColor(defaultTextColor);
        tv = findViewById(helpText1);
        tv.setTextColor(defaultTextColor);
        tv = findViewById(helpText2);
        tv.setTextColor(defaultTextColor);
        tv = findViewById(helpText3);
        tv.setTextColor(defaultTextColor);
        tv = findViewById(helpText4);
        tv.setTextColor(defaultTextColor);
    }



    //
    // Beállítások nézet bezárása
    //
    public void closeHelpButton(View v) {
        this.finish();
    }

}


