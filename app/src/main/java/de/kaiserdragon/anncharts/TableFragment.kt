@file:OptIn(DelicateCoroutinesApi::class)

package de.kaiserdragon.anncharts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class TableFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TableAdapter
    private var tableUrl: String = ""
    private var tableName = "Default Table Name"
    private var infoText = "Default Info Text"

    companion object {
        private const val KEY_TABLE_NAME = "table_name"
        private const val ARG_TABLE_URL = "table_url"

        fun newInstance(tableUrl: String): TableFragment {
            val fragment = TableFragment()
            val args = Bundle()
            args.putString(ARG_TABLE_URL, tableUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_table, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve the URL from arguments
        tableUrl = arguments?.getString(ARG_TABLE_URL, "") ?: ""

        // Use Kotlin coroutine for background task
        GlobalScope.launch(Dispatchers.Main) {
            val result = fetchData()
            adapter = TableAdapter(result)
            recyclerView.adapter = adapter
            // Set the title in the activity
            if (activity is MainActivity) {
                (activity as MainActivity).setTitle(tableName)
            }
        }
        setHasOptionsMenu(true) // Indicates that the fragment has menu items

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_table_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info -> {
                // Handle info button click
                showInfoDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showInfoDialog() {
        val dialogFragment = InfoDialogFragment.newInstance(infoText)
        dialogFragment.show(requireFragmentManager(), InfoDialogFragment.TAG)
    }


    override fun onResume() {
        super.onResume()

        // Retrieve the URL from arguments
        //tableUrl = arguments?.getString(ARG_TABLE_URL, "") ?: ""

        // Use Kotlin coroutine for background task
        GlobalScope.launch(Dispatchers.Main) {
           // val result = fetchData()
            //adapter = TableAdapter(result)
            //recyclerView.adapter = adapter

            // Use 'title' for setting the app name or other purposes
            if (activity is MainActivity) {
                (activity as MainActivity).setTitle(tableName)
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the title when the fragment is being destroyed
        outState.putString(KEY_TABLE_NAME, tableName)
    }

    private fun String.excludeString(original: String): String {
        return original.replace(this, "").trim()
    }


    private suspend fun fetchData(): List<TableItem> = withContext(Dispatchers.IO) {
        val data = mutableListOf<TableItem>()


        try {
            val doc: Document = Jsoup.connect(tableUrl).get()

            // Parse the title of the table
            val titleElement = doc.select("div#page-title h1#page_header").first()
            tableName = titleElement?.text() ?: "Default Table Name"
            tableName = "Anime Top 500".excludeString(tableName)

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
                    if (title == "") {
                        title = columns[1].text()
                    }
                    data.add(TableItem(num, title, link, rating, votes))
                }else {
                    infoText = columns[0].wholeText().replace("(formula)","\n Formula:")
                    infoText = infoText.replace("where:","\n Where:")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return@withContext data
    }
}
