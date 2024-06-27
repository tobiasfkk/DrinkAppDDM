package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.udesc.drinkappddm.Model.Categoria

class CatalogoProdutoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoriaSelecionada = intent.getSerializableExtra("categoria") as? Categoria

        setContent {
            if (categoriaSelecionada != null) {
                CatalogoProdutoScreen(
                    context = this,
                    categoriaSelecionada = categoriaSelecionada,
                    onVerCarrinhoClicked = {
                        val intent = Intent(this, CarrinhoActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
