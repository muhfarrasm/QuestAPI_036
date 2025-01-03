package com.example.questapi_per12.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.questapi_per12.ui.view.DestinasiDetail
import com.example.questapi_per12.ui.view.DestinasiEntry
import com.example.questapi_per12.ui.view.DestinasiHome
import com.example.questapi_per12.ui.view.DestinasiUpdate
import com.example.questapi_per12.ui.view.DetailViewScreen
import com.example.questapi_per12.ui.view.EntryMhsScreen
import com.example.questapi_per12.ui.view.HomeScreen
import com.example.questapi_per12.ui.view.UpdateMhsScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        // home screen
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToltemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }

        // input data
        composable(DestinasiEntry.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }

        // detail data
        composable(DestinasiDetail.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.NIM) {
            type = NavType.StringType }
        )
        ){
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let { nim ->
                DetailViewScreen(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$nim") },
                    navigateBack = { navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                    }
                )
            }
        }

        // update data
        composable(DestinasiUpdate.routesWithArg, arguments = listOf(navArgument(DestinasiDetail.NIM){
            type = NavType.StringType
        }
        )
        ){
            val nim = it.arguments?.getString(DestinasiUpdate.NIM)
            nim?.let { nim ->
                UpdateMhsScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}