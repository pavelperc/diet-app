package com.flamingo.dietapp.utils.commonUtils

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import org.json.JSONArray

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
fun Context.longToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun JSONArray.toList() = List(length()) { i -> getJSONObject(i) }

fun Context.dip(value: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), resources.displayMetrics).toInt()
