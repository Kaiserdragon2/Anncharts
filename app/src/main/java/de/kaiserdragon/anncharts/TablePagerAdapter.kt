package de.kaiserdragon.anncharts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TablePagerAdapter(fragmentActivity: FragmentActivity, private val tableUrls: List<String>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return tableUrls.size
    }

    override fun createFragment(position: Int): Fragment {
        return TableFragment.newInstance(tableUrls[position])
    }
}
