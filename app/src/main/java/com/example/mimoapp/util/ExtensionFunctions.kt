package com.example.mimoapp.util

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.showMessageToUser(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

//prevents the button from being clicked multiple times before the required action is triggered
//mainly used to prevent an error caused by the navigation component
fun View.blockingClickListener(debounceTime: Long = 1200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            val timeNow = SystemClock.elapsedRealtime()
            val elapsedTimeSinceLastClick = timeNow - lastClickTime

            if (elapsedTimeSinceLastClick < debounceTime) {

                return
            }
            else {
                action()
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}