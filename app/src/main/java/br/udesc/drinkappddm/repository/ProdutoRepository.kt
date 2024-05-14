package br.udesc.drinkappddm.repository

import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ProdutoRepository() {
    private val db = FirebaseFirestore.getInstance()
    private val produtosCollection = db.collection("produtos")
    private val categoriasCollection = db.collection("categorias")

    suspend fun addProduto(produto: Produto) {
        db.collection("produtos")
            .add(produto)
            .await()
    }
    suspend fun obterCategorias(): List<Categoria> {
        return try {
            val categoriasSnapshot = db.collection("categorias").get().await()
            val categoriasList = mutableListOf<Categoria>()
            for (categoriaSnapshot in categoriasSnapshot.documents) {

                val nomeCategoria = categoriaSnapshot.getString("nome") ?: continue
                categoriasList.add(Categoria(nomeCategoria))
            }
            categoriasList
        } catch (e: Exception) {
            // Lidar com erros de obtenção das categorias, por exemplo, lançar uma exceção ou retornar uma lista vazia
            emptyList()
        }
    }
}