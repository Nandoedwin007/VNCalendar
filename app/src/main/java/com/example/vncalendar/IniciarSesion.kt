package com.example.vncalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_iniciar_sesion.*
import kotlinx.android.synthetic.main.activity_iniciar_sesion.view.*

class IniciarSesion : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        auth = FirebaseAuth.getInstance()

        val button_iniciar_sesion= findViewById<Button>(R.id.button_iniciar_sesion)
        //val button_regresar = findViewById<Button>(R.id.button_regresar_sesion)

        button_iniciar_sesion.setOnClickListener{

            autenticarUsuario()

            //finish()
            //val myintent = Intent(this,MainActivity::class.java)
            //startActivity(myintent)

        }

        desea_crear_usuario_textview_iniciarsesion.setOnClickListener{

            val intent = Intent(this,CrearUsuario::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
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

                fetchUsers()


                val intent = Intent(this, MisActividades::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                Log.d("Iniciar","No se ha podido iniciar sesión usuario: ${it.message}")
                Toast.makeText(this,"No se ha podido iniciar sesión usuario: ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }

    fun sleep(millis:Long){
        throw InterruptedException()
    }

    private fun fetchUsers(){
        val ref = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().uid.toString())

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val ref2 = p0.child(FirebaseAuth.getInstance().uid.toString())
                //p0.children.forEach {
                    Log.d("Usuarios",ref2.toString())
                    val user = ref2.getValue(User::class.java)


                    Picasso.get().load(user?.profileImageUrl).into(foto_perfil_imageview_iniciar_sesion)
                    //Thread.sleep(10000)
                    //supportActionBar?.setIcon()
                //}
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}