package com.user.contacttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText

class ViewContact : AppCompatActivity() {
    private lateinit var contact: Contact
    private lateinit var fName:AppCompatEditText
    private lateinit var lName:AppCompatEditText
    private lateinit var email:AppCompatEditText
    private lateinit var phone:AppCompatEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contact)

        contact = intent.extras!!.get("contact") as Contact

        fName = findViewById(R.id.editFname)
        fName.setText(contact.firstName)

        lName = findViewById(R.id.editLname)
        lName.setText(contact.lastName)

        email = findViewById(R.id.editEmail)
        email.setText(contact.email)

        phone = findViewById(R.id.editPhone)
        phone.setText(contact.phone)
    }
}