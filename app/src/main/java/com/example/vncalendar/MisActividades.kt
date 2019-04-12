package com.example.vncalendar

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vncalendar.adapters.MiActividadAdapter
import com.example.vncalendar.data.MiActividad
import com.example.vncalendar.viewmodels.MiActividadViewModelMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_iniciar_sesion.*
import kotlinx.android.synthetic.main.activity_mis_actividades.*


//Basado en el código del repositorio https://github.com/berkeatac/Notes-App

//FIrebase basado en https://www.youtube.com/watch?v=SuRiwVF5bzs&t=902s
class MisActividades : AppCompatActivity() {

    companion object {
        const val ADD_MIACTIVIDAD_REQUEST = 1
        const val EDIT_MIACTIVIDAD_REQUEST = 2
        const val VER_CONTACTO_REQUEST = 3

       lateinit var miActividadViewModelMain: MiActividadViewModelMain

    }


//      CÓDIGO DEL LABORATORIO 7 PARA REFERENCIA
//    private lateinit var contactoViewModelEdit: ContactoViewModelEdit
//    private lateinit var contactoViewModelCreate: ContactoViewModelCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_actividades)

        verifyUserIsLoggedIn()

        //fetchUsers()

        fetchUsuario()

        //supportActionBar?.setDisplayShowTitleEnabled(false)


        button_add_actividad.setOnClickListener{
            startActivityForResult(
                Intent(this,AgregarActividad::class.java),
                ADD_MIACTIVIDAD_REQUEST
            )
        }

        recycler_view_misactividades.layoutManager = LinearLayoutManager(this )
        recycler_view_misactividades.setHasFixedSize(true)

        var adapter = MiActividadAdapter()

        recycler_view_misactividades.adapter = adapter



        miActividadViewModelMain = ViewModelProviders.of(this).get(MiActividadViewModelMain::class.java)

        miActividadViewModelMain.setUid(FirebaseAuth.getInstance().uid.toString())

        miActividadViewModelMain.getAllMisActividades().observe(this,Observer<List<MiActividad>> {
            adapter.submitList(it)
        })
        
        

        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                miActividadViewModelMain.delete(adapter.getMiActividadAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext,"Actividad Eliminada!", Toast.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(recycler_view_misactividades)

        adapter.setOnItemClickListener(object :MiActividadAdapter.OnItemClickListener{
            override fun onItemClick(miActividad: MiActividad) {
                var intent = Intent(baseContext,AgregarActividad::class.java)

                //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
                intent.putExtra(AgregarActividad.EXTRA_ID,miActividad.id)
                intent.putExtra(AgregarActividad.EXTRA_TITULO_ACTIVIDAD,miActividad.titulo)
                intent.putExtra(AgregarActividad.EXTRA_DESCRIPCION_ACTIVIDAD,miActividad.descipcion)
                intent.putExtra(AgregarActividad.EXTRA_TIPO_ACTIVIDAD,miActividad.tipoActividad)
                //intent.putExtra(AgregarActividad.EXTRA_TIPO_VIBRACION,miActividad.tipoVibracion)
                intent.putExtra(AgregarActividad.EXTRA_DIA_ACTIVIDAD,miActividad.diaActividad)
                intent.putExtra(AgregarActividad.EXTRA_MES_ACTIVIDAD,miActividad.mesActividad)
                intent.putExtra(AgregarActividad.EXTRA_ANO_ACTIVIDAD,miActividad.anoActividad)
                intent.putExtra(AgregarActividad.EXTRA_HORA_ACTIVIDAD,miActividad.horaActividad)
                intent.putExtra(AgregarActividad.EXTRA_MINUTO_ACTIVIDAD,miActividad.minutoActividad)
                intent.putExtra(AgregarActividad.EXTRA_PRIORITY,miActividad.priority)

                startActivityForResult(intent, EDIT_MIACTIVIDAD_REQUEST)
            }
        })

    }


