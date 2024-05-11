package com.example.tasty_treat_recipie_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasty_treat_recipie_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var db:CookeryDatabaseHelper
    private lateinit var cookeryAdapter: CookeryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= CookeryDatabaseHelper(this)
        cookeryAdapter= CookeryAdapter(db.getAllCookery(),this)

        binding.notesRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.notesRecyclerView.adapter=cookeryAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this,AddCookeryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cookeryAdapter.refreshdata(db.getAllCookery())
    }
}