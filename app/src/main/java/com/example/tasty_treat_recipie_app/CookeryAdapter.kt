package com.example.tasty_treat_recipie_app


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CookeryAdapter(private var cookery: List<Cookery>, context: Context) : RecyclerView.Adapter<CookeryAdapter.CookeryViewHolder>() {

    private val db:CookeryDatabaseHelper= CookeryDatabaseHelper(context)

    class CookeryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleTextView:TextView=itemView.findViewById(R.id.titleTextView)
        val contentTextView:TextView=itemView.findViewById(R.id.contentTextView)
        val updateButton:ImageView=itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView=itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookeryViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.cookery_item,parent,false)
        return CookeryViewHolder(view)
    }

    override fun getItemCount(): Int =cookery.size

    override fun onBindViewHolder(holder: CookeryViewHolder, position: Int) {
        val cookery=cookery[position]
        holder.titleTextView.text=cookery.title
        holder.contentTextView.text=cookery.content

        holder.updateButton.setOnClickListener {
            val intent=Intent(holder.itemView.context,UpdateCookeryActivity::class.java).apply {
                putExtra("cookery_id",cookery.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteCookery(cookery.id)
            refreshdata(db.getAllCookery())
            Toast.makeText(holder.itemView.context,"Cookery Deleted",Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshdata(newCookery: List<Cookery>){
        cookery=newCookery
        notifyDataSetChanged()
    }
}