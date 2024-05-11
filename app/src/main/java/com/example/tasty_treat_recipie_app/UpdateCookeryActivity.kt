package com.example.tasty_treat_recipie_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasty_treat_recipie_app.databinding.ActivityUpdateCookeryBinding

class UpdateCookeryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateCookeryBinding
    private lateinit var db:CookeryDatabaseHelper
    private var cookeryId:Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateCookeryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= CookeryDatabaseHelper(this)

        cookeryId=intent.getIntExtra("cookery_id",-1)
        if (cookeryId==-1){
            finish()
            return
        }

        val cookery=db.getCookeryByID(cookeryId)
        binding.updateTitleEditText.setText(cookery.title)
        binding.updateContentEditText.setText(cookery.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle=binding.updateTitleEditText.text.toString()
            val newContent=binding.updateContentEditText.text.toString()
            val updatedCookery=Cookery(cookeryId,newTitle,newContent)
            db.updateCookery(updatedCookery)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()
        }

    }
}