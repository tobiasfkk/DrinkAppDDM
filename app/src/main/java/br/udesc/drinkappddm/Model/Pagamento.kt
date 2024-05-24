package br.udesc.drinkappddm.Model

import java.io.Serializable

data class Pagamento(
    val cartao: String = "",
    val total: Double,
    val endereco: String = "",
    val numero: String = "",
    val bairro: String = ""
) : Serializable
