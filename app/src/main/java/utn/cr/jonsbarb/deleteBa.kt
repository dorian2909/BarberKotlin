package utn.cr.jonsbarb

import Util.util
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class deleteBa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete_ba)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.crud_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_insert -> {
                util.openActivity(this, insertBarb::class.java)
                return true
            }

            R.id.mnu_delete -> {
                util.openActivity(this, deleteBa::class.java)

                return true
            }

            R.id.mnu_upd -> {
                util.openActivity(this, updateBarb::class.java)
                return true
            }

            R.id.mnu_date -> {
                util.openActivity(this, createDate::class.java)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}