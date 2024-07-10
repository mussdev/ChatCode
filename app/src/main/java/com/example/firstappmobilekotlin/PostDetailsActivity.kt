package com.example.firstappmobilekotlin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PostDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_details)

        val txtTitreSujet = findViewById<TextView>(R.id.txtTitreSujet)
        val titre = intent.getStringExtra("titre")
        txtTitreSujet.text = titre

        // Afficher le titre du sujet sur la barre en haut
        supportActionBar!!.title = titre
    }
}