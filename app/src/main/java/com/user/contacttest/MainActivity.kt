package com.user.contacttest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import java.io.*

data class Contact(var id:String,
                   var firstName:String,
                   var lastName:String,
                   var email:String,
                   var phone:String): Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var listContact:RecyclerView
    private lateinit var swipeToRefresh:SwipeRefreshLayout
    private lateinit var adapter: ListContactAdapter
    private lateinit var contactArray:MutableList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listContact = findViewById(R.id.listContact)
        listContact.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listContact.setHasFixedSize(true)

        getListDataFromJson()

        swipeToRefresh = findViewById(R.id.refresh)
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun getListDataFromJson() {
        var input: InputStream = resources.openRawResource(R.raw.data)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(input, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } finally {
            input.close()
        }

        val jsonString: String = writer.toString()

        val gson = Gson()

        val userArray: Array<Contact> = gson.fromJson(jsonString, Array<Contact>::class.java)

        contactArray = arrayListOf()

        userArray.forEach {
          contactArray.add(it)
        }
        adapter = ListContactAdapter(contactArray, this)
        listContact.adapter = adapter
    }
}
class ListContactAdapter(var listContact:MutableList<Contact>, var context:Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ArticleViewHolder(view)
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: AppCompatTextView = itemView.findViewById(R.id.fName)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewArticle = holder as ArticleViewHolder


        viewArticle.name.text = listContact[position].firstName + listContact[position].lastName

        viewArticle.itemView.setOnClickListener(View.OnClickListener {
            var a = Intent(context, ViewContact::class.java)
            var objContact:Contact =  listContact[position]
            a.putExtra("contact", objContact as Serializable)
            context.startActivity(a)
        })
    }

    override fun getItemCount(): Int {
        return listContact.size
    }
}