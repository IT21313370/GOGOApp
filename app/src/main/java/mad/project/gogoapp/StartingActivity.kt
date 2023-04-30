package mad.project.gogoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)


        val serviceProviderBtn = findViewById<Button>(R.id.service_provider)

        serviceProviderBtn.setOnClickListener{
            val Intent = Intent(this, SignupActivityService::class.java)
            startActivity(Intent)
        }
    }
}