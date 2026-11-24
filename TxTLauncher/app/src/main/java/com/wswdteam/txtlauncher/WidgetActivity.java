package com.wswdteam.txtlauncher;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.wswdteam.txtlauncher.IconManagement.iconFrame;
import static com.wswdteam.txtlauncher.IconManagement.setIconColor;
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
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_SEARCH;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_SETTINGS_LONG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_WEATHER;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_NOTE_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultBackGroundColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultFontSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultPlusFontSizeTitle;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultScreenIcons;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultTextColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.extraTools;
import static com.wswdteam.txtlauncher.TxTLauncherApp.focusMode;
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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;


//
// Widget nézet létrehozása
//
public class WidgetActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
    public boolean widgetIconChanged = false;
    public boolean widgetFirstStart = true;

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility", "SetTextI18n", "MissingInflatedId"})
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

        widgetIconChanged = false;
        widgetFirstStart = true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean touchOnIcon;
        boolean touchOnIcon2;

        CalendarView cv = findViewById(R.id.widgetCalendar);
        touchOnIcon = isTouchOnView(event, cv);
        EditText text = findViewById(R.id.widgetTextNote);
        touchOnIcon2 = isTouchOnView(event, text);
        if (!touchOnIcon && !touchOnIcon2) {
            gestureDetector.onTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }
    private boolean isTouchOnView(MotionEvent event, View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        float x = event.getRawX();
        float y = event.getRawY();
        return x >= loc[0] && x <= loc[0] + view.getWidth() && y >= loc[1] && y <= loc[1] + view.getHeight();
    }



    //
    // Widget nézet indítása
    //
    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onStart(){
        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.enter_from_left, R.anim.exit_to_right);
        overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.enter_from_right, R.anim.exit_to_left);

        super.onStart();
        ImageView iv;
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

        iv = findViewById(R.id.menuIconW);
        iv.setOnLongClickListener(v -> {
            this.finish();
            return true;
        });

        EditText editText = findViewById(R.id.widgetTextNote);
        iv.setOnClickListener(v -> this.finish());
        String val = TxTLauncherApp.sharedPreferences.getString(SETTINGS_NOTE_TAG, "");
        if (!val.isEmpty()) {
            editText.setText(val);
        }

        editText.setMovementMethod(new ScrollingMovementMethod());
        editText.setOnTouchListener((v, event) -> {
            if (editText.hasFocus()) {
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

        iv = findViewById(R.id.extraSearchBar);
        iv.setOnClickListener(v -> {
            startInsideFunctionApp(this, START_SEARCH);
            this.finish();
        });

        iv = findViewById(R.id.wsettingsButton);
        iv.setOnLongClickListener(v -> {
            startButtonSystemSettings(v);
            this.finish();
            return true;
        });

        if (widgetFirstStart) {
            //ImageView iv;
            iv = findViewById(R.id.wsettingsButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.wapplistButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.wfavlistButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.wbrowserButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.wcameraButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.wwetButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.whelpButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.waiButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.wdiscoveryButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.wmapButton);
            defaultScreenIcons.add(iv.getDrawable());
            iv = findViewById(R.id.menuIconW);
            defaultScreenIcons.add(iv.getDrawable());
        }

        syslog(getString(R.string.started_activity) + ": "+ this.getClass().getSimpleName());
    }



    //
    // Widget nézet leállítása
    //
    @Override
    public void onStop() {
        super.onStop();
    }



    //
    // Widget nézet leállítása
    //
    @Override
    public void onResume () {
        super.onResume();

        int textColor;
        int iconColor;
        if (textColorMode) {
            textColor = adaptiveIconColor;
            iconColor = adaptiveIconColor;
        } else {
            textColor = defaultTextColor;
            iconColor = defaultIconColor;
        }

        if (widgetFirstStart && extraTools) {
            iconFrame(this, R.id.wsettingsButton, iconColor);
            iconFrame(this, R.id.wapplistButton, iconColor);
            iconFrame(this, R.id.wfavlistButton, iconColor);
            iconFrame(this, R.id.wbrowserButton, iconColor);
            iconFrame(this, R.id.wcameraButton, iconColor);
            iconFrame(this, R.id.wwetButton, iconColor);
            iconFrame(this, R.id.whelpButton, iconColor);
            iconFrame(this, R.id.waiButton, iconColor);
            iconFrame(this, R.id.wdiscoveryButton, iconColor);
            iconFrame(this, R.id.wmapButton, iconColor);
            MainActivity.emptyIcon = true;
            iconFrame(this, R.id.menuIconW, iconColor);
            MainActivity.emptyIcon = false;
            widgetIconChanged = true;
        }
        if (extraTools) {
            if (!widgetIconChanged) {
                iconFrame(this, R.id.wsettingsButton, iconColor);
                iconFrame(this, R.id.wapplistButton, iconColor);
                iconFrame(this, R.id.wfavlistButton, iconColor);
                iconFrame(this, R.id.wbrowserButton, iconColor);
                iconFrame(this, R.id.wcameraButton, iconColor);
                iconFrame(this, R.id.wwetButton, iconColor);
                iconFrame(this, R.id.whelpButton, iconColor);
                iconFrame(this, R.id.waiButton, iconColor);
                iconFrame(this, R.id.wdiscoveryButton, iconColor);
                iconFrame(this, R.id.wmapButton, iconColor);
                MainActivity.emptyIcon = true;
                iconFrame(this, R.id.menuIconW, iconColor);
                MainActivity.emptyIcon = false;
                widgetIconChanged = true;
            }
        } else {
            if (widgetIconChanged) {
                ImageView ivone;
                ivone = findViewById(R.id.wsettingsButton);
                ivone.setImageDrawable(defaultScreenIcons.get(0));
                ivone = findViewById(R.id.wapplistButton);
                ivone.setImageDrawable(defaultScreenIcons.get(1));
                ivone = findViewById(R.id.wfavlistButton);
                ivone.setImageDrawable(defaultScreenIcons.get(2));
                ivone = findViewById(R.id.wbrowserButton);
                ivone.setImageDrawable(defaultScreenIcons.get(3));
                ivone = findViewById(R.id.wcameraButton);
                ivone.setImageDrawable(defaultScreenIcons.get(4));
                ivone = findViewById(R.id.wwetButton);
                ivone.setImageDrawable(defaultScreenIcons.get(5));
                ivone = findViewById(R.id.whelpButton);
                ivone.setImageDrawable(defaultScreenIcons.get(6));
                ivone = findViewById(R.id.waiButton);
                ivone.setImageDrawable(defaultScreenIcons.get(7));
                ivone = findViewById(R.id.wdiscoveryButton);
                ivone.setImageDrawable(defaultScreenIcons.get(8));
                ivone = findViewById(R.id.wmapButton);
                ivone.setImageDrawable(defaultScreenIcons.get(9));
                ivone = findViewById(R.id.menuIconW);
                ivone.setImageDrawable(defaultScreenIcons.get(10));
                widgetIconChanged = false;
            }
        }

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

        setIconColor(this, R.id.wsettingsButton, iconColor);
        setIconColor(this, R.id.wwetButton, iconColor);
        setIconColor(this, R.id.whelpButton, iconColor);
        setIconColor(this, R.id.wbrowserButton, iconColor);
        setIconColor(this, R.id.wcameraButton, iconColor);
        setIconColor(this, R.id.wapplistButton, iconColor);
        setIconColor(this, R.id.wfavlistButton, iconColor);
        setIconColor(this, R.id.waiButton, iconColor);
        setIconColor(this, R.id.wdiscoveryButton, iconColor);
        setIconColor(this, R.id.wmapButton, iconColor);

        TextView tvx;
        setIconColor(this, R.id.wsettingsButton, iconColor);
        tvx = findViewById(R.id.wsettingsButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.wwetButton, iconColor);
        tvx = findViewById(R.id.wwetButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.whelpButton, iconColor);
        tvx = findViewById(R.id.whelpButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.wbrowserButton, iconColor);
        tvx = findViewById(R.id.wbrowserButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.wcameraButton, iconColor);
        tvx = findViewById(R.id.wcameraButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.wapplistButton, iconColor);
        tvx = findViewById(R.id.wapplistButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.wfavlistButton, iconColor);
        tvx = findViewById(R.id.wfavlistButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.waiButton, iconColor);
        tvx = findViewById(R.id.waiButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.wdiscoveryButton, iconColor);
        tvx = findViewById(R.id.wdiscoveryButtonTitle);
        tvx.setTextColor(textColor);
        setIconColor(this, R.id.wmapButton, iconColor);
        tvx = findViewById(R.id.wmapButtonTitle);
        tvx.setTextColor(textColor);
        tvx = findViewById(R.id.widgetNoteTitle);
        tvx.setTextColor(textColor);

        setIconColor(this, R.id.menuIconW, iconColor);

        ImageView iv = findViewById(R.id.extraSearchBar);
        iv.setOnClickListener(v -> {
            startInsideFunctionApp(this, START_SEARCH);
            this.finish();
        });

        if (extraTools && !focusMode) {
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
        if (focusMode) {
            LinearLayout ll = findViewById(R.id.buttonsFrame2);
            ll.setVisibility(GONE);
            Button ib = findViewById(R.id.wcalendarbutton);
            ib.setVisibility(INVISIBLE);
            ll = findViewById(R.id.wapplistButtonL);
            ll.setVisibility(INVISIBLE);
            ll = findViewById(R.id.wfavlistButtonL);
            ll.setVisibility(INVISIBLE);
            ll = findViewById(R.id.wbrowserButtonL);
            ll.setVisibility(INVISIBLE);
        }

        ScrollView sv = findViewById(R.id.widgetScrollFrame);
        sv.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (Math.abs(scrollY - oldScrollY) > 1) {
                hideKeyboard(this);
            }
        });

        widgetFirstStart = false;
    }



    //
    // Widget nézet leállítása
    //
    @Override
    public void onPause(){
        super.onPause();

        var settings = TxTLauncherApp.sharedPreferences.edit();
        EditText tv = findViewById(R.id.widgetTextNote);
        String text = tv.getText().toString();
        settings.putString(SETTINGS_NOTE_TAG, text);
        settings.apply();
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