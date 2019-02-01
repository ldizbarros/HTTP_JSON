package com.dam18.project.http_json

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
/////////////////////////////
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import org.jetbrains.anko.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    // Etiqueta de los logs
    val LOGTAG = "requestwp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cuando le damos al boton empezamos una corutina
        btnStart.setOnClickListener { perticionWP() }
    }

    /**
     * Hace una peticion a Wordpress. Utiliza API Request
     */
    fun perticionWP(){
        var respuesta = ""

        // Lanzo la corutina. No se lanzara en el Hilo Principal
        doAsync {

            // Peticion a WordPress
            respuesta = URL("http://18.223.121.82/wp5/?rest_route=/wp/v2/posts/1").readText()

            // Accedemos al hilo principal
            uiThread {
                txtRespuesta.text = respuesta
                Log.d(LOGTAG, respuesta)
                longToast("Request performed")
            }
        }
    }
}
