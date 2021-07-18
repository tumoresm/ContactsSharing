package com.example.contactssharing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    val arraylist = ArrayList<ContactModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.READ_CONTACTS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf(Manifest.permission.READ_CONTACTS), 100
            )
        } else {
            contactList
        }
    }

    private val contactList: Unit
        private get() {
            val uri = ContactsContract.Contacts.CONTENT_URI
            val sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "ASC"
            val cursor = contentResolver.query(uri, null, null, null, sort)
            if (cursor!!.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                        )
                    )
                    val uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    val selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?"
                    val phoneCursor =
                        contentResolver.query(uriPhone, null, selection, arrayOf(id), null)
                    if (phoneCursor!!.moveToNext()) {
                        val number =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val model = ContactModel()
                        model.name = name
                        model.number = number
                        arraylist.add(model)
                        phoneCursor.close()
                    }
                }
                cursor.close()
            }
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            var adapter = MainAdapter(this, arraylist)
            recyclerView!!.adapter = adapter
        }

    fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.size > 0 && (grantResults[0]
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            contactList
        } else {
            Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            checkPermission()
        }
    }
}