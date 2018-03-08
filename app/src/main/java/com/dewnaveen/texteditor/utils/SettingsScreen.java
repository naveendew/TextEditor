package com.dewnaveen.texteditor.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import java.lang.ref.Reference;

/**
 * Created by naveendewangan on 07/03/18.
 */

public class SettingsScreen {

    Context context;

    public SettingsScreen(Context context) {
        this.context = context;
    }

    private void _showSettingScreen(String intentStr) {
        try {
            Intent intent = new Intent(intentStr);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            showtoast(e.toString(), true);
        }
    }

    private void showtoast(String s, boolean b) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    private void showSettingScreen() {
        _showSettingScreen("android.settings.SETTINGS");
    }

    public void showAPNScreen() {
        _showSettingScreen("android.settings.APN_SETTINGS");
    }

    public void showLocationScreen() {
        _showSettingScreen("android.settings.LOCATION_SOURCE_SETTINGS");
    }

    public void showSecurityScreen() {
        _showSettingScreen("android.settings.SECURITY_SETTINGS");
    }

    public void showWifiScreen() {
        _showSettingScreen("android.settings.WIFI_SETTINGS");
    }

    public void showBluetoothScreen() {
        _showSettingScreen("android.settings.BLUETOOTH_SETTINGS");
    }

    public void showDateScreen() {
        _showSettingScreen("android.settings.DATE_SETTINGS");
    }

    public void showSoundScreen() {
        _showSettingScreen("android.settings.SOUND_SETTINGS");
    }

    public void showDisplayScreen() {
        _showSettingScreen("android.settings.DISPLAY_SETTINGS");
    }

    public void showApplicationScreen() {
        _showSettingScreen("android.settings.APPLICATION_SETTINGS");
    }

    public void showNetworkSettingScreen() {
        showDataRoamingScreen();
    }

    public void showNetworkOperatorScreen() {
        if (android.os.Build.VERSION.SDK_INT > 15) {
            _showSettingScreen("android.settings.NETWORK_OPERATOR_SETTINGS");
        } else {
            Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    private void showDataRoamingScreen() {
        if (android.os.Build.VERSION.SDK_INT > 15) {
            _showSettingScreen("android.settings.DATA_ROAMING_SETTINGS");
        } else {
            Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
            ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
            intent.setComponent(cName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void showDataMobileScreen() {
        if (android.os.Build.VERSION.SDK_INT > 15) {
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);//android.provider.Settings.ACTION_SETTINGS //Intent.ACTION_MAIN
            intent.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            showDataRoamingScreen();
        }
    }

    public void showNotificationScreen() {
        _showSettingScreen("android.settings.NOTIFICATION_SETTINGS");
    }

    public void showBatterySaverScreen() {
        _showSettingScreen("android.settings.BATTERY_SAVER_SETTINGS");
    }

    public void showNfcScreen() {
        _showSettingScreen("android.settings.NFC_SETTINGS");
    }

    public void showInternalStorageScreen() {
        _showSettingScreen("android.settings.INTERNAL_STORAGE_SETTINGS");
    }

    public void showDictionarySettingScreen() {
        _showSettingScreen("android.settings.USER_DICTIONARY_SETTINGS");
    }

    public void showManageApplicationsScreen() {
        _showSettingScreen("android.settings.MANAGE_APPLICATIONS_SETTINGS");
    }

    public void showManageAllApplicationsScreen() {
        _showSettingScreen("android.settings.MANAGE_ALL_APPLICATIONS_SETTINGS");
    }

    public void showMemoryCardScreen() {
        _showSettingScreen("android.settings.MEMORY_CARD_SETTINGS");
    }

    public void showAirPlaneScreen() {
        if (android.os.Build.VERSION.SDK_INT > 16) {
            if (Build.MANUFACTURER.equalsIgnoreCase("Lenovo")) {
                showSettingScreen();
            } else {
                _showSettingScreen("android.settings.WIRELESS_SETTINGS");
            }
        } else {
            _showSettingScreen("android.settings.AIRPLANE_MODE_SETTINGS");
        }
    }

    public void showWirelessScreen() {
        _showSettingScreen("android.settings.WIRELESS_SETTINGS");
    }

    public void showWifiScreenSafe() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
}