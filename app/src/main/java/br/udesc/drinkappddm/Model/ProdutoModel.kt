package br.udesc.drinkappddm.Model

data class Produto(
    val nome: String,
    val imagem: String,
    val preco: Double,
    val categoria: Categoria = Categoria(""),
    val descricao: String,
    val quantidadeEstoque: Number
)
