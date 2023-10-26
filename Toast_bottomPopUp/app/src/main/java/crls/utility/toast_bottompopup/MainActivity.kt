package crls.utility.toast_bottompopup

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import crls.utility.toast_bottompopup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val nome = binding.editTextTextNome.text.toString().trim()
            val pronome = binding.editTextTextPronome.text.toString().trim()

            if (nome.isEmpty() || pronome.isEmpty()){
                binding.textViewWelcome.text = "Nome não inserido"
                Toast.makeText(applicationContext, "Nome não inserido.", Toast.LENGTH_SHORT).show()
            } else {
                binding.textViewWelcome.text = "Olá $nome $pronome"
                Toast.makeText(applicationContext, "Olá $nome $pronome!", Toast.LENGTH_LONG).show()
            }
        }
    }
}