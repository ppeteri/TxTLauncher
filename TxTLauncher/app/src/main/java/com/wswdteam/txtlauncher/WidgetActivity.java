package com.wswdteam.txtlauncher;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_APPLIST;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_FAVAPP;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_HELP;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_SETTINGS;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_AI;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_AI_LONG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_BROWSER;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_BROWSER_LONG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_CALENDAR;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_CAMERA;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_DISCOVERY;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_MAP;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_SETTINGS_LONG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_WEATHER;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_NOTE_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultBackGroundColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultFontSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultPlusFontSizeTitle;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultTextColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.extraTools;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startInsideFunctionApp;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startLocalActivity;
import static com.wswdteam.txtlauncher.TxTLauncherApp.syslog;
import static com.wswdteam.txtlauncher.TxTLauncherApp.textColorMode;
import static com.wswdteam.txtlauncher.TxTLauncherApp.hideKeyboard;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startedAndroidApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;


//
// Widget nézet létrehozása
//
public class WidgetActivity extends AppCompatActivity {

    public static boolean firstLoadAI = true;
    private GestureDetector gestureDetector;


    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_widget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.widgetLayout), (v, insets) -> {
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

        // teljes háttér színe
        getWindow().getDecorView().setBackgroundColor(defaultBackGroundColor);

        TextView tv = findViewById(R.id.widgetTitle);
        tv.setTextColor(defaultTextColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, TxTLauncherApp.defaultFontSize + defaultPlusFontSizeTitle);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable appI = getDrawable(R.drawable.arrow_back);
        if (appI != null) {
            int ts = (int) defaultFontSize + (int) defaultPlusFontSizeTitle;
            appI.setBounds(0, 0, ts, ts);
            appI.setTint(defaultTextColor);
            tv.setCompoundDrawables(appI, null, null, null);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setCompoundDrawables(appI, null, null, null);
        }

        EditText editText = findViewById(R.id.widgetTextNote);
        editText.setBackgroundTintList(ColorStateList.valueOf(Color.DKGRAY));
        editText.setMovementMethod(new ScrollingMovementMethod());
        editText.setOnTouchListener((v, event) -> {
            if (editText.hasFocus()) {
                editText.clearFocus();
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_MASK:
                        return true;
                }
            }
            return false;
        });

        CalendarView cv = findViewById(R.id.widgetCalendar);
        cv.setLongClickable(true);
        Calendar calendar = Calendar.getInstance();
        cv.setDate(calendar.getTimeInMillis(), false, true);
        cv.setFirstDayOfWeek(calendar.getFirstDayOfWeek());
        cv.setOnLongClickListener(v -> {
            startInsideFunctionApp(v.getContext(), START_CALENDAR);
            return true;
        });
        cv.setOnDateChangeListener((view, year, mounth, day) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year, mounth, day);
            DateFormat df = DateFormat.getDateInstance();
            String date = df.format(calendar1.getTime());
            String text = editText.getText().toString();
            if (text.isEmpty()) {
                editText.setText(date + "  ");
            } else {
                // végére: editText.setText(text + "\n" + date + "  ");
                editText.setText(date + "  \n" + text);
            }
            editText.requestFocus();
            // végére: editText.setSelection(editText.getText().length());
            editText.setSelection(date.length() + 2);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        if (adaptiveIcon) {
            setIconColorW(R.id.wsettingsButton, adaptiveIconColor);
            setIconColorW(R.id.wwetButton, adaptiveIconColor);
            setIconColorW(R.id.whelpButton, adaptiveIconColor);
            setIconColorW(R.id.wbrowserButton, adaptiveIconColor);
            setIconColorW(R.id.wcameraButton, adaptiveIconColor);
            setIconColorW(R.id.wapplistButton, adaptiveIconColor);
            setIconColorW(R.id.wfavlistButton, adaptiveIconColor);
            setIconColorW(R.id.waiButton, adaptiveIconColor);
            setIconColorW(R.id.wdiscoveryButton, adaptiveIconColor);
            setIconColorW(R.id.wmapButton, adaptiveIconColor);
        } else {
            setIconColorW(R.id.wsettingsButton, defaultIconColor);
            setIconColorW(R.id.wwetButton, defaultIconColor);
            setIconColorW(R.id.whelpButton, defaultIconColor);
            setIconColorW(R.id.wbrowserButton, defaultIconColor);
            setIconColorW(R.id.wcameraButton, defaultIconColor);
            setIconColorW(R.id.wapplistButton, defaultIconColor);
            setIconColorW(R.id.wfavlistButton, defaultIconColor);
            setIconColorW(R.id.waiButton, defaultIconColor);
            setIconColorW(R.id.wdiscoveryButton, defaultIconColor);
            setIconColorW(R.id.wmapButton, defaultIconColor);
        }

        int textColor;
        int iconColor;
        if (textColorMode) {
            textColor = adaptiveIconColor;
            iconColor = adaptiveIconColor;
        } else {
            textColor = defaultTextColor;
            if (adaptiveIcon) {
                iconColor = adaptiveIconColor;
            } else {
                iconColor = defaultTextColor;
            }
        }
        TextView tvx;
        setIconColorW(R.id.wsettingsButton, iconColor);
        tvx = findViewById(R.id.wsettingsButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.wwetButton, iconColor);
        tvx = findViewById(R.id.wwetButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.whelpButton, iconColor);
        tvx = findViewById(R.id.whelpButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.wbrowserButton, iconColor);
        tvx = findViewById(R.id.wbrowserButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.wcameraButton, iconColor);
        tvx = findViewById(R.id.wcameraButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.wapplistButton, iconColor);
        tvx = findViewById(R.id.wapplistButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.wfavlistButton, iconColor);
        tvx = findViewById(R.id.wfavlistButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.waiButton, iconColor);
        tvx = findViewById(R.id.waiButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.wdiscoveryButton, iconColor);
        tvx = findViewById(R.id.wdiscoveryButtonTitle);
        tvx.setTextColor(textColor);
        setIconColorW(R.id.wmapButton, iconColor);
        tvx = findViewById(R.id.wmapButtonTitle);
        tvx.setTextColor(textColor);

        tvx = findViewById(R.id.widgetNoteTitle);
        tvx.setTextColor(textColor);

        ImageView iv = findViewById(R.id.wsettingsButton);
        iv.setOnLongClickListener(v -> {
            startButtonSystemSettings(v);
            this.finish();
            return true;
        });

        iv = findViewById(R.id.waiButton);
        iv.setOnLongClickListener(v -> {
            startInsideFunctionApp(this, START_AI_LONG);
            this.finish();
            return true;
        });

        iv = findViewById(R.id.wapplistButton);
        iv.setOnLongClickListener(v -> {
            startFavApp(v);
            this.finish();
            return true;
        });

        iv = findViewById(R.id.wbrowserButton);
        iv.setOnLongClickListener(v -> {
            startButtonAppBrowserLong();
            this.finish();
            return true;
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }


    //
    // Widget nézet indítása
    //
    @Override
    public void onStart(){
        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.enter_from_left, R.anim.exit_to_right);
        overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.enter_from_right, R.anim.exit_to_left);

        super.onStart();

        String val = TxTLauncherApp.sharedPreferences.getString(SETTINGS_NOTE_TAG, "");
        if (!val.isEmpty()) {
            EditText tv = findViewById(R.id.widgetTextNote);
            tv.setText(val);
        }
        firstLoadAI = true;

        if (extraTools) {
            LinearLayout ll = findViewById(R.id.extraWidget1);
            ll.setVisibility(VISIBLE);
            ll = findViewById(R.id.extraWidget2);
            ll.setVisibility(VISIBLE);
        } else {
            LinearLayout ll = findViewById(R.id.extraWidget1);
            ll.setVisibility(GONE);
            ll = findViewById(R.id.extraWidget2);
            ll.setVisibility(GONE);
        }

        ScrollView sv = findViewById(R.id.widgetScrollFrame);
        sv.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (Math.abs(scrollY - oldScrollY) > 1) {
                hideKeyboard(this);
            }
        });

        syslog(getString(R.string.started_activity) + ": "+ this.getClass().getSimpleName());
    }


    //
    // Widget nézet leállítása
    //
    @Override
    public void onStop(){
        super.onStop();
        //appWidgetHost.stopListening();

        var settings = TxTLauncherApp.sharedPreferences.edit();
        EditText tv = findViewById(R.id.widgetTextNote);
        String text = tv.getText().toString();
        settings.putString(SETTINGS_NOTE_TAG, text);
        settings.apply();
    }



    //
    //  Fő nézet: alapértelmezett gombok beállítása
    //
    public void setIconColorW(int id, int color) {
        ImageView btn;
        Drawable drw;
        btn = findViewById(id);
        drw =btn.getDrawable();
        if(drw !=null) {
            drw = DrawableCompat.wrap(drw).mutate();
            DrawableCompat.setTint(drw, color);
            btn.setImageDrawable(drw);
        }
    }


    //
    // Widget nézet bezárása
    //
    public void closeWidgetButton(View view) {
        this.finish();
    }



    //
    // Rendszer beállítások indítása
    //
    public void startButtonSystemSettings(View v) {
        startInsideFunctionApp(this, START_SETTINGS_LONG);
        this.finish();
    }



    //
    // időjárás keresés
    //
    public void startButtonWeatherSearch(View v) {
        startInsideFunctionApp(this, START_WEATHER);
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppHelp(View v) {
        // help
        openHelp(v);
    }



    //
    // App indítása
    //
    public void startButtonAppBrowser(View v) {
        startInsideFunctionApp(this, START_BROWSER);
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppBrowserLong() {
        startInsideFunctionApp(this, START_BROWSER_LONG);
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppCamera(View v) {
        startInsideFunctionApp(this, START_CAMERA);
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppList(View v) {
        // applist
        MainActivity.startedSettingsAct = true;
        startLocalActivity(this, START_ACT_APPLIST);
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppFav(View v) {
        // fav
        MainActivity.startedSettingsAct = true;
        startLocalActivity(this, START_ACT_FAVAPP);
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonSettings(View v) {
        MainActivity.startedSettingsAct = true;
        startLocalActivity(this, START_ACT_SETTINGS);
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppAI(View v) {
        startInsideFunctionApp(this, START_AI);
        startedAndroidApp = true;
        this.finish();
    }



    //
    // AI mutatása
    //
    public void openAI(View view) {
        startInsideFunctionApp(this, START_AI);
        startedAndroidApp = true;
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppDiscovery(View v) {
        startInsideFunctionApp(this, START_DISCOVERY);
        startedAndroidApp = true;
        this.finish();
    }


    //
    // App indítása
    //
    public void startCalendar(View v) {
        startInsideFunctionApp(this, START_CALENDAR);
        startedAndroidApp = true;
        this.finish();
    }



    //
    // App indítása
    //
    public void startButtonAppMap(View v) {
        // map
        openMap(v);
    }



    //
    // Térkép mutatása
    //
    public void openMap(View view) {
        startInsideFunctionApp(this, START_MAP);
        startedAndroidApp = true;
        this.finish();
    }



    //
    //  Fő nézet: leírás
    //
    public void openHelp(View view) {
        MainActivity.startedHelpAct = true;
        startLocalActivity(this, START_ACT_HELP);
        this.finish();
    }



    //
    //  Fő nézet: kedvencek
    //
    public void startFavApp(View view) {
        MainActivity.startedFavAct = true;
        startLocalActivity(this, START_ACT_FAVAPP);
        this.finish();
    }

}