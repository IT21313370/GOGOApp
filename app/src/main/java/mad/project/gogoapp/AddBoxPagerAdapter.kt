package mad.project.gogoapp

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AddBoxPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    @NonNull
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            AddJob()
        } else {
            AddVacancy()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}