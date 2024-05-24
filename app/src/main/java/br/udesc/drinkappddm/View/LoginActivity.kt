package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.View.Admin.GerenciarProdutoActivity
import br.udesc.drinkappddm.ViewModel.CarrinhoViewModel
import br.udesc.drinkappddm.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth //autenticação para o FireBASe - J.parro
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitNetwork().build())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth//J.Parro

        binding?.btnLogin?.setOnClickListener{//RECUPERANDO OS DADOS DA TELA DE LOGIN
            val email: String = binding?.etEmail?.text.toString()
            val password: String = binding?.etPassword?.text.toString()

           // val email: String = "teste@teste.com"
            //val password: String = "12345678"


            if(email.isNotEmpty() && password.isNotEmpty()){
                signInWithEmailAndPassword(email,password)
            }else {
                Toast.makeText(this@LoginActivity,"Por favor, preencha os campos",Toast.LENGTH_SHORT).show()
            }
        }

        binding?.tvCreateAccount?.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun startActivity() {

//      val intent = Intent(this, CatalogoCategoriaActivity::class.java)
        val intent = Intent(this, PagamentoActivity::class.java)
//      val intent = Intent(this, ProdutoActivity::class.java)
//      val intent = Intent(this, GerenciarProdutoActivity::class.java)

        startActivity(intent)

    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInUserWithEmailAndPassword")
                Toast.makeText(baseContext, "Login Sucess", Toast.LENGTH_SHORT).show()
                val carrinhoViewModel = ViewModelProvider(this).get(CarrinhoViewModel::class.java)
                carrinhoViewModel.limparCarrinho()

                val intent = if (email == "adm@adm.com") {
                    Intent(this, GerenciarProdutoActivity::class.java)
                } else {
                    Intent(this, CatalogoCategoriaActivity::class.java)
                }
                startActivity(intent)
            } else {
                Log.w(TAG, "signInUserWithEmailAndPassword:Failure", task.exception)
                Toast.makeText(baseContext, "Authentication Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object{
        private var TAG = "EmailAndPassaWord"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}