package com.mrengineer13.grouper.base

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.mrengineer13.grouper.R


/**
 * Created by Jon on 10/30/17.
 */
open class SignInResultNotifier(context: Context) : OnCompleteListener<AuthResult> {
    private val mContext: Context = context.applicationContext

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Toast.makeText(mContext, R.string.signed_in, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, R.string.anonymous_auth_failed_msg, Toast.LENGTH_LONG).show()
        }
    }
}