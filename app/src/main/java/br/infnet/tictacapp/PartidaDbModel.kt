package br.infnet.tictacapp

import com.google.firebase.firestore.FieldValue

class PartidaDbModel(
    val winner: String = "", val looser: String = ""
)