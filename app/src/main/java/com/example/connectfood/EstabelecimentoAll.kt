package com.example.connectfood

data class EstabelecimentoAll(
    val name: String,
    val description: String,
    var distance: String,
    val photo: String,
    var cnpj: String,
    var loggedUserType: String,
    var loggedUserId: String,
    var loggedUserName: String,
    var loggedUserPhoto: String,
    var loggedUserLocation: String
)

