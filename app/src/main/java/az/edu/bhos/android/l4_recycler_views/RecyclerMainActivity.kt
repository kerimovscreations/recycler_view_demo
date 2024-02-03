package az.edu.bhos.android.l4_recycler_views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_main)

        val contacts = arrayListOf<Contact>()

        for (i in 1..100) {
            contacts.add(Contact(
                firstName = "First N$i",
                lastName = "Last name",
                email = "person$i@example.com"
            ))
        }

        val adapter = ContactAdapter(contacts) { contact ->
            Toast.makeText(this, "Selected ${contact.firstName}", Toast.LENGTH_SHORT).show()
        }

        val rv = findViewById<RecyclerView>(R.id.rcView)
        rv.adapter = adapter
//        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//        rv.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
//        rv.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        rv.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        (rv.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) {
                    2
                } else {
                    1
                }
            }

        }
    }
}

data class Contact(val firstName: String, val lastName: String, val email: String)

class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val fullName: TextView
    val email: TextView

    init {
        fullName = view.findViewById(R.id.name)
        email = view.findViewById(R.id.email)
    }
}

class CompanyViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val name: TextView
    val address: TextView

    init {
        name = view.findViewById(R.id.name)
        address = view.findViewById(R.id.address)
    }
}

class ContactAdapter(val dataSource: List<Contact>,
    val onClick: (Contact) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_list_item, parent, false)

            return ContactViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.company_list_item, parent, false)

            return CompanyViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ContactViewHolder)?.let {
            val contact = dataSource[position]
            holder.fullName.text = "${contact.firstName} ${contact.lastName}"

            holder.email.text = contact.email

            holder.itemView.setOnClickListener {
                onClick(dataSource[position])
            }

            return
        }

        (holder as? CompanyViewHolder)?.let {
            val contact = dataSource[position]

            holder.name.text = "Company - ${contact.firstName}"

            holder.address.text = contact.email

            holder.itemView.setOnClickListener {
                onClick(dataSource[position])
            }
        }
    }

    // 0 - contact, 1 - company
    override fun getItemViewType(position: Int): Int {
        return if (position % 3 == 2) {
            1
        }else {
            0
        }
    }

}