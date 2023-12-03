package de.kaiserdragon.anncharts
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class Main : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        FetchAnimeData().execute()
    }

    private inner class FetchAnimeData : AsyncTask<Void, Void, List<TableItem>>() {

        override fun doInBackground(vararg params: Void?): List<TableItem> {
            val data = mutableListOf<TableItem>()

            try {
                val url = "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=best_bayesian&n=500"
                val doc: Document = Jsoup.connect(url).get()

                val table: Elements = doc.select("table.encyc-ratings tbody tr:has(td)")

                for (row in table) {
                    val columns = row.select("td")
                    if (columns.size == 4) {
                        val num = columns[0].text()
                        val titleElement = columns[1].select("a").first()
                        var title = titleElement?.text() ?: ""
                        val link = titleElement?.absUrl("href") ?: ""
                        val rating = columns[2].text()
                        val votes = columns[3].text()
                        if (title == ""){
                            title = columns[1].text()
                        }
                        //if(num == "#"){
                        //Log.d("AnnMain", "Link:$title")
                        //}else
                        data.add(TableItem(num, title, link, rating, votes))
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return data
        }

        override fun onPostExecute(result: List<TableItem>?) {
            super.onPostExecute(result)
            if (result != null) {
                adapter = TableAdapter(result)
                recyclerView.adapter = adapter
            }
        }
    }
}
