package utn.cr.jonsbarb
import Adapter.adapter
import Entities.Barber
import Model.BarberModel
import Util.util
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class barberCustomList : AppCompatActivity() {
    lateinit var model: BarberModel
    lateinit var listCustom: List<Barber>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_barber_custom_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        model= BarberModel(this)
        val lstCustomList = findViewById<ListView>(R.id.BarbersLista)
        listCustom = model.getBarbers().filterIsInstance<Barber>()


        val adapter = adapter(this, R.layout.item_list, listCustom)
        lstCustomList.adapter= adapter

        lstCustomList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val containerNum = listCustom[position].id
                util.openActivity(
                    this,
                    insertBarb::class.java,
                    EXTRA_MESSAGE_BARB_NAME,
                    containerNum
                )
            }
    }
}