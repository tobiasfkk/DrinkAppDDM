package br.udesc.drinkappddm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.udesc.drinkappddm.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth //autenticação para o FireBASe - J.parro
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth//J.Parro

        binding?.btnLogin?.setOnClickListener{//RECUPERANDO OS DADOS DA TELA DE LOGIN
            val email: String = binding?.etEmail?.text.toString()
            val password: String = binding?.etPassword?.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                signInWithEmailAndPassword(email,password)
            }else {
                Toast.makeText(this@MainActivity,"Por favor, preencha os campos",Toast.LENGTH_SHORT).show()
            }
        }

        binding?.tvCreateAccount?.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Log.d(TAG,"signInUserWithEmailAndPassWord")
                //val user = auth.currentUser
                Toast.makeText(baseContext, "Login Sucess", Toast.LENGTH_SHORT).show()
            }else {
                Log.w(TAG, "signInUserWithEmailAndPassaword:Failure", task.exception)
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