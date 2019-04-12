package com.example.vncalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_iniciar_sesion.*
import kotlinx.android.synthetic.main.activity_iniciar_sesion.view.*

class IniciarSesion : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        auth = FirebaseAuth.getInstance()

        val button_iniciar_sesion= findViewById<Button>(R.id.button_iniciar_sesion)
        val button_regresar = findViewById<Button>(R.id.button_regresar_sesion)

        button_iniciar_sesion.setOnClickListener{

            autenticarUsuario()

            //finish()
            //val myintent = Intent(this,MainActivity::class.java)
            //startActivity(myintent)

        }

        button_regresar_sesion.setOnClickListener{
            Toast.makeText(this,"Regresar",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

    private fun autenticarUsuario(){
        val correoIniciarSesion = correoiniciar_edittext_iniciarsesion.text.toString()
        val contrasenaIniciarSesion = contrasenainiciar_edittext_iniciarsesion.text.toString()

        if(correoIniciarSesion.isEmpty()||contrasenaIniciarSesion.isEmpty()){
            Toast.makeText(this,"Por favor, ingrese correo y contraseña",Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(correoIniciarSesion,contrasenaIniciarSesion)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //Else if succesful

                Log.d("Iniciar","Se ha iniciado sesion el usuario: ${it.result.user.uid}")
                Toast.makeText(this,"Ha iniciado sesión con éxito! ",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Log.d("Iniciar","No se ha podido iniciar sesión usuario: ${it.message}")
                Toast.makeText(this,"No se ha podido iniciar sesión usuario: ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }
}
