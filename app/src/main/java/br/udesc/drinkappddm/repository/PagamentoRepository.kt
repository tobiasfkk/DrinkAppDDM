package br.udesc.drinkappddm.repository

import br.udesc.drinkappddm.Model.Pagamento
import br.udesc.drinkappddm.Model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PagamentoRepository {
    private val db = FirebaseFirestore.getInstance()
    private val pagamentoCollection = db.collection("pagamento")

   suspend fun realizarPagamentoComCartao(numerocartao: Pagamento) {
        try {
            pagamentoCollection.add(numerocartao).await()
        } catch (e: Exception) {
            // Tratar possíveis exceções aqui

        }


    }

}