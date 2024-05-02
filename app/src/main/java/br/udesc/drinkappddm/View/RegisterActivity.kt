package br.udesc.drinkappddm.View

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.udesc.drinkappddm.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private var binding: ActivityRegisterBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        binding?.btnRegister?.setOnClickListener{
            val email: String = binding?.etEmail?.text.toString()
            val password: String = binding?.etPassword?.text.toString()
            val confirmPassword: String = binding?.etConfirmPassword?.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if (password == confirmPassword){
                    createUserWithEmailAndPassaword(email,password)
                }else{
                    Toast.makeText(this@RegisterActivity,"Senha incompativel",Toast.LENGTH_SHORT).show()
                }

            }
        }
        binding?.btnVoltar?.setOnClickListener{
            finish()//aqui ta errado, ele destroi a activty , tem que ver como faz pra retornar para a tela usand oa arvore. J.PARRO
        }

    }
    private fun createUserWithEmailAndPassaword(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
            if (task.isSuccessful){
                Log.d(TAG,"createUserWithEmailAndPassaword:Sucess")
                val user = auth.currentUser
                Toast.makeText(baseContext, "Authentication Sucess", Toast.LENGTH_SHORT).show()

            }else{
                Log.w(TAG,"createUserWithEmailAndPassaword:Failure",task.exception)
            Toast.makeText(baseContext, "Authentication Failure", Toast.LENGTH_SHORT).show()
        }}
    }

        companion object{
            private var TAG = "EmailAndPassaWord"
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}