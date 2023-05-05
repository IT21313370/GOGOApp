package mad.project.gogoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button

class StartingActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)


        handler = Handler()

        handler.postDelayed({
            val intent = Intent(this,LoginActivityService::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}