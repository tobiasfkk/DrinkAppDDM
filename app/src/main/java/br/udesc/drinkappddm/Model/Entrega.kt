package br.udesc.drinkappddm.Model

import java.io.Serializable

data class Entrega(
    val endereco: String = "",
    val numero: String,
    val bairro: String =""
) : Serializable
