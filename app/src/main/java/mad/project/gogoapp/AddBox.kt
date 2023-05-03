package mad.project.gogoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AddBox : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_box, container, false)

        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager01)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout01)

        viewPager.adapter = AddBoxPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Add Job"
                1 -> "Add Vacancy"
                else -> null
            }
        }.attach()

        return view
    }

}