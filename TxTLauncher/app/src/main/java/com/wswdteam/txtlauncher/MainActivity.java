package com.wswdteam.txtlauncher;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.wswdteam.txtlauncher.TxTLauncherApp.HOME_APP_NUM;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_APPLIST;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_FAVAPP;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_SETTINGS;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_WIDGET;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_AI;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_AI_LONG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_BROWSER;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_CALENDAR;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_CAMERA;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_CLOCK;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_DISCOVERY;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_MAIL;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_PHONE;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_SEARCH;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_SEARCH_LONG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_SETTINGS_LONG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_WEATHER;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.allApplicationsList;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultBackGroundColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultIcons;
import static com.wswdteam.txtlauncher.TxTLauncherApp.extraTools;
import static com.wswdteam.txtlauncher.TxTLauncherApp.getSettingsAppList;
import static com.wswdteam.txtlauncher.TxTLauncherApp.iconCache;
import static com.wswdteam.txtlauncher.TxTLauncherApp.lockApp;
import static com.wswdteam.txtlauncher.TxTLauncherApp.packageMan;
import static com.wswdteam.txtlauncher.TxTLauncherApp.savedBackgroundImage;
import static com.wswdteam.txtlauncher.TxTLauncherApp.setIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.showArrows;
import static com.wswdteam.txtlauncher.TxTLauncherApp.showMainIcons;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startInsideFunctionApp;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startLocalActivity;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startNewTaskActivity;
import static com.wswdteam.txtlauncher.TxTLauncherApp.textColorMode;
import static com.wswdteam.txtlauncher.TxTLauncherApp.darkMode;
import static com.wswdteam.txtlauncher.TxTLauncherApp.homeStartAppIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.homeSysIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultLetterColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultSelectColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.allAppData;
import static com.wswdteam.txtlauncher.TxTLauncherApp.backgroundImage;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultFontSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultPlusFontSizeTitle;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultTextColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.homeAppName;
import static com.wswdteam.txtlauncher.TxTLauncherApp.iconSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.timeHandler;
import static com.wswdteam.txtlauncher.TxTLauncherApp.timeRunnable;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startedAndroidApp;
import static com.wswdteam.txtlauncher.TxTLauncherApp.syslog;
import static com.wswdteam.txtlauncher.TxTLauncherApp.systemMessage;
import static com.wswdteam.txtlauncher.TxTLauncherApp.generateAppList;
import static com.wswdteam.txtlauncher.TxTLauncherApp.getSettingsMain;
import static com.wswdteam.txtlauncher.TxTLauncherApp.verMessage;
import static java.lang.Math.round;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    // háttérkép kezelése
    private boolean firstPermissionRequest = true;
    public static String backgroundImageBackup = "";

    // activity figyelés
    public static boolean startedAppAct = false;
    public static boolean startedWidgetAct = false;
    public static boolean startedSettingsAct = false;
    public static boolean startedFavAct = false;
    public static boolean startedHelpAct = false;
    public static boolean startedAppSelect = false;
    public static boolean touchOnIconObject = false;
    private GestureDetector gestureDetector;


    //
    //  Fő nézet létrehozása
    //
    @SuppressLint({"PrivateResource", "CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        darkMode = (getResources().getConfiguration().uiMode
                & android.content.res.Configuration.UI_MODE_NIGHT_MASK)
                == android.content.res.Configuration.UI_MODE_NIGHT_YES;
        var settings = TxTLauncherApp.sharedPreferences.edit();
        if (darkMode) {
            settings.putString(TxTLauncherApp.SETTINGS_DARK_MODE_TAG, "1");
        } else {
            settings.putString(TxTLauncherApp.SETTINGS_DARK_MODE_TAG, "0");
        }
        settings.apply();

        // globális érintés figyelés
        gestureDetector = GestureManagerMain.create(this, new GestureManagerMain.Callback() {
            @Override
            public void onLongPress() {
            }
            @Override
            public void onLeft() {
                openGoogleDiscovery();
            }
            @Override
            public void onRight() {
                openWidgetActivity();
            }
            @Override
            public void onUp() {
                // fel
                openAppListActivity();
            }
            @Override
            public void onDown() {
                openFavActivity();
            }
            @Override
            public void onDoubleTap() {
                lockApp(getBaseContext());
            }
        });

        // touch
        findViewById(R.id.menuIconMain).setOnTouchListener(new View.OnTouchListener() {
            private final GestureDetector gestureDetectorIcon = GestureManagerMain.create(MainActivity.this, new GestureManagerMain.Callback() {
                @Override
                public void onLongPress() {
                    lockApp(getBaseContext());
                }
                @Override
                public void onLeft() {
                    openGoogleDiscovery();
                }
                @Override
                public void onRight() {
                    openWidgetActivity();
                }
                @Override
                public void onUp() { openAppListActivity(); }
                @Override
                public void onDown() {
                    openFavActivity();
                }
                @Override
                public void onDoubleTap() {
                    lockApp(getBaseContext());
                }
            });

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchOnIconObject = true;
                gestureDetectorIcon.onTouchEvent(event);
                touchOnIconObject = false;
                return true;
            }
        });


        TextView tv = findViewById(R.id.mainTitle);
        defaultFontSize = tv.getTextSize();
        // méretezés: float scaledDensity = configuration.fontScale;
        // @float defaultTextSize = 14f;
        // - méretezetten: defaultPlusFontSize = (defaultFontSize * scaledDensity) - defaultFontSize + defaultPlusFontSize;
        // - méretezetten: defaultPlusFontSizeTitle = (defaultFontSize * scaledDensity) - defaultFontSize + defaultPlusFontSizeTitle;
        tv = findViewById(R.id.digitalClock);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultFontSize + (2 * defaultPlusFontSizeTitle));
        tv = findViewById(R.id.digitalDate);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultFontSize + (2 * defaultPlusFontSizeTitle));

        ImageView iv;
        iv = findViewById(R.id.applistButton);
        iv.setOnLongClickListener(v -> {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        });

        iv = findViewById(R.id.settingsButton);
        iv.setOnLongClickListener(v -> {
            openAndroidSystemSettingsButton(v);
            return true;
        });

        iv = findViewById(R.id.browserButton);
        iv.setOnLongClickListener(v -> {
            openSearchBrowser(v);
            return true;
        });

        iv = findViewById(R.id.searchButton);
        iv.setOnLongClickListener(v -> {
            openSearchBrowser(v);
            return true;
        });

        iv = findViewById(R.id.aiButton);
        iv.setOnLongClickListener(v -> {
            startInsideFunctionApp(this, START_AI_LONG);
            return true;
        });

        LinearLayout ll = findViewById(R.id.AppFrame);
        ll.setOnLongClickListener(v -> {
            openFavActivity();
            return true;
        });

        // új telepítés vagy frissítési üzenet
        verMessage(this);

        // első indulás
        startedAndroidApp = true;
        generateAppList();
        getSettingsMain(this);
        getSettingsAppList();
        backgroundImage = backgroundImageBackup;
        saveButtonImages();
        backgroundPrepare();
        setColors();
        screenPrepare();
        buttonPrepare();
        setHomaApp();
    }



    //
    // egész képernyős érintés kezelésének lezáráaa
    //
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean touchOnIcon;

        ImageView micon = findViewById(R.id.menuIconMain);
        touchOnIcon = isTouchOnView(ev, micon);
        if (!touchOnIcon) {
            gestureDetector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
    private boolean isTouchOnView(MotionEvent event, View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        float x = event.getRawX();
        float y = event.getRawY();
        return x >= loc[0] && x <= loc[0] + view.getWidth() && y >= loc[1] && y <= loc[1] + view.getHeight();
    }



    //
    //  Fő nézet indítása
    //
    @Override
    public void onStart() {
        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.enter_from_bottom, R.anim.exit_to_top);
        overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.enter_from_top, R.anim.exit_to_bottom);

        super.onStart();

        generateAppList();
        startClock();

        if (startedSettingsAct) {
            if (startedAppSelect) {
                getSettingsAppList();
            }
            getSettingsMain(this);
            backgroundPrepare();
            setColors();
            screenPrepare();
            buttonPrepare();
            setHomaApp();
        }

        startedAppAct = false;
        startedWidgetAct = false;
        startedSettingsAct = false;
        startedFavAct = false;
        startedHelpAct = false;
        startedAndroidApp = false;

        syslog(getString(R.string.started_activity) + ": " + this.getClass().getSimpleName());
    }


    //
    //  Fő nézet leállítása
    //
    @Override
    public void onStop() {
        super.onStop();
        stopClock();
    }


    //
    //  Fő nézet visszaállítása
    //
    @Override
    protected void onResume() {
        super.onResume();
        //startClock();
    }


    //
    //  Fő nézet megállítás
    //
    @Override
    protected void onPause() {
        super.onPause();
        stopClock();
    }


    //
    //  Fő nézet kilépés
    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeHandler.removeCallbacks(timeRunnable);
    }



    //
    // a jépernyő előkészítése
    //
    private void screenPrepare() {
        LinearLayout ll;
        ll = findViewById(R.id.lLayoutIcons1);
        if (showArrows) {
            ll.setVisibility(VISIBLE);
        } else {
            ll.setVisibility(INVISIBLE);
        }
        ll = findViewById(R.id.lLayoutIcons2);
        if (showMainIcons) {
            ll.setVisibility(VISIBLE);
        } else {
            ll.setVisibility(INVISIBLE);
        }
        ll = findViewById(R.id.lLayoutDecor);
        if (extraTools) {
            ll.setVisibility(VISIBLE);
        } else {
            ll.setVisibility(INVISIBLE);
        }
    }



    //
    // a háttér előkészítése
    //
    private void backgroundPrepare() {
        if (Objects.equals(backgroundImage, backgroundImageBackup)) {
            View view = findViewById(R.id.mainView);
            if (view == null) return;
            // régi kép
            File imgFile = new File(savedBackgroundImage);
            if (imgFile.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    if (bitmap != null) {
                        view.setBackground(new BitmapDrawable(getResources(), bitmap));
                    } else {
                        view.setBackground(null);
                    }
                } catch (OutOfMemoryError e) {
                    //e.printStackTrace();
                    systemMessage(getString(R.string.background_file_not_found));
                    view.setBackground(null);
                }
            } else {
                view.setBackground(null);
            }
        } else {
            // új kép
            if (!backgroundImage.isEmpty()) {
                setupBackground();
            }
        }
    }



    //
    // háttér beállítása és mentése
    //
    @SuppressLint("ThreadConstraint")
    private void setupBackground() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
            // jogodultdág kérése csak egyszer
            if (firstPermissionRequest) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PackageManager.PERMISSION_GRANTED);
                firstPermissionRequest = false;
            }
        }
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File sourceFile = new File(dir + "/" + backgroundImage);
        if (!sourceFile.exists()) {
            systemMessage(getString(R.string.background_file_not_found));
            backgroundImageBackup = backgroundImage;
            return;
        }
        View view = findViewById(R.id.mainView);
        int screenWidth = view.getWidth();
        int screenHeight = view.getHeight();
        try {
            Bitmap sourceBitmap = BitmapFactory.decodeFile(sourceFile.getAbsolutePath());
            if (sourceBitmap == null) throw new Exception(String.valueOf(R.string.background_file_not_found));
            int iWidth = sourceBitmap.getWidth();
            int iHeight = sourceBitmap.getHeight();
            float screenRatio = (float) screenHeight / screenWidth;
            float imageRatio = (float) iHeight / iWidth;
            int finalWidth, finalHeight;
            int startX = 0, startY = 0;
            if (imageRatio > screenRatio) {
                // A kép "magasabb", mint a kijelző -> alul-felül vágunk
                finalWidth = iWidth;
                finalHeight = Math.round(iWidth * screenRatio);
                startY = (iHeight - finalHeight) / 2;
            } else {
                // A kép "szélesebb", mint a kijelző -> két oldalt vágunk
                finalHeight = iHeight;
                finalWidth = Math.round(iHeight / screenRatio);
                startX = (iWidth - finalWidth) / 2;
            }
            Bitmap croppedBitmap = Bitmap.createBitmap(sourceBitmap, startX, startY, finalWidth, finalHeight);
            try (FileOutputStream out = new FileOutputStream(savedBackgroundImage)) {
                croppedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            }
            // Megjelenítés
            view.setBackground(new BitmapDrawable(getResources(), croppedBitmap));
            if (sourceBitmap != croppedBitmap) {
                sourceBitmap.recycle();
            }
            //savedBackgroundImage = croppedBitmap;
        } catch (Exception e) {
            //e.printStackTrace();
            systemMessage(getString(R.string.error_bitmap) + ": " + e.getMessage());
        }
    }



    //
    //  Fő nézet: beállítások betöltése
    //
    @SuppressLint("PrivateResource")
    public void setColors() {
        // szín beállítás a változókba
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            defaultBackGroundColor = getColor(com.google.android.material.R.color.design_dark_default_color_background);
            defaultTextColor = getColor(com.google.android.material.R.color.design_dark_default_color_on_surface);
            defaultSelectColor = getColor(com.google.android.material.R.color.design_dark_default_color_primary_variant);
            defaultLetterColor = getColor(com.google.android.material.R.color.design_dark_default_color_secondary_variant);
            defaultIconColor = getColor(com.google.android.material.R.color.design_dark_default_color_on_surface);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            defaultBackGroundColor = getColor(com.google.android.material.R.color.design_default_color_background);
            defaultTextColor = getColor(com.google.android.material.R.color.design_default_color_on_surface);
            defaultSelectColor = getColor(com.google.android.material.R.color.design_default_color_secondary_variant);
            defaultLetterColor = getColor(com.google.android.material.R.color.design_default_color_secondary);
            defaultIconColor = getColor(com.google.android.material.R.color.design_default_color_on_surface);
        }

        // teljes háttér színe
        getWindow().getDecorView().setBackgroundColor(defaultBackGroundColor);

        if (adaptiveIcon || textColorMode) {
            setIconColor(this, R.id.applistButton, adaptiveIconColor);
            setIconColor(this, R.id.searchButton, adaptiveIconColor);
            setIconColor(this, R.id.weatherButton, adaptiveIconColor);
            setIconColor(this, R.id.settingsButton, adaptiveIconColor);
            setIconColor(this, R.id.aiButton, adaptiveIconColor);
            setIconColor(this, R.id.arrowDown, adaptiveIconColor);
            setIconColor(this, R.id.arrowLeft, adaptiveIconColor);
            setIconColor(this, R.id.arrowRight, adaptiveIconColor);
            setIconColor(this, R.id.arrowUp, adaptiveIconColor);
            setIconColor(this, R.id.applistButton, adaptiveIconColor);
            setIconColor(this, R.id.dialButton, adaptiveIconColor);
            setIconColor(this, R.id.mailButton, adaptiveIconColor);
            setIconColor(this, R.id.applistButton, adaptiveIconColor);
            setIconColor(this, R.id.browserButton, adaptiveIconColor);
            setIconColor(this, R.id.cameraButton, adaptiveIconColor);
            setIconColor(this, R.id.menuIconMain, adaptiveIconColor);
        } else {
            setIconColor(this, R.id.applistButton, defaultIconColor);
            setIconColor(this, R.id.searchButton, defaultIconColor);
            setIconColor(this, R.id.weatherButton, defaultIconColor);
            setIconColor(this, R.id.settingsButton, defaultIconColor);
            setIconColor(this, R.id.aiButton, defaultIconColor);
            setIconColor(this, R.id.arrowDown, defaultIconColor);
            setIconColor(this, R.id.arrowLeft, defaultIconColor);
            setIconColor(this, R.id.arrowRight, defaultIconColor);
            setIconColor(this, R.id.arrowUp, defaultIconColor);
            setIconColor(this, R.id.applistButton, defaultIconColor);
            setIconColor(this, R.id.dialButton, defaultIconColor);
            setIconColor(this, R.id.mailButton, defaultIconColor);
            setIconColor(this, R.id.applistButton, defaultIconColor);
            setIconColor(this, R.id.browserButton, defaultIconColor);
            setIconColor(this, R.id.cameraButton, defaultIconColor);
            setIconColor(this, R.id.menuIconMain, defaultIconColor);
        }

       TextView tw = findViewById(R.id.digitalClock);
       tw.setTextColor(defaultTextColor);
       TextView tdw = findViewById(R.id.digitalDate);
       tdw.setTextColor(defaultTextColor);

    }



    //
    //  Fő nézet: gombok eredeti képeinek mentése
    //
    public void saveButtonImages() {
        ImageView ivone;
        ivone = findViewById(R.id.dialButton);
        defaultIcons.add(ivone.getDrawable());
        ivone = findViewById(R.id.mailButton);
        defaultIcons.add(ivone.getDrawable());
        ivone = findViewById(R.id.applistButton);
        defaultIcons.add(ivone.getDrawable());
        ivone = findViewById(R.id.browserButton);
        defaultIcons.add(ivone.getDrawable());
        ivone = findViewById(R.id.cameraButton);
        defaultIcons.add(ivone.getDrawable());
    }



    //
    //   Fő nézet: alapértelmezett gombok beállítása (Cache-elve)
    //
    public void setHomaApp() {
        final ArrayList<String> appHList1 = new ArrayList<>();
        final ArrayList<String> appHList2 = new ArrayList<>();
        final ListView homeTable1 = findViewById(R.id.homeAppList1);
        final ListView homeTable2 = findViewById(R.id.homeAppList2);

        // Gyorskereső térkép a ResolveInfo-hoz, hogy ne kelljen ciklikusan keresgélni
        final java.util.Map<String, ResolveInfo> appIconMap = new java.util.HashMap<>();
        if (homeStartAppIcon && allAppData != null && allApplicationsList != null) {
            for (int i = 0; i < allAppData.length; i++) {
                if (i < allApplicationsList.size()) {
                    appIconMap.put(allAppData[i][0], allApplicationsList.get(i));
                }
            }
        }

        final var adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appHList1) {
            @Override
            public String getItem(int position) {
                return super.getItem(position);
            }

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                String appN = getItem(position);
                View row = super.getView(position, convertView, parent);
                if (appN != null) {
                    TextView tvt = row.findViewById(android.R.id.text1);
                    if (homeStartAppIcon) {
                        ResolveInfo app = appIconMap.get(appN);
                        if (app != null && app.activityInfo != null) {
                            int padding = iconSize / 2;
                            tvt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

                            // Gyorsítótár ellenőrzése a csomagnév alapján
                            String cacheKey = app.activityInfo.packageName;
                            Drawable iconToDisplay = iconCache.get(cacheKey);

                            if (iconToDisplay == null) {
                                // Ha nincs a cache-ben, legyártjuk és eltároljuk
                                Drawable appI = app.loadIcon(packageMan);
                                iconToDisplay = getDrawableTxT(row, appI, iconSize);
                                iconCache.put(cacheKey, iconToDisplay);
                            }

                            tvt.setCompoundDrawablesRelative(iconToDisplay, null, null, null);
                            tvt.setCompoundDrawablePadding(padding);
                        } else {
                            tvt.setCompoundDrawablesRelative(null, null, null, null);
                        }
                    } else {
                        tvt.setGravity(Gravity.CENTER_HORIZONTAL);
                        tvt.setCompoundDrawablesRelative(null, null, null, null);
                    }
                    tvt.setText(appN);
                    if (textColorMode) {
                        tvt.setTextColor(adaptiveIconColor);
                    } else {
                        tvt.setTextColor(defaultTextColor);
                    }
                    tvt.setEllipsize(TextUtils.TruncateAt.END);
                    tvt.setMaxLines(1);
                }
                return row;
            }
        };

        final var adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appHList2) {
            @Override
            public String getItem(int position) {
                return super.getItem(position);
            }

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                String appN = getItem(position);
                View row = super.getView(position, convertView, parent);
                if (appN != null) {
                    TextView tvt = row.findViewById(android.R.id.text1);
                    if (homeStartAppIcon) {
                        ResolveInfo app = appIconMap.get(appN);
                        if (app != null && app.activityInfo != null) {
                            int padding = iconSize / 2;
                            tvt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

                            // Gyorsítótár ellenőrzése a csomagnév alapján
                            String cacheKey = app.activityInfo.packageName;
                            Drawable iconToDisplay = iconCache.get(cacheKey);

                            if (iconToDisplay == null) {
                                // Ha nincs a cache-ben, legyártjuk és eltároljuk
                                Drawable appI = app.loadIcon(packageMan);
                                iconToDisplay = getDrawableTxT(row, appI, iconSize);
                                iconCache.put(cacheKey, iconToDisplay);
                            }

                            tvt.setCompoundDrawablesRelative(iconToDisplay, null, null, null);
                            tvt.setCompoundDrawablePadding(padding);
                        } else {
                            tvt.setCompoundDrawablesRelative(null, null, null, null);
                        }
                    } else {
                        tvt.setGravity(Gravity.CENTER_HORIZONTAL);
                        tvt.setCompoundDrawablesRelative(null, null, null, null);
                    }
                    tvt.setText(appN);
                    if (textColorMode) {
                        tvt.setTextColor(adaptiveIconColor);
                    } else {
                        tvt.setTextColor(defaultTextColor);
                    }
                    tvt.setEllipsize(TextUtils.TruncateAt.END);
                    tvt.setMaxLines(1);
                }
                return row;
            }
        };

        double anum = Math.min(homeAppName.size(),  HOME_APP_NUM);
        double an = anum / 2;
        long halfA = round(an);
        int halfApp = (int) halfA;
        for (var i = 0; i < halfApp; i++) {
            if (homeAppName.size() > i) {
                if (!homeAppName.get(i).isEmpty()) {
                    appHList1.add(homeAppName.get(i));
                }
            }
        }
        for (var i = halfApp; i <  HOME_APP_NUM; i++) {
            if (homeAppName.size() > i) {
                if (!homeAppName.get(i).isEmpty()) {
                    appHList2.add(homeAppName.get(i));
                }
            }
        }

        homeTable1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        homeTable2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        homeTable1.setOnItemClickListener((parent, view, position, id) -> {
            String selectedP = (String) (homeTable1.getItemAtPosition(position));
            for (int i=0; i<allAppData.length; i++) {
                String appName;
                appName = allAppData[i][0];
                if (selectedP.equals(appName)) {
                    String pkg = allAppData[i][1];
                    String cls = allAppData[i][2];
                    if (startNewTaskActivity(this, pkg, cls)) {
                        startedAndroidApp = true;
                    }
                    i = allAppData.length;
                }
            }
        });

        homeTable2.setOnItemClickListener((parent, view, position, id) -> {
            String selectedP = (String) (homeTable2.getItemAtPosition(position));
            for (int i=0; i<allAppData.length; i++) {
                String appName;
                appName = allAppData[i][0];
                if (selectedP.equals(appName)) {
                    String pkg = allAppData[i][1];
                    String cls = allAppData[i][2];
                    if (startNewTaskActivity(this, pkg, cls)) {
                        startedAndroidApp = true;
                    }
                    i = allAppData.length;
                }
            }
        });
    }


    //
    // ikonok kiszedése és szinezáse
    //
    static Drawable getDrawableTxT(View view, Drawable appI, int iconSize) {
        Drawable iconToDisplay;
        Drawable base = appI != null ? appI.mutate() : null;

        if (adaptiveIcon && base instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptI = (AdaptiveIconDrawable) base;
            Drawable monoIcon = adaptI.getMonochrome();
            if (monoIcon == null) {
                monoIcon = ContextCompat.getDrawable(view.getContext(), R.drawable.app);
            }
            iconToDisplay = getDefaultMonoIcon(view, monoIcon, iconSize);
            if (iconToDisplay == null) {
                iconToDisplay = getDefaultIconAsLayer(view, iconSize);
            }
        } else {
            if (adaptiveIcon) {
                iconToDisplay = getDefaultIconAsLayer(view, iconSize);
            } else {
                if (base == null) {
                    iconToDisplay = getDefaultIconAsLayer(view, iconSize);
                } else {
                    LayerDrawable layer = new LayerDrawable(new Drawable[]{base});
                    layer.setLayerSize(0, iconSize, iconSize);
                    layer.setBounds(0, 0, iconSize, iconSize);
                    iconToDisplay = layer;
                }
            }
        }
        assert iconToDisplay != null;
        iconToDisplay.setBounds(0, 0, iconSize, iconSize);
        return iconToDisplay;
    }

    // alapértelmezett app ikon
    private static Drawable getDefaultIconAsLayer(View view, int iconSize) {
        try {
            Drawable d = ContextCompat.getDrawable(view.getContext(), R.drawable.app);
            if (d != null) {
                d = d.mutate();
                d.setTint(adaptiveIconColor);
                LayerDrawable layer = new LayerDrawable(new Drawable[]{d});
                layer.setLayerSize(0, iconSize, iconSize);
                layer.setBounds(0, 0, iconSize, iconSize);
                return layer;
            }
        } catch (Exception e) {
            // Hiba kezelése
        }
        return null;
    }

    // mono app ikon
    public static Drawable getDefaultMonoIcon(View view, Drawable monoIcon, int iconSize) {
        try {
            if (monoIcon != null) {
                monoIcon = monoIcon.mutate();
                monoIcon.setTint(adaptiveIconColor);
                monoIcon.setBounds(0, 0, iconSize, iconSize);
                // 1
                // - float negInset = -0.50f; // -50%
                // - InsetDrawable insetIcon = new InsetDrawable(monoIcon, negInset);
                // - LayerDrawable layer = new LayerDrawable(new Drawable[]{insetIcon});
                // - layer.setLayerSize(0, iconSize, iconSize);
                // - layer.setBounds(0, 0, iconSize, iconSize);
                // 2 - nagyított ikon, de drive eltűnik
                // - int largerSize = (int) (iconSize * 1.5f);
                // - int offset = (largerSize - iconSize) / 2;
                // - LayerDrawable layer = new LayerDrawable(new Drawable[]{monoIcon});
                // - layer.setLayerSize(0, largerSize, largerSize);
                // - layer.setLayerInset(0, -offset, -offset, -offset, -offset);
                // - layer.setBounds(0, 0, iconSize, iconSize);
                // 3 - teljes háttér
                // - GradientDrawable background = new GradientDrawable();
                // - int baseColor = ContextCompat.getColor(view.getContext(), R.color.transparentgray);
                // - int translucentColor = ColorUtils.setAlphaComponent(baseColor, 180);
                // - background.setColor(translucentColor);
                // - background.setShape(GradientDrawable.OVAL);
                // - background.setCornerRadius(iconSize * 0.22f);
                // 4
                // - GradientDrawable background = new GradientDrawable();
                // - background.setColor(Color.TRANSPARENT);
                // - int baseColor = ContextCompat.getColor(view.getContext(), R.color.transparentgray);
                // - int translucentColor = ColorUtils.setAlphaComponent(baseColor, 180);
                // - background.setStroke(iconPadding, translucentColor);
                // - background.setShape(GradientDrawable.OVAL);
                // - int inset = iconPadding;
                // - if (full) {
                // -   inset = inset * 4;
                // - }
                // - LayerDrawable layer = new LayerDrawable(new Drawable[]{background, monoIcon});
                // - layer.setLayerSize(0, iconSize, iconSize);
                // - layer.setLayerInset(1, inset, inset, inset, inset);
                // - return layer;

                //  Nagyobb ideiglenes Bitmap az elemzéshez és vágáshoz
                int rawSize = iconSize * 2;
                Bitmap rawBitmap = Bitmap.createBitmap(rawSize, rawSize, Bitmap.Config.ARGB_8888);
                Canvas rawCanvas = new Canvas(rawBitmap);
                monoIcon.setBounds(0, 0, rawSize, rawSize);
                monoIcon.draw(rawCanvas);
                // Automatikus margóvágás (tartalom határainak keresése)
                int minX = rawSize, minY = rawSize, maxX = 0, maxY = 0;
                boolean hasContent = false;
                for (int y = 0; y < rawSize; y++) {
                    for (int x = 0; x < rawSize; x++) {
                        int pixel = rawBitmap.getPixel(x, y);
                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);
                        int a = Color.alpha(pixel);
                        if (!(r > 240 && g > 240 && b > 240) && a > 50) {
                            if (x < minX) minX = x;
                            if (x > maxX) maxX = x;
                            if (y < minY) minY = y;
                            if (y > maxY) maxY = y;
                            hasContent = true;
                        }
                    }
                }
                if (!hasContent || minX >= maxX || minY >= maxY) {
                    minX = 0; minY = 0; maxX = rawSize; maxY = rawSize;
                }
                int croppedWidth = maxX - minX;
                int croppedHeight = maxY - minY;
                Bitmap croppedBitmap = Bitmap.createBitmap(rawBitmap, minX, minY, croppedWidth, croppedHeight);
                // Aránytartó méretezés és középre igazítás belső paddinggel
                Bitmap finalBitmap = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888);
                Canvas finalCanvas = new Canvas(finalBitmap);
                //float paddingPercentage = 0.3f;
                float paddingPercentage = 0.3f;
                float maxAvailableSize = iconSize * (1.0f - (paddingPercentage * 2));
                float scale = Math.min(maxAvailableSize / croppedWidth, maxAvailableSize / croppedHeight);
                float scaledWidth = croppedWidth * scale;
                float scaledHeight = croppedHeight * scale;
                float left = (iconSize - scaledWidth) / 2f;
                float top = (iconSize - scaledHeight) / 2f;
                Rect srcRect = new Rect(0, 0, croppedWidth, croppedHeight);
                RectF dstRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
                Paint tintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                finalCanvas.drawBitmap(croppedBitmap, srcRect, dstRect, tintPaint);
                int baseColor = ContextCompat.getColor(view.getContext(), R.color.transparentgray);
                int translucentColor = ColorUtils.setAlphaComponent(baseColor, 180);
                // A keret távolsága a szélektől
                int frameMargin = (int) (iconSize * 0.1f);
                GradientDrawable background = new GradientDrawable();
                background.setShape(GradientDrawable.OVAL);
                background.setStroke((int) (iconSize * 0.05f), translucentColor);
                // Keret vastagsága
                background.setColor(Color.TRANSPARENT);
                // Itt állítjuk be a margót, hogy a keret ne érjen ki teljesen a szélére
                background.setBounds(frameMargin, frameMargin, iconSize - frameMargin, iconSize - frameMargin);
                background.draw(finalCanvas);
                return new BitmapDrawable(view.getContext().getResources(), finalBitmap);

            }
        } catch (Exception e) {
            // Hiba kezelése
        }
        return null;
    }



    // fő ikonok kirajzolása
    private Drawable mainDrawable(Drawable appI, Drawable drI) {
        Drawable iconToDisplay;
        if (adaptiveIcon && appI instanceof AdaptiveIconDrawable) {
            AdaptiveIconDrawable adaptI = (AdaptiveIconDrawable) appI;
            Drawable monoIcon = adaptI.getMonochrome();
            if (monoIcon != null) {
                monoIcon.mutate();
                monoIcon.setTint(adaptiveIconColor);
                int negPadding = -(iconSize/2);
                InsetDrawable insetIcon = new InsetDrawable(monoIcon, negPadding);
                LayerDrawable layer = new LayerDrawable(new Drawable[]{insetIcon});
                layer.setLayerSize(0, iconSize, iconSize);
                iconToDisplay = layer;
            } else {
                iconToDisplay = getDefaultIconMain(iconSize);
            }
        } else {
            if (adaptiveIcon) {
                iconToDisplay = getDefaultIconMain(iconSize);
            } else {
                LayerDrawable layer = new LayerDrawable(new Drawable[]{appI});
                layer.setLayerSize(0, iconSize, iconSize);
                iconToDisplay = layer;
            }
        }
        if (!adaptiveIcon) {
            if (homeSysIcon) {
                iconToDisplay = appI;
            } else {
                iconToDisplay = drI;
            }
        }
        assert iconToDisplay != null;
        iconToDisplay.setBounds(0, 0, iconSize, iconSize);
        return iconToDisplay;
    }


    // Segédfüggvény a kód tisztaságáért
    private Drawable getDefaultIconMain(int iconSize) {
        Drawable d = ContextCompat.getDrawable(this, R.drawable.app);
        if (d != null) {
            d.mutate();
            d.setTint(adaptiveIconColor);
            d.setBounds(0, 0, iconSize, iconSize);
            LayerDrawable layer = new LayerDrawable(new Drawable[]{d});
            layer.setLayerSize(0, iconSize, iconSize);
            return layer;
        }
        return null;
    }



    //
    //  Fő nézet: alapértelmezett gombok előkészítése, alapértelmezett app kéeresése
    //
    @SuppressLint("UseCompatLoadingForDrawables")
    public void buttonPrepare() {
        // dial
        Intent intent = new Intent(Intent.ACTION_DIAL, null);
        ImageView iv = findViewById(R.id.dialButton);
        Drawable dr = getDrawable(R.drawable.call);
        imageButtonColorize(intent, iv, dr, 0);

        // mail
        intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        iv = findViewById(R.id.mailButton);
        dr = getDrawable(R.drawable.email);
        imageButtonColorize(intent, iv, dr, 1);

        // app list
        ImageView appF = findViewById(R.id.applistButton);
        appF.clearColorFilter();
        if ((homeSysIcon) && (!adaptiveIcon)) {
            GradientDrawable border = new GradientDrawable();
            border.setColor(Color.TRANSPARENT);
            border.setStroke(2, Color.WHITE);
            border.setCornerRadius(10);
            appF.setBackground(border);
            appF.setPadding(15, 15, 15, 15);
        } else {
            appF.setBackground(null);
            appF.setPadding(0, 0, 0, 0);
        }
        //appF.setImageDrawable(defaultIcons.get(2));
        if (adaptiveIcon) {
            appF.setColorFilter(adaptiveIconColor);
        } else {
            if (textColorMode) {
                appF.setColorFilter(adaptiveIconColor);
            } else {
                appF.setColorFilter(defaultIconColor);
            }
        }

        // browser
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"));
        iv = findViewById(R.id.browserButton);
        dr = getDrawable(R.drawable.internet);
        imageButtonColorize(intent, iv, dr, 3);

        // camera
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        iv = findViewById(R.id.cameraButton);
        dr = getDrawable(R.drawable.photo);
        imageButtonColorize(intent, iv, dr, 4);
    }



    //
    // gomb szinezése
    //
    public void imageButtonColorize(Intent in, ImageView iv, Drawable dr, int deficon) {
        List<ResolveInfo> appDL = getPackageManager().queryIntentActivities(in, 0);
        ResolveInfo appD = appDL.get(0);
        iv.clearColorFilter();
        if (homeSysIcon) {
            Drawable appI = appD.loadIcon(packageMan);
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable appI2 = mainDrawable(appI, dr);
            if (adaptiveIcon) {
                iv.setImageDrawable(appI2);
            } else {
                iv.setImageDrawable(appI);
            }
        } else {
            iv.setImageDrawable(defaultIcons.get(deficon));
            if (adaptiveIcon || textColorMode) {
                iv.setColorFilter(adaptiveIconColor);
            } else {
                iv.setColorFilter(defaultIconColor);
            }
        }
    }



    //
    //  Fő nézet: gombok lenyomása, app start
    //
    @SuppressLint("NonConstantResourceId")
    public void startButtonApp(View view) {
        if (view.getId() == R.id.applistButton) {
            openAppListActivity();
            return;
        }
        if (view.getId() == R.id.dialButton) {
            startInsideFunctionApp(this, START_PHONE);
            return;
        }
        if (view.getId() == R.id.mailButton) {
            startInsideFunctionApp(this, START_MAIL);
            return;
        }
        if (view.getId() == R.id.browserButton) {
            startInsideFunctionApp(this, START_BROWSER);
            return;
        }
        if (view.getId() == R.id.cameraButton) {
            startInsideFunctionApp(this, START_CAMERA);
        }
    }



    //
    //  Fő nézet: idő kiírása a képernyőre
    //
    public void startClock() {
        // óra
        stopClock();
        final TextView textView = findViewById(R.id.digitalClock);
        final TextView textDateView = findViewById(R.id.digitalDate);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textDateView.setText(currentDate);
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = simpleTimeFormat.format(calendar.getTime());
                textView.setText(currentTime);
                timeHandler.postDelayed(this, 10000);
            }
        };
        timeHandler.post(timeRunnable);
    }



    //
    //  Fő nézet: idő kiírás: leállítás
    //
    public void stopClock() {
        if (timeRunnable != null) {
            timeHandler.removeCallbacks(timeRunnable);
        }
    }


    //
    // Rendszer beállítások indítása
    //
    public void openAndroidSystemSettings(View v) {
        openSettingsActivity();
    }



    //
    //  Fő nézet: keresés
    //
    public void startSearch(View view) {
        startInsideFunctionApp(this, START_SEARCH);
        //syslog("Button start: search");
    }


    //
    //  Fő nézet: google discovery
    //
    public void openGoogleDiscovery() {
        startInsideFunctionApp(this, START_DISCOVERY);
        //syslog("Button start: search");
    }



    //
    //  Fő nézet: óra app indítása
    //
    public void openClock(View view) {
        startInsideFunctionApp(this, START_CLOCK);
    }


    //
    //  Fő nézet: naptár app indítása
    //
    public void openCalendar(View view) {
        startInsideFunctionApp(this, START_CALENDAR);
    }



    //
    // Rendszer beállítások indítása
    //
    public void openAndroidSystemSettingsButton(View v) {
        startInsideFunctionApp(this, START_SETTINGS_LONG);
    }


    //
    // Internet keresás
    //
    public void openSearchBrowser(View v) {
        startInsideFunctionApp(this, START_SEARCH_LONG);
    }


    //
    // Időjárás keresás
    //
    public void openWeatherSearch(View v) {
        startInsideFunctionApp(this, START_WEATHER);
    }


    //
    // AI mutatása
    //
    public void openAI(View view) {
        startInsideFunctionApp(this, START_AI);
    }



    //
    // App indítása
    //
    public void startButtonAppDiscovery(View v) {
        startInsideFunctionApp(this, START_DISCOVERY);
    }



    //
    // App indítása
    //
    public void startButtonAppFav(View v) {
        startLocalActivity(this, START_ACT_FAVAPP);
        startedFavAct = true;
        //syslog("Action swipe right: open widgets");
    }



    //
    // App indítása
    //
    public void startButtonAllApp(View v) {
        startLocalActivity(this, START_ACT_APPLIST);
        startedAppAct = true;
        //syslog("Action swipe down: open all app list");
    }



    //
    //  Fő nézet: widget nézet indítása
    //
    public void startButtonWidgetActivity(View v) {
        startLocalActivity(this, START_ACT_WIDGET);
        //syslog("Action swipe right: open widgets");
        startedWidgetAct = true;
    }



    //
    //  Fő nézet: widget nézet indítása
    //
    public void openWidgetActivity() {
        startLocalActivity(this, START_ACT_WIDGET);
        startedWidgetAct = true;
        //syslog("Action swipe right: open widgets");
    }



    //
    //  Fő nézet: app lista nézet indítása
    //
    public void openAppListActivity() {
        startLocalActivity(this, START_ACT_APPLIST);
        startedAppAct = true;
        //syslog("Action swipe up: openapplist");
    }


    //
    //  Fő nézet: app lista nézet indítása gombról
    //
    public void openAppListButton(View view) {
        startLocalActivity(this, START_ACT_APPLIST);
        startedAppAct = true;
        //syslog("Action tap button: openapplist");
    }



    //
    //  Fő nézet: kedvensek nézet indítása
    //
    public void openFavActivity() {
        startLocalActivity(this, START_ACT_FAVAPP);
        startedFavAct = true;
        //syslog("Action swipe down: open fav avt");
    }



    //
    //  Fő nézet: beállítás nézet indítása
    //
    public void openSettingsActivity() {
        startLocalActivity(this, START_ACT_SETTINGS);
        startedSettingsAct = true;
        backgroundImageBackup = backgroundImage;
        //syslog("Action long tap: open settings");
    }

}

