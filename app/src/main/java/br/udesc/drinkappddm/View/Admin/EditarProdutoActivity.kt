package br.udesc.drinkappddm.View.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.View.ListagemCategoriaActivity
import br.udesc.drinkappddm.repository.ProdutoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditarProdutoActivity : AppCompatActivity() {
    private lateinit var nomeEditText: EditText
    private lateinit var imagemEditText: EditText
    private lateinit var precoEditText: EditText
    private lateinit var descricaoEditText: EditText
    private lateinit var quantidadeEstoqueEditText: EditText
    private lateinit var salvarButton: Button

    private val produtoRepository = ProdutoRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_produto)

        nomeEditText = findViewById(R.id.nomeEditText)
        imagemEditText = findViewById(R.id.imagemEditText)
        precoEditText = findViewById(R.id.precoEditText)
        descricaoEditText = findViewById(R.id.descricaoEditText)
        quantidadeEstoqueEditText = findViewById(R.id.quantidadeEstoqueEditText)
        salvarButton = findViewById(R.id.salvarButton)

        val produto = intent.getSerializableExtra("produto") as? Produto
        produto?.let {
            nomeEditText.setText(it.nome)
            imagemEditText.setText(it.imagem)
            precoEditText.setText(it.preco.toString())
            descricaoEditText.setText(it.descricao)
            quantidadeEstoqueEditText.setText(it.quantidadeEstoque.toString())
        }

        salvarButton.setOnClickListener {
            produto?.let {
                val produtoAtualizado = Produto(
                    nome = nomeEditText.text.toString(),
                    imagem = imagemEditText.text.toString(),
                    preco = precoEditText.text.toString().toDouble(),
                    categoria = it.categoria,
                    descricao = descricaoEditText.text.toString(),
                    quantidadeEstoque = quantidadeEstoqueEditText.text.toString().toInt()
                )

                val contexto = this@EditarProdutoActivity

                CoroutineScope(Dispatchers.IO).launch {
                    produtoRepository.atualizarProduto(produtoAtualizado)
                    val intent = Intent(contexto, ListagemCategoriaActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
