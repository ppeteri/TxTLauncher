package com.wswdteam.txtlauncher;

import static com.wswdteam.txtlauncher.FavoritesActivity.favAppName;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class TxTLauncherApp extends android.app.Application {
    //
    // verzió, fejlesztő adatok
    //
    public static String TXT_VERSION = "1.0.1";
    public static String TXT_APP_NAME = "TxTLauncher";
    public static String TXT_WEB_PAGE = "https://github.com/pphome2/TxTLauncher";

    //
    // Log üzenetekhez használható
    //
    public static boolean developerMode = false;
    public static boolean verNewInstall = false;
    public static boolean verUpdate = false;

    // verzió mentése
    public static final String SETTINGS_VERSION_TAG = "TxTVersion";

    // debug jelölő a log-ba
    public static final String DEBUG_TAG = "TxTLauncher_App";

    // általános constansok
    public static final int HOME_APP_NUM = 10;
    public static final int FAV_APP_NUM = 20;

    // gesture mód beállítások
    public static int SWIPE_MIN_DISTANCE = 36;
    public static int SWIPE_MAX_OFF_PATH = 24;
    public static int SWIPE_MIN_VELOCITY = 50;

    // jelölők
    public static final int START_PHONE = 1;
    public static final int START_MAIL = 2;
    public static final int START_BROWSER = 3;
    public static final int START_BROWSER_LONG = 4;
    public static final int START_CAMERA = 5;
    public static final int START_SEARCH = 6;
    public static final int START_SEARCH_LONG = 7;
    public static final int START_WEATHER = 8;
    public static final int START_SETTINGS = 9;
    public static final int START_SETTINGS_LONG = 10;
    public static final int START_AI = 11;
    public static final int START_AI_LONG = 12;
    public static final int START_DISCOVERY = 13;
    public static final int START_MAP = 14;
    public static final int START_CALENDAR = 15;
    public static final int START_CLOCK = 16;
    public static final int START_ACT_APPLIST = 1;
    public static final int START_ACT_FAVAPP = 2;
    public static final int START_ACT_HELP = 3;
    public static final int START_ACT_SETTINGS = 4;
    public static final int START_ACT_WIDGET = 5;
    public static final int START_ACT_FAVAPP_SEL = 6;
    public static final int START_ACT_APPLIST_SEL = 7;

    // beállítások mentése jelölők
    public static String PRIVATE_SETTINGS_TAG = "TxTLauncher_App";
    public static String SETTINGS_APP_TAG = "HomeApp";
    public static String SETTINGS_FAV_APP_TAG = "FavApp";
    public static String SETTINGS_SYS_ICON_TAG = "SysIcon";
    public static String SETTINGS_HOME_ICON_TAG = "AppIcon";
    public static String SETTINGS_ADAPTIVE_ICON_TAG = "AdaptiveIcon";
    public static String SETTINGS_ADAPTIVE_ICON_COLOR_TAG = "AdaptiveIconColor";
    public static String SETTINGS_TEXT_COLOR_MODE_TAG = "TextColorMode";
    public static String SETTINGS_DARK_MODE_TAG = "DarkMode";
    public static String SETTINGS_SHOW_ARROWS_TAG = "ShowArrows";
    public static String SETTINGS_SHOW_MAIN_CONTROL_ICONS = "ShowMainIcons";
    public static String SETTINGS_EXTRA_TOOLS = "ExtraTools";
    public static String SETTINGS_ONE_COLUMN_FAVORITES_TAG = "oneColFavorites";
    public static String SETTINGS_URL_PRIVATEAI_TAG = "PrivateAI";
    public static String SETTINGS_URL_SEARCH_TAG = "Search";
    public static String SETTINGS_WEATHER_URL_TAG = "Weather";
    public static String SETTINGS_BACKGROUND_IMAGE_TAG = "BackgroundImage";
    public static String SETTINGS_NOTE_TAG = "AppNote";

    // beállítások alapértelmezett tartalma
    public static String AI_NAME_STRING = "gemini";
    public static String privateAIUrl = "https://duckduckgo.com/?q=DuckDuckGo+AI+Chat&ia=chat&duckai=1";
    public static String privateSearchUrl = "https://duckduckgo.com/?q=";
    public static String privateAIUrlOrig = "https://duckduckgo.com/?q=DuckDuckGo+AI+Chat&ia=chat&duckai=1";
    public static String privateSearchUrlOrig = "https://duckduckgo.com/?q=";
    public static String weatherUrl = "";
    public static String backgroundImage = "";
    public static String weatherUrlOrig = "";
    public static String backgroundImageOrig = "";
    public static String savedBackgroundImage = "";

    // rendszer változók
    public static SharedPreferences sharedPreferences;
    public static PackageManager packageMan;
    public static Handler timeHandler = new Handler(Looper.getMainLooper());
    public static Runnable timeRunnable;

    // beállítások változói
    public static boolean homeSysIcon = false;
    public static boolean homeStartAppIcon = false;
    public static boolean adaptiveIcon = false;
    public static boolean onecolFavorites = false;
    public static boolean textColorMode = false;
    public static boolean darkMode = true;
    public static boolean showArrows = false;
    public static boolean showMainIcons = true;
    public static boolean extraTools = false;
    public static int adaptiveIconColor = 0;
    public static int defaultIconColor = 0;

    // app-ok kezelése
    public static ArrayList<ResolveInfo> allApplicationsList = new ArrayList<>();
    public static List<String> homeAppName = new ArrayList<>();
    public static List<Drawable> defaultIcons = new ArrayList<>();
    public static Context AppContext;
    public static long packageUpdateTime;
    public static String[][] allAppData;

    // szöveg és szín beállítások
    public static float defaultFontSize = 0;
    public static float defaultPlusFontSize = 10;
    public static float defaultPlusFontSizeTitle = 15;
    public static int defaultBackGroundColor = 0;
    public static int defaultSelectColor = 0;
    public static int defaultTextColor = 0;
    public static int defaultLetterColor = 0;
    public static int iconSize;
    public static int iconPadding;
    public static boolean startedAndroidApp = true;


    @Override
    public void onCreate() {
        super.onCreate();

        SWIPE_MIN_VELOCITY = Math.round(SWIPE_MIN_VELOCITY * getResources().getDisplayMetrics().density);
        SWIPE_MAX_OFF_PATH = Math.round(SWIPE_MAX_OFF_PATH * getResources().getDisplayMetrics().density);
        SWIPE_MIN_DISTANCE = Math.round(SWIPE_MIN_DISTANCE * getResources().getDisplayMetrics().density);

        AppContext = this.getApplicationContext();
        sharedPreferences = getSharedPreferences(PRIVATE_SETTINGS_TAG, MODE_PRIVATE);
        //saveSettings();
        packageMan = getPackageManager();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        versionCheck();

        weatherUrl = getString(R.string.search_weather);
        weatherUrlOrig = getString(R.string.search_weather);

        iconSize = (int) (32 * this.getResources().getDisplayMetrics().density);
        float density = ((float) iconSize / 2);// * getContext().getResources().getDisplayMetrics().density;
        iconPadding = (int) (-1 * density);

        savedBackgroundImage = String.format("%s/launcher_bg.png", getFilesDir());

        syslog(TXT_APP_NAME + " " + TXT_VERSION + " - " + TXT_WEB_PAGE);
        syslog(getString(R.string.started_activity) + ": " + this.getClass().getSimpleName());
    }



    //
    //  Fő nézet: telepített app-ok bellvasása
    //
    public static void generateAppList() {
        // másodperc
        // long currentTime = System.currentTimeMillis() / 1000;
        // 5 perc és újra olvas
        // - if ((currentTime - packageUpdateTime) > 300) {
        // -    startedAndroidApp = true;
        // - }
        //packageUpdateTime = currentTime;
        if (startedAndroidApp) {
            startedAndroidApp = false;
            allApplicationsList.clear();
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> apps = packageMan.queryIntentActivities(intent, PackageManager.GET_META_DATA);
            apps.sort(new ResolveInfo.DisplayNameComparator(packageMan));
            allApplicationsList.addAll(apps);
            allAppData = new String[apps.size()][3];
            String appName;
            String pkgName;
            String[] parts;
            String formerAppName = "";
            String formerPkgName = "";
            for (int i=0; i<apps.size(); i++) {
                ResolveInfo info = apps.get(i);
                appName = info.loadLabel(packageMan).toString();
                pkgName= info.activityInfo.packageName;
                parts = pkgName.split("\\.");
                if (parts.length < 2) {
                    pkgName = "0";
                } else {
                    pkgName = parts[1];
                }
                if (appName.equals(formerAppName)) {
                    appName = appName + " (" + pkgName + ")";
                    if (i > 0) {
                        allAppData[i - 1][0] = formerAppName + " (" + formerPkgName + ")";
                    }
                }
                formerAppName = appName;
                formerPkgName = pkgName;
                allAppData[i][0] = appName;
                allAppData[i][1] = info.activityInfo.packageName;
                allAppData[i][2] = info.activityInfo.name;
            }
            // végrehajtási idő
            // currentTime = (System.currentTimeMillis() / 1000) - currentTime;
            //syslog(String.valueOf(currentTime));
            // ! kiírás:  systemMessage(String.valueOf(currentTime));
        }
    }



    //
    //  Fő nézet: beállítások betöltése
    //
    public static void getSettingsMain(Context cont) {
        String tag;
        String val;
        String appName;
        homeAppName.clear();
        for (var i = 0; i <  HOME_APP_NUM; i++) {
            tag = SETTINGS_APP_TAG + i;
            if (sharedPreferences.contains(tag)) {
                val = sharedPreferences.getString(tag, null);
                if (val != null) {
                    for (String[] allAppDatum : allAppData) {
                        appName = allAppDatum[0];
                        if (appName.equals(val)) {
                            homeAppName.add(appName);
                        }
                    }
                }
            }
        }
        favAppName.clear();
        for (var i = 0; i < FAV_APP_NUM; i++) {
            tag = SETTINGS_FAV_APP_TAG + i;
            if (sharedPreferences.contains(tag)) {
                val = sharedPreferences.getString(tag, "");
                if (!val.isEmpty()) {
                    for (String[] allAppDatum : allAppData) {
                        appName = allAppDatum[0];
                        if (appName.equals(val)) {
                            favAppName.add(appName);
                        }
                    }
                }
            }
        }

        if (sharedPreferences.contains(SETTINGS_ADAPTIVE_ICON_TAG)) {
            val = sharedPreferences.getString(SETTINGS_ADAPTIVE_ICON_TAG, null);
            if (val != null) {
                adaptiveIcon = !val.equals("0");
            }
        }

        int buttonId = 0;
        if (sharedPreferences.contains(SETTINGS_ADAPTIVE_ICON_COLOR_TAG)) {
            buttonId = sharedPreferences.getInt(SETTINGS_ADAPTIVE_ICON_COLOR_TAG, Integer.parseInt("0"));
        }
        if (buttonId == R.id.btnRed) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.red);
        }
        if (buttonId == R.id.btnWhite) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.white);
        }
        if (buttonId == R.id.btnBlack) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.black);
        }
        if (buttonId == R.id.btnGray) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.gray);
        }
        if (buttonId == R.id.btnBlue) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.blue);
        }
        if (buttonId == R.id.btnGreen) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.green);
        }
        if (buttonId == R.id.btnPurple) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.purple);
        }
        if (buttonId == R.id.btnOlive) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.olive);
        }
        if (buttonId == R.id.btnLevander) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.levander);
        }
        if (buttonId == R.id.btnLightblue) {
            adaptiveIconColor = ContextCompat.getColor(cont, R.color.lightblue);
        }
        if (adaptiveIconColor == 0) {
            adaptiveIconColor = defaultIconColor;
        }

        if (sharedPreferences.contains(SETTINGS_SYS_ICON_TAG)) {
            val = sharedPreferences.getString(SETTINGS_SYS_ICON_TAG, null);
            if (val != null) {
                homeSysIcon = !val.equals("0");
            }
        }

        if (sharedPreferences.contains(SETTINGS_HOME_ICON_TAG)) {
            val = sharedPreferences.getString(SETTINGS_HOME_ICON_TAG, null);
            if (val != null) {
                homeStartAppIcon = !val.equals("0");
            }
        }

        if (sharedPreferences.contains(SETTINGS_TEXT_COLOR_MODE_TAG)) {
            val = sharedPreferences.getString(SETTINGS_TEXT_COLOR_MODE_TAG, null);
            if (val != null) {
                textColorMode = !val.equals("0");
            }
        }

        if (sharedPreferences.contains(SETTINGS_DARK_MODE_TAG)) {
            val = sharedPreferences.getString(SETTINGS_DARK_MODE_TAG, null);
            if (val != null) {
                darkMode = !val.equals("0");
            }
        }

        if (sharedPreferences.contains(SETTINGS_SHOW_ARROWS_TAG)) {
            val = sharedPreferences.getString(SETTINGS_SHOW_ARROWS_TAG, null);
            if (val != null) {
                showArrows = !val.equals("0");
            }
        }

        if (sharedPreferences.contains(SETTINGS_SHOW_MAIN_CONTROL_ICONS)) {
            val = sharedPreferences.getString(SETTINGS_SHOW_MAIN_CONTROL_ICONS, null);
            if (val != null) {
                showMainIcons = !val.equals("0");
            }
        }

        if (sharedPreferences.contains(SETTINGS_EXTRA_TOOLS)) {
            val = sharedPreferences.getString(SETTINGS_EXTRA_TOOLS, null);
            if (val != null) {
                extraTools = !val.equals("0");
            }
        }

        if (sharedPreferences.contains(SETTINGS_ONE_COLUMN_FAVORITES_TAG)) {
            val = sharedPreferences.getString(SETTINGS_ONE_COLUMN_FAVORITES_TAG, null);
            if (val != null) {
                onecolFavorites = !val.equals("0");
            }
        }


        if (sharedPreferences.contains(SETTINGS_URL_PRIVATEAI_TAG)) {
            val = sharedPreferences.getString(SETTINGS_URL_PRIVATEAI_TAG, null);
            if (val != null) {
                privateAIUrl = val;
            }
        }

        if (sharedPreferences.contains(SETTINGS_URL_SEARCH_TAG)) {
            val = sharedPreferences.getString(SETTINGS_URL_SEARCH_TAG, null);
            if (val != null) {
                privateSearchUrl = val;
            }
        }

        if (sharedPreferences.contains(SETTINGS_WEATHER_URL_TAG)) {
            val = sharedPreferences.getString(SETTINGS_WEATHER_URL_TAG, null);
            if (val != null) {
                weatherUrl = val;
            }
        }

        if (sharedPreferences.contains(SETTINGS_BACKGROUND_IMAGE_TAG)) {
            val = sharedPreferences.getString(SETTINGS_BACKGROUND_IMAGE_TAG, null);
            if (val != null) {
                backgroundImage = val;
            }
        }
    }



    //
    //  Billentyűzet elrejtése
    //
    public static void hideKeyboard(Activity act) {
        View v = act.getCurrentFocus();
        if (v != null) {
            InputMethodManager im = (InputMethodManager) act.getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(), 0);
            v.clearFocus();
        }
    }



    //
    //  Fő nézet: eszköz zárolása
    //
    public static void lockApp(Context cont) {
        LDAccessibility service = LDAccessibility.getInstance();
        if (service != null) {
            service.lockScreen();
        } else {
            systemMessage(cont.getString(R.string.error_lock));
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            cont.startActivity(intent);
            startedAndroidApp = true;
        }
    }


    //
    //
    //
    public static boolean startNewTaskActivity(Context cont, String pkg, String cls) {
        //Intent intent = new Intent();
        try {
            Intent intent = cont.getPackageManager().getLaunchIntentForPackage(pkg);
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cont.startActivity(intent);
            } else {
                Intent intent2 = new Intent();
                intent2.setClassName(pkg, cls);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cont.startActivity(intent2);
            }
        } catch (Exception e) {
            systemMessage(cont.getString(R.string.error_startapp));
            return false;
        }
        return true;
    }



    //
    // activity indítás
    //
    public static boolean startLocalActivity(Context cont, int marker) {
        switch (marker) {
            case START_ACT_APPLIST:
                try {
                    cont.startActivity(new Intent(cont, AppListActivity.class));
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_ACT_FAVAPP:
                try {
                cont.startActivity(new Intent(cont, FavoritesActivity.class));
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_ACT_HELP:
                try {
                cont.startActivity(new Intent(cont, HelpActivity.class));
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_ACT_SETTINGS:
                try {
                cont.startActivity(new Intent(cont, SettingsActivity.class));
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_ACT_WIDGET:
                try {
                cont.startActivity(new Intent(cont, WidgetActivity.class));
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_ACT_FAVAPP_SEL:
                try {
                cont.startActivity(new Intent(cont, FavoritesAddAppActivity.class));
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_ACT_APPLIST_SEL:
                try {
                cont.startActivity(new Intent(cont, AddAppActivity.class));
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
        }
        return true;
    }



    //
    // activity indítás
    //
    public static boolean startInsideFunctionApp(Context cont, int marker) {
        switch (marker) {
            case START_PHONE:
                try {
                    Intent dialIn = new Intent(Intent.ACTION_DIAL, null);
                    List<ResolveInfo> appDL = cont.getPackageManager().queryIntentActivities(dialIn, 0);
                    ResolveInfo appR = appDL.get(0);
                    String pkg = appR.activityInfo.packageName;
                    if (!pkg.isEmpty()) {
                        dialIn = new Intent(cont.getPackageManager().getLaunchIntentForPackage(pkg));
                    }
                    dialIn.addCategory(Intent.CATEGORY_DEFAULT);
                    dialIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(dialIn);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_MAIL:
                try {
                    Intent mailIn = cont.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                    if (mailIn == null) {
                        mailIn = new Intent(Intent.ACTION_MAIN, null);
                        mailIn.addCategory(Intent.CATEGORY_APP_EMAIL);
                    }
                    mailIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(mailIn);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_BROWSER:
                try {
                    Intent broIn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"));
                    broIn.addCategory(Intent.CATEGORY_DEFAULT);
                    broIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(broIn);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_BROWSER_LONG:
                try {
                    Intent broIn;
                    if (privateSearchUrl.isEmpty()) {
                        broIn = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"));
                    } else {
                        broIn = new Intent(Intent.ACTION_VIEW, Uri.parse(privateSearchUrl));
                    }
                    broIn.addCategory(Intent.CATEGORY_DEFAULT);
                    broIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(broIn);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_CAMERA:
                try {
                    Intent camIn = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    camIn.addCategory(Intent.CATEGORY_DEFAULT);
                    camIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(camIn);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_SEARCH:
                try {
                    Intent se = new Intent(SearchManager.INTENT_ACTION_GLOBAL_SEARCH);
                    se.addCategory(Intent.CATEGORY_DEFAULT);
                    se.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(se);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_SEARCH_LONG:
                try {
                    Intent lIntent;
                    if (privateSearchUrl.isEmpty()) {
                        lIntent = new Intent(SearchManager.INTENT_ACTION_GLOBAL_SEARCH);
                    } else {
                        lIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(privateSearchUrl));
                    }
                    lIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(lIntent);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_WEATHER:
                try {
                    Intent searchIntent;
                    if (weatherUrl.contains("://")) {
                        searchIntent = new Intent(Intent.ACTION_VIEW);
                        searchIntent.setData(Uri.parse(weatherUrl));
                        searchIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    } else {
                        searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                        searchIntent.putExtra(SearchManager.QUERY, weatherUrl + "\n");
                        searchIntent.setComponent(new ComponentName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity"));
                        //searchIntent.setPackage("com.google.android.googlequicksearchbox");
                    }
                    searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(searchIntent);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_SETTINGS:
                startLocalActivity(cont, START_ACT_SETTINGS);
                break;
            case START_SETTINGS_LONG:
                try {
                    Intent launchIntent = new Intent(Settings.ACTION_SETTINGS);
                    launchIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(launchIntent);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_AI:
                try {
                    Intent intent;
                    if (privateAIUrl != null && !privateAIUrl.trim().isEmpty()) {
                        String url = privateAIUrl.trim();
                        if (!url.contains("://")) {
                            url = "https://" + url;
                        }
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    } else {
                        intent = new Intent(Intent.ACTION_ASSIST);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(intent);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_AI_LONG:
                try {
                    String appName;
                    for (String[] allAppDatum : allAppData) {
                        appName = allAppDatum[0];
                        syslog(appName);
                        if (appName != null && appName.toLowerCase().contains(AI_NAME_STRING.toLowerCase())) {
                            String pkg = allAppDatum[1];
                            String cls = allAppDatum[2];
                            if (startNewTaskActivity(cont, pkg, cls)) {
                                startedAndroidApp = true;
                            }
                            break;
                        }
                    }
                    if (!startedAndroidApp) {
                        Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        cont.startActivity(intent);
                        startedAndroidApp = true;
                    }
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_DISCOVERY:
                startNewTaskActivity(cont, "com.google.android.googlequicksearchbox", "");
                break;
            case START_MAP:
                try {
                    Uri mapUri = Uri.parse("geo:0,0");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                    mapIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(mapIntent);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_CALENDAR:
                try {
                    Intent cal = new Intent(Intent.ACTION_MAIN);
                    cal.addCategory(Intent.CATEGORY_APP_CALENDAR);
                    cal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(cal);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
            case START_CLOCK:
                try {
                    Intent cl = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                    cl.addCategory(Intent.CATEGORY_DEFAULT);
                    cl.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    cont.startActivity(cl);
                    startedAndroidApp = true;
                } catch (Exception e) {
                    systemMessage(cont.getString(R.string.error_startapp));
                }
                break;
        }
        return true;
    }



    //
    // verzió ellenőrzése
    //
    public void versionCheck() {
        // régi beállítások törlése
        // - String valx = sharedPreferences.getString(SETTINGS_BACKGROUND_IMAGE, "");
        // - if (!valx.isEmpty()) {
        // -    var settings = sharedPreferences.edit();
        // -    settings.remove(SETTINGS_BACKGROUND_IMAGE);
        // -    settings.apply();
        // - }

        //newInstall();
        String val = sharedPreferences.getString(SETTINGS_VERSION_TAG, "");
        if (val.isEmpty()) {
            newInstall();
            //systemMessage(getString(R.string.new_install));
        } else {
            if (!val.equals(TXT_VERSION)) {
                updateInstall();
                //systemMessage(getString(R.string.update_install));
            }
        }
    }



    //
    // Első indítás
    //
    public void newInstall() {
        //
        // első indítás: alap beállítások
        //
        // feladatok
        var settings = TxTLauncherApp.sharedPreferences.edit();
        settings.putString(SETTINGS_VERSION_TAG, TXT_VERSION);
        settings.putString(SETTINGS_SYS_ICON_TAG, "0");
        settings.putString(SETTINGS_HOME_ICON_TAG, "0");
        settings.putString(SETTINGS_ADAPTIVE_ICON_TAG, "0");
        settings.putString(SETTINGS_ONE_COLUMN_FAVORITES_TAG, "0");
        settings.putString(SETTINGS_TEXT_COLOR_MODE_TAG, "0");
        settings.putString(SETTINGS_SHOW_ARROWS_TAG, "0");
        settings.putString(SETTINGS_SHOW_MAIN_CONTROL_ICONS, "1");
        settings.putString(SETTINGS_EXTRA_TOOLS, "0");
        if (darkMode) {
            settings.putString(SETTINGS_DARK_MODE_TAG, "1");
        } else {
            settings.putString(SETTINGS_DARK_MODE_TAG, "0");
        }
        settings.apply();

        verNewInstall = true;
    }



    //
    // Frissítés utáni első indítás
    //
    public void updateInstall() {
        //
        // esetleges beállítások és egyebek módosítása az új verzióhoz
        //
        // feladatok
        var settings = TxTLauncherApp.sharedPreferences.edit();
        settings.putString(SETTINGS_VERSION_TAG, TXT_VERSION);
        settings.apply();

        verUpdate = true;
    }



    //
    // üzenet
    //
    public static void verMessage(Context cont) {
        if (verNewInstall) {
            // üzenet
            AlertDialog.Builder builder = new AlertDialog.Builder(cont);
            String msg = cont.getString(R.string.news_firstrun);
            builder.setTitle(cont.getString(R.string.news_firstrun_title))
                    .setMessage(msg)
                    .setPositiveButton(cont.getString(R.string.button_next), (dialog,
                                      id) -> cont.startActivity(new Intent(cont, HelpActivity.class)));
            AlertDialog alert = builder.create();
            alert.show();
        }
        if (verUpdate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(cont);
            String msg = cont.getString(R.string.news_update);
            builder.setTitle(cont.getString(R.string.news_update_title))
                    .setMessage(msg)
                    .setPositiveButton(cont.getString(R.string.button_next), (dialog,
                                      id) -> cont.startActivity(new Intent(cont, HelpActivity.class)));
            AlertDialog alert = builder.create();
            alert.show();
        }
    }



    //
    //  Fő nézet: rendszerüzenet
    //
    public static void systemMessage(String mtext) {
        Toast.makeText(AppContext, mtext, Toast.LENGTH_SHORT).show();
    }



    //
    //  Fő nézet: degug log üzenet
    //
    public static void syslog(String mtext) {
        if (developerMode) {
            Log.d(DEBUG_TAG, mtext);
        }
    }


}
