package com.example.firstappmobilekotlin.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.MediaStore.Audio.Genres
import com.example.firstappmobilekotlin.data.User

public class ChatCodeDatabase(mContext: Context) : SQLiteOpenHelper(
    mContext,
    DB_NAME,
    null,
    DB_VERSION
){
    override fun onCreate(db: SQLiteDatabase?) {
        // Création des tables
        val createTableUser = """
            CREATE TABLE users(
                $USER_ID integer PRIMARY KEY,
                $FIRST_NAME varchar(50),
                $LAST_NAME varchar(255),
                $EMAIL varchar(100),
                $PASSWORD varchar(20),
                $PROFESSION varchar(100),
                $GENRE_USERS varchar(8)
            )
        """.trimIndent()
        db?.execSQL(createTableUser)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // La suppression des anciennes tables  et
        // la création des nouvelles tables
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        onCreate(db)
    }

    // Methode de création de la base de données

    fun addUser(user: User): Boolean {
        // Inserer un nouveau utilisateur dans la base de données

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRST_NAME, user.nom)
        values.put(LAST_NAME, user.prenoms)
        values.put(EMAIL, user.email)
        values.put(PASSWORD, user.password)
        values.put(PROFESSION, user.profession)
        values.put(GENRE_USERS, user.genre)

        val result = db.insert(USER_TABLE_NAME, null, values).toInt()

        db.close()

        return result != -1
    }

    // Methode de recherche d'utilisateur
    fun findUser(email: String, password: String) : User? {

        var user: User? = null
        val db = this.readableDatabase
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(USER_TABLE_NAME, null, "$EMAIL=? AND $PASSWORD=?", selectionArgs, null, null, null)

        if (cursor != null){
            if (cursor.moveToFirst()){
                val id = cursor.getInt(0)
                val nom = cursor.getString(1)
                val prenom = cursor.getString(2)
                val email = cursor.getString(3)
                val profession = cursor.getString(4)
                val genre = cursor.getString(5)
                val user = User(id, nom, prenom, email, "", profession, genre)
                return user
            }
        }
        db.close()
        return user
    }

    companion object {
        private val DB_NAME = "chatcode_db"
        private val DB_VERSION = 1
        private val  USER_TABLE_NAME = "users"
        private val USER_ID = "id"
        private val FIRST_NAME = "nom"
        private val LAST_NAME = "prenoms"
        private val EMAIL = "email"
        private val PASSWORD = "password"
        private val PROFESSION = "profession"
        private val GENRE_USERS = "genre"

    }
}
