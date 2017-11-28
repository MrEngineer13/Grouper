package com.mrengineer13.grouper.models

import com.google.firebase.auth.FirebaseUser

/**
 * Created by Jon on 10/27/17.
 */

class User (){
    var userId: String = ""
    var displayName: String = ""
    var photoUrl: String = ""

    constructor(user: FirebaseUser) : this(){
        this.userId = user.uid
        this.displayName = user.displayName!!
        this.photoUrl = user.photoUrl.toString()
    }
}

