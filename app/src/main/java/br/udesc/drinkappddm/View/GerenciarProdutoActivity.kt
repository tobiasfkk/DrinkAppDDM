package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.udesc.drinkappddm.databinding.ActivityGerenciarprodutoBinding
import br.udesc.drinkappddm.databinding.ActivityMainBinding

class GerenciarProdutoActivity : AppCompatActivity(){
    private var binding: ActivityGerenciarprodutoBinding? = null  //binding recupera as coisas da tela

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerenciarprodutoBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnCadastrarproduto?.setOnClickListener{//quando alguém clicar no botão vai fazer alguma coisa
            val intent = Intent(this,ProdutoActivity::class.java)
            startActivity(intent)

        }



    }


}