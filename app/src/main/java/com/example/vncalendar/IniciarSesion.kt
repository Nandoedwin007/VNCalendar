package com.example.vncalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_iniciar_sesion.view.*

class IniciarSesion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        val button_iniciar_sesion= findViewById<Button>(R.id.button_iniciar_sesion)
        val button_regresar = findViewById<Button>(R.id.button_regresar)

        button_iniciar_sesion.setOnClickListener{
            Toast.makeText(this,"Se han validado las credenciales",Toast.LENGTH_SHORT).show()

            finish()
            //val myintent = Intent(this,MainActivity::class.java)
            //startActivity(myintent)

        }

        button_regresar.setOnClickListener{
            Toast.makeText(this,"Regresar",Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}
