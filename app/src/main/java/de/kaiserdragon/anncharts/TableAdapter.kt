package de.kaiserdragon.anncharts

import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView

class TableAdapter(private val animeList: List<TableItem>) :
    RecyclerView.Adapter<TableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_item_layout, parent, false)
        return TableViewHolder(view)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val item = animeList[position]

        // Bind data to other views
        holder.rankTextView.text = item.rank
        holder.ratingTextView.text = item.rating
        holder.votesTextView.text = item.votes

        // Create a SpannableString to handle clickable links
        if(item.link != "") {
            val spannable = SpannableString(item.title)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    // Check if the link is not empty or null
                    if (item.link.isNotBlank()) {
                        // Handle the click event, open the link in a web browser
                        Log.d("TableAdapter",item.link)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                        // Check if there's an activity that can handle the intent
                        //if (intent.resolveActivity(view.context.packageManager) != null) {
                            view.context.startActivity(intent)
                        //} else {
                            // Log a message or handle the case where no activity can handle the intent
                            Log.e("TableAdapter", "No activity found to handle the intent.")
                        //}
                    } else {
                        // Log a message or handle the case where the link is empty
                        Log.e("TableAdapter", "Empty or invalid link.")
                    }
                }
            }
            spannable.setSpan(
                clickableSpan,
                0,
                item.title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.titleTextView.text = spannable
            holder.titleTextView.movementMethod = LinkMovementMethod.getInstance()
        }else holder.titleTextView.text =item.title
    }



    override fun getItemCount(): Int {
        return animeList.size
    }
}
