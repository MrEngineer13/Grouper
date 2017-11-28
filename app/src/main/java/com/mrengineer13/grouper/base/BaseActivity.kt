package com.mrengineer13.grouper.base

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import butterknife.ButterKnife
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mrengineer13.grouper.login.LoginActivity


abstract class BaseActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {

    var auth: FirebaseAuth? = null
    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setContentView(layoutId())
        ButterKnife.bind(this)
        onViewInjected(savedInstanceState)
    }

    public override fun onStart() {
        super.onStart()
        auth!!.addAuthStateListener(this)
    }

    override fun onStop() {
        super.onStop()
        auth!!.removeAuthStateListener(this)
    }

    override fun onAuthStateChanged(auth: FirebaseAuth){
        if (isSignedIn()){
            userSignedIn()
        } else {
            userSignedOut()
        }
    }



    fun isSignedIn(): Boolean {
        return auth!!.currentUser != null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menuId() != Int.MAX_VALUE) {
            menuInflater.inflate(menuId(), menu)
        }
        return true
    }

    open fun menuId() : Int {
        return Int.MAX_VALUE
    }

    open fun signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this@BaseActivity, LoginActivity::class.java))
                    finish()
                }
    }

    protected fun showSnackbar(message: Int) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    }

    open fun userSignedIn() {}

    abstract fun userSignedOut()

    abstract fun onViewInjected(savedInstanceState: Bundle?)

    abstract fun layoutId(): Int
}