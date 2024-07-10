package com.example.firstappmobilekotlin

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firstappmobilekotlin.data.Sujet

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // 1. Recuperer l'email envoyé par l'activitéMain
        val email = intent.getStringExtra("email")

        // Création d'une variable qui va referencier listView
        val listCategSujet = findViewById<ListView>(R.id.listCategoriesSujets)

        // Création d'une variable qui va contenir la categorie des sujets pour le moment
        val categSujetArray = arrayListOf(
            Sujet("Angular", "Une description 1 de la categorie de sujet 1 sera affichée ici au lieu de ce texte qui ne veut rien dire", R.drawable.angular),
            Sujet("Java", "Une description 1 de la categorie de sujet 1 sera affichée ici au lieu de ce texte qui ne veut rien dire", R.drawable.java),
            Sujet("Flutter", "Une description 1 de la categorie de sujet 1 sera affichée ici au lieu de ce texte qui ne veut rien dire", R.drawable.flutter),
            Sujet("Xamarin", "Une description 1 de la categorie de sujet 1 sera affichée ici au lieu de ce texte qui ne veut rien dire", R.drawable.xamarin),
            Sujet("Csharpe (C#)", "Une description 1 de la categorie de sujet 1 sera affichée ici au lieu de ce texte qui ne veut rien dire", R.drawable.csharpe),
            Sujet("Kotlin", "Une description 1 de la categorie de sujet 1 sera affichée ici au lieu de ce texte qui ne veut rien dire", R.drawable.kotlin),
            Sujet("Dart", "Une description 1 de la categorie de sujet 1 sera affichée ici au lieu de ce texte qui ne veut rien dire", R.drawable.dart)
        )

        // Création d'un adaptateur pour adapter la valeur 'categSujetArray' au design
        val adapter = CategSujetAdapter(this, R.layout.item_categ_sujet, categSujetArray)
        // Affecter l'adapter à notre listView
         listCategSujet.adapter = adapter

        listCategSujet.setOnItemClickListener { adapterView, view, position, id ->
            // Toast.makeText(this, "Position : $position", Toast.LENGTH_LONG).show()
            val clickedSujet = categSujetArray[position]
            Intent(this, PostDetailsActivity::class.java).also {
                it.putExtra("titre", clickedSujet.titre)
                startActivity(it)
            }
        }

    } // fin methode onCreate()

    // Methode onCreateOptionsMenu pour acceder au menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // menuInflater pour transformer le fichier xml du menu en view
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Pour le contexte menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId ){
            R.id.itemAddSujet -> {
                Toast.makeText(this, "Add new subject !", Toast.LENGTH_LONG).show()
            }
            R.id.itemConfig -> {
                Toast.makeText(this, "Configurer l'application", Toast.LENGTH_LONG).show()
            }
            R.id.itemLogout -> {
                // finish()
                // Afficher un dialog de confirmation
                showLogoutConfirmDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Methode de confirmation
    fun showLogoutConfirmDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation!")
        builder.setMessage("Êtes-vous sûr de vouloir quitter l'application ?")
        builder.setPositiveButton("Oui", {dialogInterface: DialogInterface, id: Int ->
            val editor = this.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
            editor.remove("is_authentificated")
            editor.apply()
            finish()
        })
        builder.setNegativeButton("Non"){
            dialogInterface: DialogInterface, id:Int -> dialogInterface.dismiss()
        }
        builder.setNeutralButton("Annuler"){ dialogInterface: DialogInterface, id:Int ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }



}