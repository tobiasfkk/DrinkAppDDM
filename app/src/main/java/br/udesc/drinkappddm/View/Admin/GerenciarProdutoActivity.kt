package br.udesc.drinkappddm.View.Admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.udesc.drinkappddm.View.ListagemCategoriaActivity
import br.udesc.drinkappddm.View.ProdutoActivity
import br.udesc.drinkappddm.databinding.ActivityGerenciarprodutoBinding

class GerenciarProdutoActivity : AppCompatActivity(){
    private var binding: ActivityGerenciarprodutoBinding? = null  //binding recupera as coisas da tela

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerenciarprodutoBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnCadastrarproduto?.setOnClickListener{//quando alguém clicar no botão vai fazer alguma coisa
            val intent = Intent(this, ProdutoActivity::class.java)
            startActivity(intent)
        }

        binding?.btnEditarproduto?.setOnClickListener{
            val intent = Intent(this, ListagemCategoriaActivity::class.java)
            startActivity(intent)
        }


    }


}