package com.mrengineer13.grouper.models

import android.support.v7.widget.RecyclerView
import android.transition.Visibility
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.google.firebase.auth.FirebaseAuth
import com.mrengineer13.grouper.R
import com.mrengineer13.grouper.utils.DateUtils


/**
 * Created by Jon on 10/28/17.
 */
class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageField: SimpleDraweeView = itemView.findViewById(R.id.message_user_image)
    private val nameField: TextView = itemView.findViewById(R.id.message_user)
    private val textField: TextView = itemView.findViewById(R.id.message_text)
    private val timeField: TextView = itemView.findViewById(R.id.message_time)
    private val container: LinearLayout = itemView.findViewById(R.id.message_container)
    private var selfId: String = ""

    fun bind(chat: ChatMessage) {
        selfId = FirebaseAuth.getInstance().currentUser!!.uid
        setText(chat.messageText)
        setTime(chat.messageTime)
        setName(chat.sentBy!!.displayName)
        setImage(chat.sentBy)
        checkMessageSender(selfId, chat)
    }

    private fun checkMessageSender(userId: String, chat: ChatMessage) {
        if (userId == chat.sentBy!!.userId) {
            container.gravity = Gravity.LEFT
            imageField.visibility = View.VISIBLE
        } else {
            container.gravity = Gravity.RIGHT
            imageField.visibility = View.GONE
        }
    }

    private fun setImage(currentUser: User?) {
        if (currentUser != null) {
            imageField.setImageURI(currentUser.photoUrl)
        } else {
            imageField.setBackgroundResource(R.drawable.ic_person_outline_black_24dp)
        }
    }

    private fun setName(name: String) {
        nameField.text = name
    }

    private fun setText(text: String) {
        textField.text = text
    }

    private fun setTime(time: Long) {
        timeField.text = DateUtils.formatMessageTime(time)
    }
}