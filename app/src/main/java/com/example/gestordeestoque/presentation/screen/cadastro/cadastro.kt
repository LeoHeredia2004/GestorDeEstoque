package com.example.gestordeestoque.presentation.screen.cadastro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class cadastro {

    @Composable
    fun Cadastro(onVoltarParaPrincipal: () -> Unit){
        Column (
            Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Sobre O App",
                fontSize = 30.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Tela de cadastro",
                fontSize = 25.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Aula: 11",
                fontSize = 25.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Tema: Navegação entre layouts utilizando Navgation Compose",
                fontSize = 25.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Autor: Jason Sobreiro",
                fontSize = 25.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Data: 01/09/2025",
                fontSize = 25.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = onVoltarParaPrincipal) {
                Text(text = "Voltar")
            }

        }


    }
}