package com.example.androiddevelopment.domain.utils

import android.content.Context

class NotificationPreferences(context: Context) {
    private val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun areNotificationsEnabled(): Boolean {
        return sharedPref.getBoolean("notifications_enabled", false)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPref.edit().putBoolean("notifications_enabled", enabled).apply()
    }
}