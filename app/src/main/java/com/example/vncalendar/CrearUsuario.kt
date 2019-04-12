package com.example.vncalendar

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_crear_usuario.*
import java.util.*


//Firebase Tutorial https://www.youtube.com/watch?v=RYyri2W3Tho
class CrearUsuario : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)

        auth = FirebaseAuth.getInstance()

        registrar_button_registro.setOnClickListener {
            registrarUsuario()
        }

        selecionarfoto_button_registro.setOnClickListener {
            Log.d("CrearUsuario","Se presiono boton de selecionar foto")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }


        ya_posee_cuenta_textview_registro.setOnClickListener {
            val myintent= Intent(this,IniciarSesion::class.java)
            startActivity(myintent)
        }
    }

    var uriFotoSeleccionada: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //Proceder y verificar la imagen seleccionada

            Log.d("CrearUsuario","Foto fue seleccionada")

            uriFotoSeleccionada = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uriFotoSeleccionada)

            val bitmapDrawable = BitmapDrawable(bitmap)
            selecionarfoto_button_registro.setBackgroundDrawable(bitmapDrawable)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

    private fun registrarUsuario(){
        val emailRegistro = email_edittext_registro.text.toString()
        val contrasenaRegistro = contrasena_edittext_registro.text.toString()

        if(emailRegistro.isEmpty()||contrasenaRegistro.isEmpty()){
            Toast.makeText(this,"Por favor, ingrese correo y contraseña",Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("CrearUsuario","El correo es: "+emailRegistro)
        Log.d("CrearUsuario","La contraseña es: $contrasenaRegistro")



        //Firebase Authenticaction to create a user with email and passsword
        auth.createUserWithEmailAndPassword(emailRegistro,contrasenaRegistro)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener

                //Else if succesful

                Log.d("CrearUsuario","Se ha creado el usuario: ${it.result.user.uid}")
                Toast.makeText(this,"Se ha creado el usuario exitosamente! ",Toast.LENGTH_SHORT).show()

                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Log.d("CrearUsuario","No se ha podido crear usuario: ${it.message}")
                Toast.makeText(this,"No se ha podido crear usuario: ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage(){

        if(uriFotoSeleccionada == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(uriFotoSeleccionada!!)
            .addOnSuccessListener {
                Log.d("CrearUsuario", "Se ha subido la foto exitosamente: ${it.metadata?.path}")
            }
    }
}
