package com.wswdteam.txtlauncher;

import static com.wswdteam.txtlauncher.MainActivity.getDrawableTxT;
import static com.wswdteam.txtlauncher.TxTLauncherApp.adaptiveIconColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.allAppData;
import static com.wswdteam.txtlauncher.TxTLauncherApp.allApplicationsList;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultBackGroundColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultFontSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultLetterColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultPlusFontSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.defaultTextColor;
import static com.wswdteam.txtlauncher.TxTLauncherApp.hideKeyboard;
import static com.wswdteam.txtlauncher.TxTLauncherApp.homeStartAppIcon;
import static com.wswdteam.txtlauncher.TxTLauncherApp.iconCache;
import static com.wswdteam.txtlauncher.TxTLauncherApp.iconSize;
import static com.wswdteam.txtlauncher.TxTLauncherApp.packageMan;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startNewTaskActivity;
import static com.wswdteam.txtlauncher.TxTLauncherApp.syslog;
import static com.wswdteam.txtlauncher.TxTLauncherApp.textColorMode;
import static com.wswdteam.txtlauncher.TxTLauncherApp.startedAndroidApp;

import android.annotation.SuppressLint;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

//
// App lista előkészítése
//
public class AppListActivity extends AppCompatActivity {

    final ArrayList<String> allappList = new ArrayList<>();
    final ArrayList<String> appList = new ArrayList<>();
    final ArrayList<String> appPackList = new ArrayList<>();
    final ArrayList<String> letterList = new ArrayList<>();

    final ArrayList<String> allappListBackup = new ArrayList<>();
    final ArrayList<String> appListBackup = new ArrayList<>();
    final ArrayList<String> appPackListBackup = new ArrayList<>();
    private GestureDetector gestureDetector;


