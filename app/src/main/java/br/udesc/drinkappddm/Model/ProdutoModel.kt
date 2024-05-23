package br.udesc.drinkappddm.Model

data class Produto(
    val nome: String = "",
    val imagem: String = "",
    val preco: Double = 0.0,
    val categoria: Categoria = Categoria(""),
    val descricao: String = "",
    val quantidadeEstoque: Int = 0
)
