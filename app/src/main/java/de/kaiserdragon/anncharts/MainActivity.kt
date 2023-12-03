package de.kaiserdragon.anncharts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: TablePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_main)

        // Provide a list of URLs for each table
        val tableUrls = listOf(
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=best_bayesian&n=500",
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=worst_bayesian&n=500",
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=best_wavg&n=500",
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=popular&n=500",
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=most_viewed&n=500",
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=most_underrated&n=500",
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=most_overrated&n=500",
            "https://www.animenewsnetwork.com/encyclopedia/ratings-anime.php?top50=std_dev&n=500"
            // Add more URLs as needed
        )

        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = TablePagerAdapter(this, tableUrls)
        viewPager.adapter = pagerAdapter
    }
    fun setTitle(title: String) {
        supportActionBar?.title = title
    }
}
