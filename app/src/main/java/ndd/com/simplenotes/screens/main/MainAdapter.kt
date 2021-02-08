package ndd.com.simplenotes.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ndd.com.simplenotes.R
import ndd.com.simplenotes.models.AppNote

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainHolder>() {
    private var listNotes = emptyList<AppNote>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bodyNone.text = listNotes[position].text
        holder.nameNone.text = listNotes[position].name
    }

    override fun getItemCount(): Int = listNotes.size
    override fun onViewAttachedToWindow(holder: MainHolder) {
        holder.itemView.setOnClickListener {

            MainFragment.click(listNotes[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: MainHolder) {
        holder.itemView.setOnClickListener { null }
    }

    fun setList(list: List<AppNote>) {
        listNotes = list
        notifyDataSetChanged()
    }

    class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameNone: TextView = view.findViewById(R.id.tv_item_note_name)
        val bodyNone: TextView = view.findViewById(R.id.tv_item_note_body)
    }
}