    @SuppressLint({"MissingInflatedId", "PrivateResource"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.allAppLayout), (v, insets) -> {
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
        // vissza gomb szine
        ImageView imv = findViewById(R.id.quitAllAppListButton);
        imv.setColorFilter(defaultTextColor);

        @SuppressLint("UseCompatLoadingForDrawables") Drawable appI = getDrawable(R.drawable.arrow_back);
        if (appI != null) {
            appI.setBounds(0, 0, (int) defaultFontSize, (int) defaultFontSize);
            appI.setTint(defaultTextColor);
            //ImageView iv = findViewById(R.id.quitAllAppListButton);
            ViewGroup.LayoutParams layoutParams = imv.getLayoutParams();
            layoutParams.width = layoutParams.width + (int) defaultPlusFontSize;
            layoutParams.height = layoutParams.height + (int) defaultPlusFontSize;
            imv.setLayoutParams(layoutParams);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    //
    // App lista indítása
    //
    @Override
    protected void onStart() {
        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.enter_from_bottom, R.anim.exit_to_top);
        overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.enter_from_top, R.anim.exit_to_bottom);
        super.onStart();

        appListView();
        SearchView sv = findViewById(R.id.allAppListSearch);
        sv.setQuery("", false);
        sv.setIconified(true);
        sv.setOnClickListener(v -> sv.setIconified(false));

        ListView scv = findViewById(R.id.allAppListTable);
        scv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    hideKeyboard(AppListActivity.this);
                }
            }
        });

        syslog(getString(R.string.started_activity) + ": "+ this.getClass().getSimpleName());
    }


    //
    // App lista leállítása
    //
    @Override
    public void onStop() {
        super.onStop();
    }



    //
    // App lista nézet készítése (Optimalizálva + Magyar ékezetkezeléssel)
    //
    @SuppressLint("QueryPermissionsNeeded")
    public void appListView() {
        final ListView appTable = findViewById(R.id.allAppListTable);
        final ListView letterTable = findViewById(R.id.allLetterListTable);

        final java.util.Map<String, ResolveInfo> appIconMap = new java.util.HashMap<>();
        if (homeStartAppIcon && allAppData != null && allApplicationsList != null) {
            for (int i = 0; i < allAppData.length; i++) {
                if (i < allApplicationsList.size()) {
                    appIconMap.put(allAppData[i][0], allApplicationsList.get(i));
                }
            }
        }

        final var adapterallapp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                String appN = getItem(position);
                View row = super.getView(position, convertView, parent);
                TextView tvt = row.findViewById(android.R.id.text1);
                if (appN != null) {
                    if (appN.isEmpty()) {
                        tvt.setText("");
                        tvt.setCompoundDrawablesRelative(null, null, null, null);
                    } else {
                        if (appN.length() == 1) {
                            tvt.setTextColor(defaultLetterColor);
                            tvt.setText(appN);
                            tvt.setCompoundDrawablesRelative(null, null, null, null);
                        } else {
                            if (textColorMode) {
                                tvt.setTextColor(adaptiveIconColor);
                            } else {
                                tvt.setTextColor(defaultTextColor);
                            }
                            tvt.setText(appN);
                            if (homeStartAppIcon) {
                                String cleanAppName = appN.trim();
                                ResolveInfo info = appIconMap.get(cleanAppName);
                                if (info != null) {
                                    int padding = iconSize / 2;
                                    tvt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

                                    // Gyorsítótár kulcs generálása a csomagnév alapján
                                    String cacheKey = info.activityInfo.packageName;
                                    Drawable ic = iconCache.get(cacheKey);

                                    if (ic == null) {
                                        // Ha még nincs a memóriában, legyártjuk és elmentjük a cache-be
                                        Drawable appI = info.loadIcon(packageMan);
                                        ic = getDrawableTxT(row, appI, iconSize);
                                        iconCache.put(cacheKey, ic);
                                    }

                                    tvt.setCompoundDrawablesRelative(ic, null, null, null);
                                    tvt.setCompoundDrawablePadding(padding);
                                } else {
                                    tvt.setCompoundDrawablesRelative(null, null, null, null);
                                }
                            }
                        }
                    }
                }
                return row;
            }
        };

        final var adapterletters = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, letterList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                String appN = getItem(position);
                View row = super.getView(position, convertView, parent);
                TextView tvt = row.findViewById(android.R.id.text1);
                if (appN != null && appN.length() == 1) {
                    tvt.setTextColor(defaultLetterColor);
                    tvt.setText(appN);
                    tvt.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    tvt.setCompoundDrawablePadding(0);
                    ViewGroup.LayoutParams params = tvt.getLayoutParams();
                    int totalH = parent.getHeight();
                    if (totalH > 0) {
                        int itemC = getCount();
                        int goodItemHeight = (int) (48 * parent.getContext().getResources().getDisplayMetrics().density);
                        int maxItemFit = totalH / goodItemHeight;
                        if (itemC > maxItemFit) {
                            params.height = totalH / itemC;
                        } else {
                            params.height = goodItemHeight;
                        }
                    }
                    tvt.setLayoutParams(params);
                }
                return row;
            }
        };

        appList.clear();
        appPackList.clear();
        allappList.clear();
        letterList.clear();

        char first = '\0';
        boolean firstapp = true;

        for (String[] allAppDatum : allAppData) {
            String appName = allAppDatum[0];
            String pName = allAppDatum[1];

            if (appName == null || appName.isEmpty()) continue;

            char currentFirst = Character.toUpperCase(appName.charAt(0));

            if (first != currentFirst) {
                first = currentFirst;
                String s = String.valueOf(first);
                if (firstapp) {
                    firstapp = false;
                }
                letterList.add(s);
                appList.add(s);
                appPackList.add("");
                allappList.add(null);
            }
            allappList.add(allAppDatum[2]);
            appList.add(appName + " ");
            appPackList.add(pName);
        }

        letterTable.setAdapter(adapterletters);
        adapterletters.notifyDataSetChanged();
        appTable.setAdapter(adapterallapp);
        adapterallapp.notifyDataSetChanged();

        appListBackup.clear();
        allappListBackup.clear();
        appPackListBackup.clear();
        appListBackup.addAll(appList);
        allappListBackup.addAll(allappList);
        appPackListBackup.addAll(appPackList);

        appTable.setOnItemClickListener((parent, view, position, id) -> {
            String selectedL = (String) (appTable.getItemAtPosition(position));
            if (selectedL == null || selectedL.length() == 1) return;
            String cleanSelected = selectedL.trim();
            for (String[] allAppDatum : allAppData) {
                String appName = allAppDatum[0];
                if (cleanSelected.equals(appName)) {
                    String pkg = allAppDatum[1];
                    String cls = allAppDatum[2];
                    if (startNewTaskActivity(this, pkg, cls)) {
                        startedAndroidApp = true;
                        this.finish();
                    }
                    break;
                }
            }
        });

        letterTable.setOnItemClickListener((parent, view, position, id) -> {
            String selectedL = (String) (letterTable.getItemAtPosition(position));
            ListView lv = findViewById(R.id.allAppListTable);
            for (int i = 0; i < appList.size(); i++) {
                if (selectedL.equals(appList.get(i))) {
                    lv.setSelection(i);
                    break;
                }
            }
        });

        SearchView sv = findViewById(R.id.allAppListSearch);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String nt = newText.toLowerCase(Locale.getDefault());
                appList.clear();
                allappList.clear();
                appPackList.clear();
                ListView lv = findViewById(R.id.allLetterListTable);
                if (nt.isEmpty()) {
                    lv.setVisibility(View.VISIBLE);
                    appList.addAll(appListBackup);
                    allappList.addAll(allappListBackup);
                    appPackList.addAll(appPackListBackup);
                } else {
                    lv.setVisibility(View.INVISIBLE);
                    for (String[] allAppDatum : allAppData) {
                        String appName = allAppDatum[0];
                        String pName = allAppDatum[1];
                        String aName = allAppDatum[2];
                        if (appName.toLowerCase(Locale.getDefault()).contains(nt)) {
                            appList.add(appName);
                            appPackList.add(pName);
                            allappList.add(aName);
                        }
                    }
                }
                if (appList.isEmpty()) {
                    appList.add("");
                }
                adapterallapp.notifyDataSetChanged();
                return true;
            }
        });
    }



    //
    // App lista kilépés
    //
    public void closeAppViewButton(View view) {
        this.finish();
    }

}