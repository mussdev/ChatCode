package com.example.firstappmobilekotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.firstappmobilekotlin.data.User
import com.example.firstappmobilekotlin.db.ChatCodeDatabase

class RegistrationActivity : AppCompatActivity() {

    var isRadioButtonChecked = false
    // Declarer la base de données
    lateinit var db: ChatCodeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        db = ChatCodeDatabase(this)
        // Declaration des variables
        val nom = findViewById<EditText>(R.id.nom)
        val prenoms = findViewById<EditText>(R.id.prenoms)
        val email = findViewById<EditText>(R.id.email)
        val userPasssword = findViewById<EditText>(R.id.userPassword)
        val profession = findViewById<EditText>(R.id.profession)
        val typeHomme = findViewById<RadioButton>(R.id.typeHomme)
        val typeFemme = findViewById<RadioButton>(R.id.typeFemme)
        val btnSubmitInscription = findViewById<Button>(R.id.btnSubmitInscription)
        val errorInscriptions = findViewById<TextView>(R.id.errorInscriptions)
        val userPasswordConfirm = findViewById<EditText>(R.id.userPasswordConfirm)

        typeFemme.setOnClickListener{
            if(isRadioButtonChecked){
                typeFemme.isChecked = false
                isRadioButtonChecked = false
            }else{
                typeFemme.isChecked = true
                isRadioButtonChecked = true
                typeHomme.isChecked = false
            }
        }

        typeHomme.setOnClickListener{
            if(isRadioButtonChecked){
                typeHomme.isChecked = false
                isRadioButtonChecked = false
            }else{
                typeHomme.isChecked = true
                isRadioButtonChecked = true
                typeFemme.isChecked = false
            }
        }

        btnSubmitInscription.setOnClickListener{
            val txtNom = nom.text.toString()
            val txtPrenoms = prenoms.text.toString()
            val txtMail = email.text.toString()
            val txtUserPasssword = userPasssword.text.toString()
            val txtProfession = profession.text.toString()
            val txtTypeHomme = typeHomme.text.toString()
            val txtTypeFemme = typeFemme.text.toString()
            val txtUserPasswordConfirm = userPasswordConfirm.text.toString()
            var genreUtilisateur =  " "

            errorInscriptions.visibility = View.GONE

            println("Genre : ${typeFemme.isChecked}")

            if(txtNom.trim().isEmpty() || txtPrenoms.trim().isEmpty() || txtMail.trim().isEmpty()
                || txtUserPasssword.trim().isEmpty() || txtUserPasssword.trim().isEmpty() || txtProfession.trim().isEmpty() &&
                (txtTypeHomme.trim().isEmpty() || txtTypeFemme.trim().isEmpty())){

                // Saisir message
                errorInscriptions.text = getString(R.string.error_empty_fields)
                errorInscriptions.visibility = View.VISIBLE
            }else{
                if (!txtUserPasssword.trim().equals(txtUserPasswordConfirm.trim())){
                    // L'adresse mail doit être unique
                    errorInscriptions.text = getString(R.string.error_password)
                    errorInscriptions.visibility = View.VISIBLE
                }else{
                    // Nous vous avons envoyé un mail de confirmation sur votre adresse
                   // errorInscriptions.text = "Allez dans la boîte de message de votre adresse ${mail.text} pour réinitialiser votre mot de passe"
                  //  errorInscriptions.visibility = View.VISIBLE

                    if(typeHomme.isChecked == false && typeFemme.isChecked==false){
                        errorInscriptions.text = getString(R.string.error_genre_user_not_checked_two_buttonRadio)
                        errorInscriptions.visibility = View.VISIBLE
                    }else if(typeHomme.isChecked == true && typeFemme.isChecked==true){
                        errorInscriptions.text = getString(R.string.error_genre_user_checked_two_buttonRadio)
                        errorInscriptions.visibility = View.VISIBLE
                    } else if(typeHomme.isChecked==true){
                        genreUtilisateur = txtTypeHomme
                        Toast.makeText(this, "le genre sélectionné : $genreUtilisateur", Toast.LENGTH_LONG).show()
                    }else{
                        genreUtilisateur = txtTypeFemme
                        Toast.makeText(this, "le genre sélectionné : $genreUtilisateur", Toast.LENGTH_LONG).show()
                    }

                    val user = User(txtNom, txtPrenoms, txtMail, txtUserPasssword, txtProfession, genreUtilisateur)
                    val isInsertedUser = db.addUser(user)

                    if(isInsertedUser){
                        Toast.makeText(this, getString(R.string.succes_register), Toast.LENGTH_LONG).show()
                        Intent(this, HomeActivity::class.java).also {
                            it.putExtra("email", txtMail)
                            startActivity(it)
                        }
                        finish()
                    }
                }
            }
        }

    }
}