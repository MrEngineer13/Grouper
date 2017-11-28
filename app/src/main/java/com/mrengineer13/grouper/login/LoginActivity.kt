package com.mrengineer13.grouper.login

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.mrengineer13.grouper.R

import java.util.*
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import android.content.Intent
import android.util.Log
import butterknife.ButterKnife
import butterknife.OnClick
import com.mrengineer13.grouper.messages.MessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.CallbackManager
import com.mrengineer13.grouper.base.BaseActivity


class LoginActivity : BaseActivity() {

    private val RC_SIGN_IN = 537

    override fun onViewInjected(savedInstanceState: Bundle?) {
        checkSignedInStatus()
    }

    private fun checkSignedInStatus(){
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            goToMessages()
        }
    }

    private fun goToMessages() {
        startActivity(Intent(this, MessageActivity::class.java))
        finish()
    }

    @OnClick(R.id.facebook_button)
    fun onFBSignInClicked(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                goToMessages()
                return
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled)
                    return
                }

                if (response.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.error_no_internet)
                    return
                }

                if (response.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(R.string.error_unknown)
                    return
                }
            }

            showSnackbar(R.string.unknown_response)
        }
    }

    override fun userSignedOut() {}

    override fun layoutId(): Int = R.layout.activity_sign_in

}
