package com.example.test.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.checkSelfPermissionCompat(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.checkSelfPermissionCompat(permissions: List<String>): Boolean {
    return permissions.all { permission ->
        checkSelfPermissionCompat(permission)
    }
}

fun Activity.shouldShowRequestPermissionRationaleCompat(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun Activity.requestPermissionsCompat(permissionsArray: Array<String>, requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}

const val PACKAGE = "package"
const val COLON = ":"
fun Context.openApplicationSetting() {
    val packageUri = Uri.parse("$PACKAGE$COLON$packageName")
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

const val PERMISSIONS_REQ_CODE = 1
fun Activity.requestAllPermission() {
    val permissions = mutableListOf<String>()
    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
    permissions.add(Manifest.permission.ACTIVITY_RECOGNITION)
    permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    requestPermissionsCompat(permissions.toTypedArray(), PERMISSIONS_REQ_CODE)
}

const val PERMISSION_LOCATION_REQ_CODE = 2
fun Activity.requestLocationPermission() {
    val permissions = mutableListOf<String>()
    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
    permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    requestPermissionsCompat(permissions.toTypedArray(), PERMISSION_LOCATION_REQ_CODE)
}

fun Context.hasLocationPermission(): Boolean {
    val hasForegroundPermission = checkSelfPermissionCompat(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val hasBackgroundPermission: Boolean = checkSelfPermissionCompat(
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    return hasForegroundPermission && hasBackgroundPermission
}

fun Context.hasForegroundLocationPermission(): Boolean {
    return checkSelfPermissionCompat(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun Context.hasRecognitionPermission(): Boolean {
    return checkSelfPermissionCompat(Manifest.permission.ACTIVITY_RECOGNITION)
}

const val ACTIVITY_RECOGNITION_REQ_CODE = 3
fun Activity.requestRecognitionPermission() {
    val permissionRationale = shouldShowRequestPermissionRationaleCompat(
        Manifest.permission.ACTIVITY_RECOGNITION
    )
    if (!permissionRationale) {
        requestPermissionsCompat(
            arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
            ACTIVITY_RECOGNITION_REQ_CODE
        )
        return
    }
    //showRationalDialog(this)
}
