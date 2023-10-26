package crls.utility.adder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val buttonAdd = Button(this)
        buttonAdd.text = "Soma"

        val textViewDisplay = TextView(this)
        textViewDisplay.text = "Carlos Dias"
        textViewDisplay.textSize = 32.0f

        setContentView()
    }
}