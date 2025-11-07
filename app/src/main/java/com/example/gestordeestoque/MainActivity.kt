package com.example.gestordeestoque

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestordeestoque.presentation.screen.home.mainpage
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestordeestoque.presentation.screen.cadastro.cadastro
import com.example.gestordeestoque.presentation.screen.categoria.CategoriaPage
import com.example.gestordeestoque.presentation.screen.categoria.CategoriaViewModel
import com.example.gestordeestoque.presentation.screen.categoria.DetalhesCategoriaPage
import com.example.gestordeestoque.presentation.screen.produto.DetalhesProdutoPage
import com.example.gestordeestoque.presentation.screen.produto.ProdutoPage
import com.example.gestordeestoque.presentation.screen.produto.ProdutoViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val produtoVM = ProdutoViewModel(context=context)
            val categoriaVM = CategoriaViewModel(context = context)
            Navegador(produtoVM = produtoVM,categoriaVM)
        }
    }
}

@Composable
fun Navegador(produtoVM: ProdutoViewModel,categoriaVM: CategoriaViewModel){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "principal" ) {

        // vamos criar as rotas de navegação, dando um nome único para cada
        // layout que desejamos navegar
        composable("principal") {
            // definimos agora qual é o layout que recebeu o nome acima
            mainpage().HomePage(
                onIrParaCadastro = { navController.navigate("cadastro") },
                onIrParaProduto = { navController.navigate("produto") },
                onIrParaCategoria = { navController.navigate("categoria") }
            )
        }

       composable("cadastro") {
            cadastro().Cadastro(
               onVoltarParaPrincipal = { navController.popBackStack()}
           )
        }

        composable("produto") {
            ProdutoPage().ProdutoPageMain(
                onVoltarParaPrincipal = { navController.popBackStack() },
                navController = navController,
                produtoVM = produtoVM
            )
        }
        composable("categoria") {
            CategoriaPage().CategoriaPageMain(
                onVoltarParaPrincipal = { navController.popBackStack()},
                navController = navController,
                categoriaVM = categoriaVM

            )
        }
        composable(
            route = "detalhescategoria/{categoriaId}",
            arguments = listOf(navArgument("categoriaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoriaId = backStackEntry.arguments?.getString("categoriaId")!!
            DetalhesCategoriaPage(
            ).Detalhes(
                categoriaId = categoriaId,
                onVoltarParaProduto = { navController.popBackStack() },
                categoriaVM = categoriaVM
            )
        }

        composable(
            route = "detalhesproduto/{produtoId}",
            arguments = listOf(navArgument("produtoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val produtoId = backStackEntry.arguments?.getString("produtoId")!!
            DetalhesProdutoPage(
            ).Detalhes(
                produtoId = produtoId,
                onVoltarParaProduto = { navController.popBackStack() },
                produtoVM = produtoVM
            )
        }
    }

}