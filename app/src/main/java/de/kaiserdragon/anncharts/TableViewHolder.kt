package de.kaiserdragon.anncharts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val rankTextView: TextView = itemView.findViewById(R.id.column0TextView)
    val titleTextView: TextView = itemView.findViewById(R.id.column1TextView)
    val ratingTextView: TextView = itemView.findViewById(R.id.column2TextView)
    val votesTextView: TextView = itemView.findViewById(R.id.column3TextView)
}
