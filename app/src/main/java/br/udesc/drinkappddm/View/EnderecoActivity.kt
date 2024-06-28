package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.ViewModel.EnderecoViewModel
import br.udesc.drinkappddm.ui.theme.*

class EnderecoActivity : ComponentActivity() {

    private lateinit var viewModel: EnderecoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EnderecoViewModel::class.java)

        // Receber o valor total passado como parâmetro
        val total = intent.getDoubleExtra("total", 0.0)

        setContent {
            EnderecoScreen(total) { endereco, numero, bairro ->
                telaPagamento(endereco, numero, bairro, total)
            }
        }
    }

    private fun telaPagamento(endereco: String, numero: String, bairro: String, total: Double) {
        val intent = Intent(this@EnderecoActivity, PagamentoActivity::class.java).apply {
            putExtra("endereco", endereco)
            putExtra("numero", numero)
            putExtra("bairro", bairro)
            putExtra("total", total)
        }
        startActivity(intent)
    }
}

@Composable
fun EnderecoScreen(total: Double, onContinuarClicked: (String, String, String) -> Unit) {
    var endereco by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ENDEREÇO DE ENTREGA",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Rua") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = numero,
                onValueChange = { numero = it },
                label = { Text("Número") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bairro,
                onValueChange = { bairro = it },
                label = { Text("Bairro") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onContinuarClicked(endereco, numero, bairro) },
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
}
