package utn.cr.jonsbarb

import Entities.Barber
import Model.BarberModel
import Util.util
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import java.io.File

class insertBarb : AppCompatActivity() {
    private lateinit var nameB: EditText
    private lateinit var cedB: EditText
    private lateinit var emailB: TextView
    private lateinit var telB: EditText

    private lateinit var modelB: BarberModel
    private val IMAGE_REQUEST_CODE = 1001

    private lateinit var imageB: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert_barb)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        modelB = BarberModel(this)
        imageB = findViewById(R.id.imageView)
        nameB = findViewById((R.id.txtNombre))
        cedB = findViewById((R.id.txtced))
        emailB = findViewById((R.id.txtEmail))
        telB = findViewById((R.id.txtPhone))


        val btncamera: Button = findViewById<Button>(R.id.btn_image)
        btncamera.setOnClickListener(View.OnClickListener { view ->
            showImageSourceDialog()
        })
        // aqui recupero id
        val barberId = intent.getStringExtra(EXTRA_MESSAGE_BARB_NAME)

        // los mando a la funcion load
        if (barberId != null) {
            loadBarberData(barberId)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mnu_crud, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_insert -> {
                saveContainer()
                return true
            }

            R.id.btn_delete -> {
              //  util.openActivity(this, deleteBa::class.java)

                return true
            }

            R.id.btn_update -> {
               // util.openActivity(this, updateBarb::class.java)
                return true
            }

            R.id.btn_list -> {
                util.openActivity(this, barberCustomList::class.java)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    // Abre la galería
    private fun openGall() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Extraer el bitmap desde el intent
            val imgBitmap = data?.extras?.get("data") as? Bitmap
            if (imgBitmap != null) {
                imageB.setImageBitmap(imgBitmap) // Asignar el bitmap al ImageView
                Toast.makeText(this, "Se obtuvo la imagen", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No se pudo obtener la imagen", Toast.LENGTH_LONG).show()
            }
        }
    }




    // Abre la cámara
    private fun openCam() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 1)
    }


    // Método insert
    private fun saveContainer() {
        try {
            // Guardar imagen en almacenamiento interno y obtener la URI
            val imgUri = imageB.drawable?.let { drawable ->
                val bitmap = (drawable as BitmapDrawable).bitmap
                saveImageToInternalStorage(bitmap)
            }

            // Crear el objeto Barber
            val obBarb = Barber(
                id = cedB.text.toString(),
                name = nameB.text.toString(),
                phone = telB.text.toString().toIntOrNull() ?: -1,
                email = emailB.text.toString(),
                img = imgUri.toString()
            )

            if (modelB.isDuplicate(obBarb)) {
                Toast.makeText(this, R.string.msgIsduplicate, Toast.LENGTH_LONG).show()
            } else {
                modelB.addBarber(obBarb)
                Toast.makeText(this, R.string.Succes, Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
    private fun loadBarberData(BarberId: String) {
        try {

            val barbero = modelB.getBarber(BarberId) as? Barber

            //
            if (barbero != null) {
                nameB.setText(barbero.name)
                cedB.setText(barbero.id)
                telB.setText(barbero.phone.toString())

                emailB.setText(barbero.email)

            } else {
                Toast.makeText(this, "Barbero no encontrado", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar los datos: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // guardar imagen en almacenamiento
    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val file = File(filesDir, "container_${System.currentTimeMillis()}.jpg")
        file.outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return Uri.fromFile(file)
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Tomar Foto", "Seleccionar de la Galería")
        AlertDialog.Builder(this)
            .setTitle("Seleccionar Imagen")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openCam() // Abre la cámara
                    1 -> openGall() // Abre la galería
                }
            }
            .show()
    }
}