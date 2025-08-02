package com.rfz.appflotalflotas.presentation.ui.inicio.ui

import android.Manifest
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rfz.appflotalflotas.core.network.NetworkConfig
import com.rfz.appflotalflotas.core.util.FlotalFlotasScreens

import com.rfz.appflotalflotas.presentation.theme.ProyectoFscSoftTheme

import com.rfz.appflotalflotas.presentation.ui.home.screen.HomeScreen
import com.rfz.appflotalflotas.presentation.ui.home.viewmodel.HomeViewModel
import com.rfz.appflotalflotas.presentation.ui.inicio.screen.InicioScreen
import com.rfz.appflotalflotas.presentation.ui.inicio.viewmodel.InicioScreenViewModel
import com.rfz.appflotalflotas.presentation.ui.languaje.LocalizedApp
import com.rfz.appflotalflotas.presentation.ui.loading.screen.LoadingScreen
import com.rfz.appflotalflotas.presentation.ui.login.screen.LoginScreen
import com.rfz.appflotalflotas.presentation.ui.login.viewmodel.LoginViewModel
import com.rfz.appflotalflotas.presentation.ui.monitor.screen.MonitorScreen
import com.rfz.appflotalflotas.presentation.ui.monitor.viewmodel.MonitorViewModel

import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@AndroidEntryPoint
class InicioActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val inicioScreenViewModel: InicioScreenViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val monitorViewModel: MonitorViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        setContent {
            val context = LocalContext.current

            val permisoBluetoothConnect = Manifest.permission.BLUETOOTH_CONNECT

            val bluetoothPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    // Si quieres, puedes inicializar tu servicio Bluetooth desde aquí
                    monitorViewModel.initService(context)
                } else {
                    Log.d("Permiso", "Permiso BLUETOOTH_CONNECT denegado")
                }
            }

            ProyectoFscSoftTheme {
                LocalizedApp {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()


                        NetworkConfig.imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                        } else {
                            val tel =
                                getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                            tel.imei
                        }

                        val hasInitialValidation by inicioScreenViewModel.initialValidationCompleted.observeAsState(
                            false
                        )
                        val userData by inicioScreenViewModel.userData.observeAsState()


                        LaunchedEffect(hasInitialValidation, userData) {
                            if (hasInitialValidation) {
                                userData?.let { data ->
                                    val fechaRegistro = data.fecha
                                    if (fechaRegistro.isNotEmpty()) {
                                        val formatter =
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                        val fechaUsuario =
                                            LocalDateTime.parse(fechaRegistro, formatter)
                                        val fechaActual = LocalDateTime.now()

                                        val diferenciaHoras =
                                            ChronoUnit.HOURS.between(fechaUsuario, fechaActual)

                                        if (diferenciaHoras < 24) {
                                            navController.navigate(NetworkConfig.HOME) {
                                                popUpTo(NetworkConfig.LOADING) { inclusive = true }
                                            }
                                        } else {
                                            inicioScreenViewModel.deleteUserData()
                                            navController.navigate(NetworkConfig.LOGIN) {
                                                popUpTo(NetworkConfig.LOADING) { inclusive = true }
                                            }
                                        }
                                    }
                                } ?: run {
                                    navController.navigate(NetworkConfig.LOGIN) {
                                        popUpTo(NetworkConfig.LOADING) { inclusive = true }
                                    }
                                }
                            }
                        }


                        loginViewModel.navigateToHome.observe(this) { shouldNavigate ->
                            if (shouldNavigate) {
                                navController.navigate(NetworkConfig.HOME) {
                                    popUpTo(NetworkConfig.LOGIN) { inclusive = true }
                                }
                                loginViewModel.onNavigateToHomeComplete()
                            }
                        }

                        NavHost(
                            navController = navController,
                            startDestination = NetworkConfig.LOADING
                        ) {
                            composable(NetworkConfig.LOADING) { LoadingScreen() }
                            composable(NetworkConfig.LOGIN) {
                                LoginScreen(
                                    loginViewModel,
                                    navController
                                )
                            }

                            composable(NetworkConfig.INICIO) {
                                InicioScreen(navController)
                            }

                            composable(NetworkConfig.HOME) {
                                HomeScreen(
                                    navController = navController,
                                    homeViewModel = homeViewModel,
                                    colors = MaterialTheme.colorScheme
                                )
                            }

                            composable(FlotalFlotasScreens.DISENIO_ORIGINAL.name) {}

                            composable(FlotalFlotasScreens.DIMENSIONES.name) {}

                            composable(FlotalFlotasScreens.PRODUCTOS.name) {}

                            composable(FlotalFlotasScreens.LLANTAS.name) {}

                            composable(FlotalFlotasScreens.VEHICULOS.name) {}

                            composable(FlotalFlotasScreens.MONTAJE.name) {}

                            composable(FlotalFlotasScreens.MONITOR.name) {

                                LaunchedEffect(Unit) {
                                    val granted = ContextCompat.checkSelfPermission(
                                        context,
                                        permisoBluetoothConnect
                                    ) == PackageManager.PERMISSION_GRANTED

                                    if (!granted) {
                                        bluetoothPermissionLauncher.launch(permisoBluetoothConnect)
                                    } else {
                                        monitorViewModel.initService(context)
                                    }
                                }

                                MonitorScreen(
                                    monitorViewModel = monitorViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}