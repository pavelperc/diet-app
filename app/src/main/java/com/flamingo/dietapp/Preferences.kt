package com.flamingo.dietapp

import android.content.Context

class Preferences(val context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var useTestRepository
        get() = prefs.getBoolean("use_test_repository", false)
        set(value) {
            prefs.edit().putBoolean("use_test_repository", value).apply()
        }
}