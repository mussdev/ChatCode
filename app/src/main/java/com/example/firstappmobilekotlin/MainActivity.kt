package com.example.firstappmobilekotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.firstappmobilekotlin.db.ChatCodeDatabase

class MainActivity : AppCompatActivity() {

    /*
    *   Création d'une variable globale qui va se chager de verifier si l'utilisateur
    * connecté est bien deconnecté
     */

    lateinit var sharedPreferences: SharedPreferences
    lateinit var db: ChatCodeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        db = ChatCodeDatabase(this)
        val isAuthentificated = sharedPreferences.getBoolean("is_authentificated", false)
        val emailSharedPreferences = sharedPreferences.getString("email", "")
        if (isAuthentificated){
            Intent(this, HomeActivity::class.java).also {
                it.putExtra("email", emailSharedPreferences)
                startActivity(it)
            }
        }
        // Declaration des variables
        val connect = findViewById<Button>(R.id.connect)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val error = findViewById<TextView>(R.id.error)
        val inscription  = findViewById<TextView>(R.id.inscription)



        connect.setOnClickListener{
            error.visibility = View.GONE
            val txtEmail = email.text.toString()
            val txtPassword = password.text.toString()
            if (txtEmail.trim().isEmpty() || txtPassword.trim().isEmpty()){
                error.text ="Vous devez remplir tous les champs !"
                error.visibility = View.VISIBLE
            }else{
                var user = db.findUser(txtEmail, txtPassword)
                if (user != null){
                    // Initialisons les variables de connexion à null
                    email.setText("")
                    password.setText("")

                    // Enregistrer dans sharePreferences le boolean isAuthentificated
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("is_authentificated", true)
                    editor.putString("email", txtEmail)
                    editor.apply()

                    // Encore une autre manière le plus utilisée
                    // Intent explicite
                    Intent(this, HomeActivity::class.java).also {
                        it.putExtra("email", txtEmail)
                        startActivity(it)
                    }
                }else{
                    error.text =getString(R.string.error_authentication)
                    error.visibility = View.VISIBLE
                }

            }

        }

        // Methode pour acceder à la page d'inscription
        inscription.setOnClickListener{
            Intent(this, RegistrationActivity::class.java).also{
                startActivity(it)
            }

        }
    }

}