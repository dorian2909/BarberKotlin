package utn.cr.jonsbarb

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import Util.util

import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val btnAddCont: Button = findViewById<Button>(R.id.btn_control)
        btnAddCont.setOnClickListener(View.OnClickListener { view ->
            util.openActivity(this, insertBarb::class.java)


            Toast.makeText(this, getString(R.string.Welc).toString(), Toast.LENGTH_LONG).show()
            //Aqui mandamos un mensaje
        })

    }




}