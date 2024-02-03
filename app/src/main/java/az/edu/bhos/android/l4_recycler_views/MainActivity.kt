package az.edu.bhos.android.l4_recycler_views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val people = arrayListOf<String>()

        for (i in 0..100) {
            people.add("People $i")
        }

        val adapter = ArrayAdapter(this, R.layout.list_item_view_simple, people)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Selected ${people[position]}", Toast.LENGTH_SHORT).show()
        }
    }
}