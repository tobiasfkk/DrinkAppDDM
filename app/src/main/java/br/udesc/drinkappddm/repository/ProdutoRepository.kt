package br.udesc.drinkappddm.repository

import br.udesc.drinkappddm.Model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ProdutoRepository() {
    private val db = FirebaseFirestore.getInstance()

    suspend fun addProduto(produto: Produto) {
        db.collection("produtos")
            .add(produto)
            .await()
    }
}
