package com.wswdteam.txtlauncher;

import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_ADAPTIVE_ICON_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_DARK_MODE_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_EXTRA_TOOLS_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_FOCUS_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_HOME_ICON_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_ONE_COLUMN_FAVORITES_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_SHOW_ARROWS_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_SHOW_MAIN_CONTROL_ICONS_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_SYS_ICON_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_TEXT_COLOR_MODE_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.SETTINGS_VERSION_TAG;
import static com.wswdteam.txtlauncher.TxTLauncherApp.TXT_VERSION;
import static com.wswdteam.txtlauncher.TxTLauncherApp.darkMode;
import static com.wswdteam.txtlauncher.TxTLauncherApp.sharedPreferences;
import static com.wswdteam.txtlauncher.TxTLauncherApp.verNewInstall;
import static com.wswdteam.txtlauncher.TxTLauncherApp.verUpdate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

public class VersionManagement {
    //
// verzió ellenőrzése
//
    public static void versionCheck() {
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
    public static void newInstall() {
        //
        // első indítás: alap beállítások
        //
        // feladatok
        var settings = sharedPreferences.edit();
        settings.putString(SETTINGS_VERSION_TAG, TXT_VERSION);
        settings.putString(SETTINGS_SYS_ICON_TAG, "0");
        settings.putString(SETTINGS_HOME_ICON_TAG, "0");
        settings.putString(SETTINGS_ADAPTIVE_ICON_TAG, "0");
        settings.putString(SETTINGS_ONE_COLUMN_FAVORITES_TAG, "0");
        settings.putString(SETTINGS_TEXT_COLOR_MODE_TAG, "0");
        settings.putString(SETTINGS_SHOW_ARROWS_TAG, "0");
        settings.putString(SETTINGS_SHOW_MAIN_CONTROL_ICONS_TAG, "1");
        settings.putString(SETTINGS_EXTRA_TOOLS_TAG, "0");
        settings.putString(SETTINGS_FOCUS_TAG, "0");
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
    public static void updateInstall() {
        //
        // esetleges beállítások és egyebek módosítása az új verzióhoz
        //
        // feladatok
        var settings = sharedPreferences.edit();
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

}