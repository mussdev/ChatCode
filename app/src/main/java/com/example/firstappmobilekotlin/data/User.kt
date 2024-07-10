package com.example.firstappmobilekotlin.data

data class User(
    var nom : String,
    var prenoms : String,
    var email : String,
    var password : String,
    var profession : String,
    var genre : String
){
    var id: Int = -1
    constructor(id: Int, nom: String, prenoms: String, email: String, password: String, profession: String, genre: String): this(nom, prenoms, email, password, profession, genre){
        this.id = id

    }
}
