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
     * Used: androidx.activity:activity:1.2.0-alpha02
     *
     * Issue:
     * 1) Cannot get result in callback, because [FragmentActivity.onRequestPermissionsResult] don't call super method ([ComponentActivity.onRequestPermissionsResult]).
     * So [ComponentActivity.mActivityResultRegistry] field cannot get result.
     *
     * 2) If VERSION.SDK_INT < 23, then [ActivityResultCallback] callback not called (see [ActivityResultRegistry.invoke] implementation in [ComponentActivity]).
     *
     *
     * NOTE Issue 1 fix: use FragmentActivity from androidx.fragment:fragment:1.3.0-alpha02
     * NOTE Issue 2 possible fix in next alpha release: ["short-circuit" behavior for requesting permissions](https://issuetracker.google.com/issues/151110799)
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
