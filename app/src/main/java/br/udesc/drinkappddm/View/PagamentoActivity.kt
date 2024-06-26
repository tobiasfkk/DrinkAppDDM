package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.ViewModel.PagamentoViewModel
import br.udesc.drinkappddm.Model.Pagamento
import br.udesc.drinkappddm.ui.*
import kotlinx.coroutines.*

class PagamentoActivity : ComponentActivity() {

    private lateinit var viewModel: PagamentoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PagamentoViewModel::class.java)

        // Ler o valor total passado como parâmetro
        val total = intent.getDoubleExtra("total", 0.0)
        val endereco = intent.getStringExtra("endereco").toString()
        val numero = intent.getStringExtra("numero").toString()
        val bairro = intent.getStringExtra("bairro").toString()

        setContent {

            PagamentoScreen(total, endereco, numero, bairro, onPaymentCompleted = { pagamento ->
                viewModel.realizarPagamento(
                    nomeCartao = pagamento.cartao,
                    numeroCartao = pagamento.numero,
                    validadeCartao = pagamento.validade,
                    cvvCartao = pagamento.cvv
                )
                CoroutineScope(Dispatchers.Main).launch {
                    val resultado = viewModel.fetchPaymentValidation()
                    Toast.makeText(this@PagamentoActivity, resultado, Toast.LENGTH_SHORT).show()
                    viewModel.salvarPagamento(pagamento)
                    term()
                }
            })

        }
    }

    private fun term() {
        val intent = Intent(this@PagamentoActivity, CatalogoCategoriaActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun PagamentoScreen(
    total: Double,
    endereco: String,
    numero: String,
    bairro: String,
    onPaymentCompleted: (Pagamento) -> Unit
) {
    var nomeCartao by remember { mutableStateOf("") }
    var numeroCartao by remember { mutableStateOf("") }
    var validadeCartao by remember { mutableStateOf("") }
    var cvvCartao by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6EC7), Color(0xFF6EC7))
                )
            )
            .padding(40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PAGAMENTO",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Total: R$ %.2f".format(total),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF333333),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = nomeCartao,
            onValueChange = { nomeCartao = it },
            label = { Text("Nome no Cartão") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = numeroCartao,
            onValueChange = { numeroCartao = it },
            label = { Text("Número do Cartão") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = validadeCartao,
            onValueChange = { validadeCartao = it },
            label = { Text("Validade (MM/AA)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cvvCartao,
            onValueChange = { cvvCartao = it },
            label = { Text("CVV") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val pagamento = Pagamento(
                    cartao = nomeCartao,
                    total = total,
                    endereco = endereco,
                    numero = numero,
                    bairro = bairro
                )
                onPaymentCompleted(pagamento)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6EC7))
        ) {
            Text(text = "Continuar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* Handle cancel action */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6EC7))
        ) {
            Text(text = "Cancelar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.logosemfundo),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
