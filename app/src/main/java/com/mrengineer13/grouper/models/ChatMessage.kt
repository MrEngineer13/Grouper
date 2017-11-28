package com.mrengineer13.grouper.models

import com.firebase.ui.auth.AuthUI
import java.util.*


/**
 * Created by Jon on 10/28/17.
 */
class ChatMessage (){

    var messageText: String = ""
    var messageTime: Long = 0
    var sentBy: User? = null

    constructor(messageText: String,  sentBy: User): this() {
        this.messageText = messageText
        this.messageTime = Date().time
        this.sentBy = sentBy
    }
}
