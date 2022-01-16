package den.project.getlocation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import den.project.getlocation.R

class ShareActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shsre)
        handleIntent()
    }

    private fun handleIntent() {
        if (intent?.type == "text/plain") {
            findViewById<TextView>(R.id.textView).text = intent.extras?.getString(Intent.EXTRA_TEXT)
        }
    }
}