package br.udesc.drinkappddm.Model

import java.io.Serializable

data class Pagamento(
    val cartao: String = "",
    val total: Double
) : Serializable
