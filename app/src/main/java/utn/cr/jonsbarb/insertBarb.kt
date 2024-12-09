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
import java.io.ByteArrayOutputStream

import java.io.File

class insertBarb : AppCompatActivity() {
    private lateinit var nameB: EditText
    private lateinit var cedB: EditText
    private lateinit var emailB: TextView
    private lateinit var telB: EditText

    private lateinit var modelB: BarberModel
    private val IMAGE_REQUEST_CODE = 1001
    private val CAMERA_REQUEST_CODE = 1002 // Código para capturar fotos


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
                saveBarber()
                return true
            }

            R.id.btn_delete -> {
              //  showConfirmationDialog("delete")

                return true
            }

            R.id.btn_update -> {
               // showConfirmationDialog("update")
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
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Obtener el Bitmap desde el Intent
            val imgBitmap = data?.extras?.get("data") as? Bitmap

            if (imgBitmap != null) {
                // Establecer el Bitmap en el ImageView
                imageB.setImageBitmap(imgBitmap)

                // Convertir la imagen a ByteArray
                val stream = ByteArrayOutputStream()
                imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val imgByteArray = stream.toByteArray()

                // Ahora puedes guardar el imgByteArray en la base de datos si es necesario
            } else {
                Toast.makeText(this, "No se pudo obtener la imagen", Toast.LENGTH_LONG).show()
            }
        }
    }







    // Abre la cámara
    private fun openCam() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    // Método insert
    private fun saveBarber() {
        val db = DBBarbers(this)
        val resultId = db.insertarBarber("1", "Dado", 123, "dado@gmail.com", ByteArray(0)) // Usando una imagen vacía

        try {

            // Validar campos requeridos
            val name = nameB.text.toString()
            val phone = telB.text.toString().toIntOrNull() ?: -1
            val email = emailB.text.toString()
            val id = cedB.text.toString()

            // Obtener la imagen desde el ImageView
            val drawable = imageB.drawable as? BitmapDrawable
            val imgBitmap = drawable?.bitmap

            if (imgBitmap == null) {
                Toast.makeText(this, "Por favor capture una imagen", Toast.LENGTH_SHORT).show()
                return
            }

            // Convertir la imagen a ByteArray
            val stream = ByteArrayOutputStream()
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imgByteArray = stream.toByteArray()

            // Crear una instancia de DBBarbers y guardar los datos en la base de datos
            val dbBarber = DBBarbers(this)

            // Insertar los datos de la barbería (incluyendo la imagen en formato ByteArray)
            val resultId = dbBarber.insertarBarber(id, name, phone, email, imgByteArray)

            // Verificar el resultado
            if (resultId > 0) {
                Toast.makeText(this, "Barbero guardado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Capturar cualquier excepción y mostrar el mensaje
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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
    /* private fun showConfirmationDialog(action: String) {
        val message =
            if (action == "delete") "¿Seguro que deseas eliminar el contenedor?" else "¿Seguro que deseas actualizar el contenedor?"

        AlertDialog.Builder(this).apply {
            setTitle("Confirmación")
            setMessage(message)
            setPositiveButton("Sí") { _, _ ->
                when (action) {
                   "delete" -> performDeleteBarber()
                    "update" -> performUpdateBarber()
                }
            }
            setNegativeButton("No", null)
        }.show()
    }*/


   /* private fun performUpdateBarber() {
        try {
            val imgUri = imageB.drawable?.let { drawable ->
                val bitmap = (drawable as BitmapDrawable).bitmap
                saveImageToInternalStorage(bitmap)
            }
            val obBarb = Barber(
                id = cedB.text.toString(),
                name = nameB.text.toString(),
                phone = telB.text.toString().toIntOrNull() ?: -1,
                email = emailB.text.toString(),
                img = imgUri.toString()
            )
            modelB.updateBarber(obBarb)
            Toast.makeText(this, R.string.Succes, Toast.LENGTH_LONG).show()
            util.openActivity(this, barberCustomList::class.java)
        }catch (e: Exception) {
            Toast.makeText(this, "Error al cargar los datos: ${e.message}", Toast.LENGTH_LONG).show()
        }

    }
    private fun performDeleteBarber() {
        modelB.remBarber(cedB.text.toString())
        Toast.makeText(this, R.string.delet, Toast.LENGTH_LONG).show()


        util.openActivity(this, barberCustomList::class.java)
    }*/

    /*Al insertar el clean barber me da un error a cargar la imagen y no pude resolverlo*/
}