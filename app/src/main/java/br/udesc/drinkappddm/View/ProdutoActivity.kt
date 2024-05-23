package br.udesc.drinkappddm.View
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.databinding.ActivityProdutoBinding
import br.udesc.drinkappddm.ViewModel.ProdutoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProdutoActivity : AppCompatActivity() {

    private lateinit var viewModel: ProdutoViewModel
    private lateinit var binding: ActivityProdutoBinding
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProdutoViewModel::class.java)

        // Configurar o Spinner de Categorias
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.produtoCategoriaSpinner.adapter = adapter
        // Obter categorias da ViewModel e preencher o Spinner
        viewModel.obterCategorias()
        viewModel.categorias.observe(this) { categorias ->
            categorias?.let {
                adapter.clear()
                for (categoria in it) {
                    adapter.add(categoria.nome)
                }
            }
        }

        // Configurar o clique no botão "Salvar Produto"
        binding.produtoSalvar.setOnClickListener {
            salvarProduto()
        }
        binding.carregarImagemButton.setOnClickListener {
            abrirGaleria()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.produtoImagemView.setImageURI(imageUri)
        }
    }

    private fun salvarProduto() {
        val produtoNome = binding.produtoNome.text.toString()
        val produtoPrecoStr = binding.produtoPreco.text.toString()
        val produtoPreco = produtoPrecoStr.toDoubleOrNull() ?: 0.0
        val categoriaSelecionada = binding.produtoCategoriaSpinner.selectedItem.toString()
        val descricao = binding.produtoDescricao.text.toString()
        val quantidadeEstoqueStr = binding.produtoQuantidadeEstoque.text.toString()
        val quantidadeEstoque = quantidadeEstoqueStr.toIntOrNull() ?: 0

        if (imageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val fileRef = storageRef.child("produtos/${System.currentTimeMillis()}.jpg")

            fileRef.putFile(imageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        val produto = Produto(produtoNome, imageUrl, produtoPreco, Categoria(categoriaSelecionada), descricao, quantidadeEstoque)

                        salvarProdutoNoFirestore(produto)
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao fazer upload da imagem: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            val produto = Produto(produtoNome, "", produtoPreco, Categoria(categoriaSelecionada), descricao, quantidadeEstoque)
            salvarProdutoNoFirestore(produto)
        }
    }

    private fun salvarProdutoNoFirestore(produto: Produto) {
        val db = FirebaseFirestore.getInstance()
        db.collection("produtos")
            .add(produto)
            .addOnSuccessListener {
                Toast.makeText(this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar produto: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun limparCampos() {
        binding.produtoNome.text.clear()
        binding.produtoImagem.text.clear()
        binding.produtoPreco.text.clear()
        binding.produtoDescricao.text.clear()
        binding.produtoQuantidadeEstoque.text.clear()
        binding.produtoCategoriaSpinner.setSelection(0)
        binding.produtoImagemView.setImageResource(R.drawable.ic_image)
    }
}