    private fun verifyUserIsLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null){
            val intent = Intent(this,CrearUsuario::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    fun fetchUsuario(){

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val rootRef = FirebaseDatabase.getInstance().reference
        val uidRef = rootRef.child("users").child(uid)

        val valueEventListener = object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val usuario = p0.getValue(User::class.java)

                Log.d("Snapshot2",p0.value.toString())
                Log.d("Snapshot",usuario?.profileImageUrl)
                Log.d("Snapshot3",usuario.toString())

                if(usuario?.profileImageUrl!=null){

                    Picasso.get().load(usuario.profileImageUrl.toString()).into(fotoperfil_circleimageview_misactividades)
                    fotoperfil_circleimageview_misactividades.bringToFront()

                    //Picasso.get().load(usuario?.profileImageUrl.toString()).into(button_add_actividad)
                }

                //Picasso.get().load(usuario?.profileImageUrl.toString()).into(button_add_actividad)


            }

            override fun onCancelled(p0: DatabaseError) {

            }
        }
        uidRef.addListenerForSingleValueEvent(valueEventListener)
//        FirebaseDatabase.getInstance().reference.child("users").addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                Log.d("Snapshot",p0!!.value.toString())
//
//                val usuario = p0!!.value.toString()
//
//                Log.d("Snapshot",usuario[2].toString())
//
//                if(usuario[2].toString()!=null){
//
//                    Picasso.get().load(usuario[1].toString()).into(fotoperfil_circleimageview_misactividades)
//                    fotoperfil_circleimageview_misactividades.bringToFront()
//
//                    Picasso.get().load(usuario[1].toString()).into(button_add_actividad)
//                }
//
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//            }
//        })
    }

    private fun fetchUsers(){
        val ref = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().uid.toString())

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val ref2 = p0.child(FirebaseAuth.getInstance().uid.toString())
                //p0.children.forEach {
                Log.d("Usuarios",ref2.toString())
                val user = ref2.getValue(User::class.java)


                Picasso.get().load(user?.profileImageUrl).into(fotoperfil_circleimageview_misactividades)
                fotoperfil_circleimageview_misactividades.bringToFront()
                //Thread.sleep(10000)
                //supportActionBar?.setIcon()
                //}
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        miActividadViewModelMain.setUid(FirebaseAuth.getInstance().uid.toString())

        fetchUsuario()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_cerrar_sesion -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this,IniciarSesion::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.action_settings ->{
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this,CrearUsuario::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
//Código de referencia del laboratorio 7
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return when (item?.itemId){
//            R.id.delete_all_contactos -> {
//                contactoViewModelMain.deleteAllContactos()
//                Toast.makeText(this, "Todos los contactos eliminados", Toast.LENGTH_SHORT).show()
//                true
//            }
//            else -> {
//                super.onOptionsItemSelected(item)
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Nótese que si se cambia algo en la clase MiActividad aquí también se debe considerar el cambio
        if (requestCode== ADD_MIACTIVIDAD_REQUEST && resultCode == Activity.RESULT_OK){
            val newMiActividad = MiActividad (
                data!!.getStringExtra(AgregarActividad.EXTRA_TITULO_ACTIVIDAD),
                data.getStringExtra(AgregarActividad.EXTRA_DESCRIPCION_ACTIVIDAD),
                data.getIntExtra(AgregarActividad.EXTRA_TIPO_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_ANO_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_MES_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_DIA_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_HORA_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_MINUTO_ACTIVIDAD,1),

                data.getIntExtra(AgregarActividad.EXTRA_PRIORITY,1),
                data.getStringExtra(AgregarActividad.EXTRA_USERID)
            )

            miActividadViewModelMain.insert(newMiActividad)

            Toast.makeText(this, "Se ha creado una nueva actividad!", Toast.LENGTH_SHORT).show()
        }else if (requestCode == EDIT_MIACTIVIDAD_REQUEST && resultCode == Activity.RESULT_OK){
            val id = data?.getIntExtra(AgregarActividad.EXTRA_ID,-1)

            if (id == -1){
                Toast.makeText(this, "Lo sentimos, no se ha podido actualizar! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateMiActividad = MiActividad (
                data!!.getStringExtra(AgregarActividad.EXTRA_TITULO_ACTIVIDAD),
                data.getStringExtra(AgregarActividad.EXTRA_DESCRIPCION_ACTIVIDAD),
                data.getIntExtra(AgregarActividad.EXTRA_TIPO_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_ANO_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_MES_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_DIA_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_HORA_ACTIVIDAD,1),
                data.getIntExtra(AgregarActividad.EXTRA_MINUTO_ACTIVIDAD,1),


                data.getIntExtra(AgregarActividad.EXTRA_PRIORITY,1),
                data.getStringExtra(AgregarActividad.EXTRA_USERID)
            )

            updateMiActividad.id = data.getIntExtra(AgregarActividad.EXTRA_ID,-1)
            miActividadViewModelMain.update(updateMiActividad)
        }
        else {
            //Toast.makeText(this, "Contacto not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}
