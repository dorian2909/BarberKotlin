package utn.cr.jonsbarb

import Model.BarberModel
import Util.util
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
const val EXTRA_MESSAGE_BARB_NAME = "utn.cr.jonsbarb.id"

class barberlist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_barberlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val model = BarberModel(this)
        val lstBarb = findViewById<ListView>(R.id.listBarbers)

        val containerList = model.getBarbers()


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, containerList.map { it.name })

        lstBarb.adapter = adapter

        lstBarb.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                val itemValue = containerList[position]
                util.openActivity(
                    this,
                    insertBarb::class.java,
                    EXTRA_MESSAGE_BARB_NAME,
                    itemValue.id
                )
            }
    }

}