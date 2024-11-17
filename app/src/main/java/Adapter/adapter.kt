package Adapter
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import Entities.Barber

import utn.cr.jonsbarb.R

class adapter(private val context: Context, private var resource: Int, private var datasource: List<Barber>):
    ArrayAdapter<Barber>(context, resource, datasource){

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return datasource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflar
        val rowView = inflater.inflate(R.layout.item_list, parent, false)


        val name = rowView.findViewById<TextView>(R.id.item_name)
        val id = rowView.findViewById<TextView>(R.id.item_ced)
        val phone = rowView.findViewById<TextView>(R.id.item_tel)
        val email = rowView.findViewById<TextView>(R.id.item_email)
        val img = rowView.findViewById<ImageView>(R.id.item_img)


        val barb = datasource[position] as Barber


        name.text = barb.name
        id.text = barb.id.toString()
        phone.text = barb.phone.toString()
        email.text = barb.email.toString()



        val imgUri = Uri.parse(barb.image)
        img.setImageURI(imgUri)
        return rowView
    }

}