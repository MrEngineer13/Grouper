package com.mrengineer13.grouper.messages

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mrengineer13.grouper.R
import com.mrengineer13.grouper.base.BaseActivity
import com.mrengineer13.grouper.models.ChatHolder
import com.mrengineer13.grouper.models.ChatMessage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.content.Intent
import butterknife.OnClick
import com.firebase.ui.auth.AuthUI
import com.mrengineer13.grouper.login.LoginActivity
import com.mrengineer13.grouper.models.User


class MessageActivity : BaseActivity() {

    private val sChatCollection = FirebaseFirestore.getInstance().collection("chats")
    private val sChatQuery = sChatCollection.orderBy("messageTime").limit(50)

    override fun onViewInjected(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        messageList.layoutManager = linearLayoutManager
        userSignedIn()
    }

    override fun userSignedIn() {
        val adapter = newAdapter()

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                messageList.smoothScrollToPosition(adapter.itemCount)
            }
        })

        messageList.adapter = adapter
    }

    @OnClick(R.id.fab)
    fun onSendClicked() {
        val input = findViewById<View>(R.id.input) as EditText

        // Read the input field and push a new instance
        // of ChatMessage to the Firebase database
        sChatCollection
                .add(ChatMessage(input.text.toString(),
                        User(FirebaseAuth.getInstance()!!
                                .currentUser!!)))

        // Clear the input
        input.setText("")
    }

    private fun newAdapter() : RecyclerView.Adapter<ChatHolder>{
        val options = FirestoreRecyclerOptions.Builder<ChatMessage>()
                .setQuery(sChatQuery, ChatMessage::class.java)
                .setLifecycleOwner(this)
                .build()


        return object : FirestoreRecyclerAdapter<ChatMessage, ChatHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
                return ChatHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_message_row, parent, false))
            }

            override fun onBindViewHolder(holder: ChatHolder, position: Int, model: ChatMessage) {
                holder.bind(model)
            }

            override fun onDataChanged() {
                // If there are no chat messages, show a view that invites the user to add a message.
//                mEmptyListMessage.setVisibility(if (itemCount == 0) View.VISIBLE else View.GONE)
                // TODO add show no messages view
            }
        }
    }

    private fun toggleViews(){
        fab.isEnabled = isSignedIn()
        input.isEnabled = isSignedIn()
    }

    override fun onAuthStateChanged(auth: FirebaseAuth) {
        super.onAuthStateChanged(auth)
        toggleViews()
    }

    override fun userSignedOut() {
        showSnackbar(R.string.signed_out)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_sign_out -> signOut()
        }
        return true
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun menuId(): Int = R.menu.menu_main

}


