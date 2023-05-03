package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import mad.project.gogoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Community())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.community -> replaceFragment(Community())
                R.id.notification -> replaceFragment(Notification())
                R.id.favourites -> replaceFragment(Favourites())
                R.id.todo -> replaceFragment(Todo())
                R.id.addbox -> replaceFragment(AddBox())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}