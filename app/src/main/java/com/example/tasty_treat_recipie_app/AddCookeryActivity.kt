package com.example.tasty_treat_recipie_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasty_treat_recipie_app.databinding.ActivityAddCookeryBinding

class AddCookeryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddCookeryBinding
    private lateinit var db:CookeryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddCookeryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= CookeryDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title=binding.titleEditText.text.toString()
            val content=binding.contentEditText.text.toString()
            val cookery=Cookery(0,title,content)
            db.insertCookery(cookery)
            finish()
            Toast.makeText(this,"recipie saved", Toast.LENGTH_SHORT).show()
        }
    }
}