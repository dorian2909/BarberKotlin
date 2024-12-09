package Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import Entities.Barber
import utn.cr.jonsbarb.R

class adapter(
    private val context: Context,
    private var resource: Int,
    private var datasource: List<Barber>
) : ArrayAdapter<Barber>(context, resource, datasource) {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datasource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflar la vista
        val rowView = inflater.inflate(R.layout.item_list, parent, false)

        // Referencias a los elementos del layout
        val name = rowView.findViewById<TextView>(R.id.item_name)
        val id = rowView.findViewById<TextView>(R.id.item_ced)
        val phone = rowView.findViewById<TextView>(R.id.item_tel)
        val email = rowView.findViewById<TextView>(R.id.item_email)
        val img = rowView.findViewById<ImageView>(R.id.item_img)

        // Obtener el objeto Barber en la posición actual
        val barb = datasource[position]

        // Asignar datos a las vistas
        name.text = barb.name
        id.text = barb.id
        phone.text = barb.phone.toString()
        email.text = barb.email

        // Manejar la imagen (ByteArray?)
        val imgByteArray = barb.image
        if (imgByteArray != null) {
            // Convertir ByteArray en Bitmap
            val imgBitmap: Bitmap = BitmapFactory.decodeByteArray(imgByteArray, 0, imgByteArray.size)
            img.setImageBitmap(imgBitmap) // Mostrar la imagen en el ImageView
        }

        return rowView
    }
}
