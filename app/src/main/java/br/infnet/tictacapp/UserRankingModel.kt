package br.infnet.tictacapp

import com.google.firebase.firestore.FieldValue

class UserRankingModel(
    val player: String = "", val wins: FieldValue
)