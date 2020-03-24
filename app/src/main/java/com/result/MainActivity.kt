package com.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * Issue:
     * 1) Cannot get result in callback, because [FragmentActivity.onRequestPermissionsResult] don't call super method ([ComponentActivity.onRequestPermissionsResult]).
     * So [ComponentActivity.mActivityResultRegistry] field cannot get result.
     *
     * 2) If VERSION.SDK_INT < 23, then [ActivityResultCallback] callback not called (see [ActivityResultRegistry.invoke] implementation in [ComponentActivity]).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launcher = prepareCall(ActivityResultContracts.RequestPermission()) { result ->
            textView.text = "Result is $result"
        }

        /*
        *   Expected: message "Result is {true/false}"
        *   Actual: message "Wait for result.." 
        */
        launcher.launch(android.Manifest.permission.CAMERA)
    }
}
