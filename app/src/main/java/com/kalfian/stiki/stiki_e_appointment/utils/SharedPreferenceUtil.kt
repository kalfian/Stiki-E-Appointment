package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


object SharedPreferenceUtil {

    private const val PREFS_NAME = "prefs"

    fun store(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }


    fun retrieve(context: Context, key: String, defaultValue: String = ""): String {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue) ?: ""
    }

    fun retrieve(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "$defaultValue")
            ?.let { Helper.stringToBoolean(it) }
            ?: defaultValue
    }

    fun clear(context: Context, key: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearAll(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}