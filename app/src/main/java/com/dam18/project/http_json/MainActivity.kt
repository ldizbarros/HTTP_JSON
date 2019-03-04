package com.dam18.project.http_json

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
/////////////////////////////
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import org.jetbrains.anko.*
import org.json.JSONArray
import java.net.URL

class MainActivity : AppCompatActivity() {

    // Etiqueta de los logs
    val LOGTAG = "requestwp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cuando le damos al boton empezamos una corutina
        btnStart.setOnClickListener {
            // visualizamos una barra de progreso para informar al usario
            // que estamos haciendo algo en segundo plano
            progressBar.visibility = VISIBLE
            perticionWP()
        }
    }

    /**
     * Hace una peticion a Wordpress. Utiliza API Request
     */
    fun perticionWP(){
        // 'high order function' doAsync(parametro funcion)
        // no necesitamos los parentesis, ponemos la funcion parametro dentro de las llaves
        // Lanzo la corutina. No se lanzara en el Hilo Principal
        doAsync {
            // capturamos los errores de la peticion
            try {
                // peticion a un servidor rest que devuelve un json generico
                //val respuesta = URL("https://jsonplaceholder.typicode.com/posts").readText()
                // Peticion a WordPress
                val respuesta = URL("http://18.191.248.177/wp5/?rest_route=/wp/v2/posts").readText()

                // parsing data
                // sabemos que recibimos un array de objetos JSON
                val miJSONArray = JSONArray(respuesta)
                // recorremos el Array
                for (jsonIndex in 0..(miJSONArray.length() - 1)) {
                    // creamos el objeto 'misDatos' a partir de la clase 'Datos'
                    // asignamos el valor de 'title' en el constructor de la data class 'Datos'
                    val misDatos = Datos(miJSONArray.getJSONObject(jsonIndex).getString("title"))
                    // salida procesada en Logcat
                    Log.d(LOGTAG, misDatos.toString())
                }
                // Accedemos al hilo principal
                uiThread {
                    progressBar.visibility = INVISIBLE
                    // rellenamos nuestro TextView con la respuesta sin procesar
                    txtRespuesta.text = respuesta
                    // Log.d(LOGTAG, respuesta)
                    longToast("Request performed")
                }
                // Si algo va mal lo capturamos
            } catch (e: Exception) {
                uiThread {
                    progressBar.visibility = INVISIBLE
                    longToast("Something go wrong: $e")
                }
            }
        }
    }
}
