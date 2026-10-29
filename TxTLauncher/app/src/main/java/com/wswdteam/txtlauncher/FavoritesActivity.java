package com.wswdteam.txtlauncher;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.wswdteam.txtlauncher.TxTLauncherApp.START_ACT_APPLIST;
import static com.wswdteam.txtlauncher.TxTLauncherApp.FAV_APP_NUM;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.allAppData;
import static com.wswdteam.txtlauncher.TxTLauncherApp.allApplicationsList;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultBackGroundColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultFontSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultPlusFontSizeTitle;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultTextColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.extraTools;
import static com.wswdteam.txtlauncher.TxTLauncherApp.homeStartAppIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.iconCache;
import static com.wswdteam.txtlauncher.TxTLauncherApp.iconSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.onecolFavorites;
import static com.wswdteam.txtlauncher.TxTLauncherApp.packageMan;
import static com.wswdteam.txtlauncher.TxTLauncherApp.setIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startLocalActivity;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startNewTaskActivity;
import static com.wswdteam.txtlauncher.TxTLauncherApp.syslog;
import static com.wswdteam.txtlauncher.TxTLauncherApp.textColorMode;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startedAndroidApp;

import android.annotation.SuppressLint;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;


//
// Kedvencek lista és indítás
//
public class FavoritesActivity extends AppCompatActivity {
    public static List<String> favAppName = new ArrayList<>();
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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
                finish();
            }
            @Override
            public void onDown() {
                finish();
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

        TextView tv = findViewById(R.id.favTitle);
        tv.setTextColor(defaultTextColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultFontSize + defaultPlusFontSizeTitle);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable appI = getDrawable(R.drawable.arrow_back);
        if (appI != null) {
            int ts = (int) defaultFontSize + (int) defaultPlusFontSizeTitle;
            appI.setBounds(0, 0, ts, ts);
            appI.setTint(defaultTextColor);
            tv.setCompoundDrawables(appI, null, null, null);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setCompoundDrawables(appI, null, null, null);
        }

        LinearLayout ll = findViewById(R.id.lLayoutDecor);
        if (extraTools) {
            ll.setVisibility(VISIBLE);
        } else {
            ll.setVisibility(INVISIBLE);
        }

        if (adaptiveIcon || textColorMode) {
            setIconColor(this, R.id.menuIconF, adaptiveIconColor);
        } else {
            setIconColor(this, R.id.menuIconF, defaultIconColor);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    //
    // Kedvenc app lista előkészítés
    //
    @Override
    public void onStart() {
        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.enter_from_top, R.anim.exit_to_bottom);
        overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.enter_from_bottom, R.anim.exit_to_top);
        super.onStart();

        setFavApp();
        syslog(getString(R.string.started_activity) + ": "+ this.getClass().getSimpleName());
    }


    //
    // Kedvenc app lista leállítása
    //
    @Override
    public void onStop() {
        super.onStop();
    }


    //
    //   Fő nézet: alapértelmezett gombok beállítása (Cache-elve)
    //
    public void setFavApp(){
        final ArrayList<String> appFList1 = new ArrayList<>();
        final ArrayList<String> appFList2 = new ArrayList<>();
        final ListView favTable1 = findViewById(R.id.favAppList1);
        final ListView favTable2 = findViewById(R.id.favAppList2);

        final java.util.Map<String, ResolveInfo> appIconMap = new java.util.HashMap<>();
        if (homeStartAppIcon && allAppData != null && allApplicationsList != null) {
            for (int i = 0; i < allAppData.length; i++) {
                if (i < allApplicationsList.size()) {
                    appIconMap.put(allAppData[i][0], allApplicationsList.get(i));
                }
            }
        }

        final var adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appFList1) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                String appN = getItem(position);
                View row = super.getView(position, convertView, parent);
                if (appN != null) {
                    TextView tvt = row.findViewById(android.R.id.text1);

                    if (homeStartAppIcon && !TextUtils.isEmpty(appN)) {
                        ResolveInfo app = appIconMap.get(appN);
                        if (app != null) {
                            int padding = iconSize / 2;
                            tvt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

                            // Gyorsítótár ellenőrzése a csomagnév alapján
                            String cacheKey = app.activityInfo.packageName;
                            Drawable iconToDisplay = iconCache.get(cacheKey);

                            if (iconToDisplay == null) {
                                // Ha nincs a cache-ben, legyártjuk és eltároljuk
                                Drawable appI = app.loadIcon(packageMan);
                                iconToDisplay = MainActivity.getDrawableTxT(row, appI, iconSize);
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

        final var adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appFList2) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                String appN = getItem(position);
                View row = super.getView(position, convertView, parent);
                if (appN != null) {
                    TextView tvt = row.findViewById(android.R.id.text1);

                    if (homeStartAppIcon && !TextUtils.isEmpty(appN)) {
                        ResolveInfo app = appIconMap.get(appN);
                        if (app != null) {
                            int padding = iconSize / 2;
                            tvt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

                            // Gyorsítótár ellenőrzése a csomagnév alapján
                            String cacheKey = app.activityInfo.packageName;
                            Drawable iconToDisplay = iconCache.get(cacheKey);

                            if (iconToDisplay == null) {
                                // Ha nincs a cache-ben, legyártjuk és eltároljuk
                                Drawable appI = app.loadIcon(packageMan);
                                iconToDisplay = MainActivity.getDrawableTxT(row, appI, iconSize);
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

        if (!onecolFavorites) {
            ListView tx1 = findViewById(R.id.favAppList2);
            tx1.setVisibility(View.VISIBLE);
            ListView tx2 = findViewById(R.id.favAppList1);
            tx2.setPadding(0, 0, 0, 0);
            double anum = Math.min(favAppName.size(), FAV_APP_NUM);
            double an = anum / 2;
            long halfA = Math.round(an);
            int halfApp = (int) halfA;
            for (var i = 0; i < halfApp; i++) {
                if (favAppName.size() > i && !favAppName.get(i).isEmpty()) {
                    appFList1.add(favAppName.get(i));
                }
            }
            for (var i = halfApp; i < FAV_APP_NUM; i++) {
                if (favAppName.size() > i && !favAppName.get(i).isEmpty()) {
                    appFList2.add(favAppName.get(i));
                }
            }
            if ((halfApp * 2) > favAppName.size()) {
                appFList2.add("");
            }
        } else {
            int dp = 30;
            int pixeldp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
            favTable1.setPadding(pixeldp, 0, 0, 0);
            ListView t2 = findViewById(R.id.favAppList2);
            t2.setVisibility(INVISIBLE);
            t2.setVisibility(GONE);
            for (var i = 0; i < FAV_APP_NUM; i++) {
                if (favAppName.size() > i && !favAppName.get(i).isEmpty()) {
                    appFList1.add(favAppName.get(i));
                }
            }
        }

        favTable1.setAdapter(adapter1);
        favTable2.setAdapter(adapter2);

        favTable1.setOnItemClickListener((parent, view, position, id) -> {
            String selectedP = (String) (favTable1.getItemAtPosition(position));
            launchApp(selectedP);
        });

        favTable2.setOnItemClickListener((parent, view, position, id) -> {
            String selectedP = (String) (favTable2.getItemAtPosition(position));
            launchApp(selectedP);
        });
    }
    private void launchApp(String selectedP) {
        if (TextUtils.isEmpty(selectedP)) return;
        for (String[] allAppDatum : allAppData) {
            if (selectedP.equals(allAppDatum[0])) {
                String pkg = allAppDatum[1];
                String cls = allAppDatum[2];
                if (startNewTaskActivity(this, pkg, cls)) {
                    startedAndroidApp = true;
                    this.finish();
                }
                break;
            }
        }
    }



    //
    // App indítása
    //
    public void startButtonApps(View v) {
        startLocalActivity(this, START_ACT_APPLIST);
        this.finish();
    }



    //
    // App kedvencek választás bezárása
    //
    public void closeFavAppButton(View view) {
        this.finish();
    }


}