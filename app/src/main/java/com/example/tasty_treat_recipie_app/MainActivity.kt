package com.example.tasty_treat_recipie_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasty_treat_recipie_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: CookeryDatabaseHelper
    private lateinit var cookeryAdapter: CookeryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = CookeryDatabaseHelper(this)
        cookeryAdapter = CookeryAdapter(db.getAllCookery(), this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = cookeryAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddCookeryActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        val filteredList = db.getAllCookery().filter { cookery ->
                            cookery.title.contains(it, ignoreCase = true)
                        }
                        cookeryAdapter.refreshdata(filteredList)
                    } else {
                        cookeryAdapter.refreshdata(db.getAllCookery())
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = db.getAllCookery().filter { cookery ->
                        cookery.title.contains(it, ignoreCase = true)
                    }
                    cookeryAdapter.refreshdata(filteredList)
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        cookeryAdapter.refreshdata(db.getAllCookery())
    }
}
