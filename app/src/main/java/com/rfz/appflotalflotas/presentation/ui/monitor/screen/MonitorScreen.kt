package com.rfz.appflotalflotas.presentation.ui.monitor.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.rfz.appflotalflotas.R
import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothSignalQuality
import com.rfz.appflotalflotas.data.repository.bluetooth.MonitorDataFrame
import com.rfz.appflotalflotas.data.repository.bluetooth.SensorAlertDataFrame
import com.rfz.appflotalflotas.data.repository.bluetooth.decodeAlertDataFrame
import com.rfz.appflotalflotas.data.repository.bluetooth.decodeDataFrame
import com.rfz.appflotalflotas.presentation.theme.ProyectoFscSoftTheme
import com.rfz.appflotalflotas.presentation.ui.monitor.viewmodel.MonitorViewModel

@Composable
fun MonitorScreen(
    monitorViewModel: MonitorViewModel,
    modifier: Modifier = Modifier
) {
    val monitorUiState = monitorViewModel.monitorUiState.collectAsState()

    val sensorId = monitorUiState.value.sensorId

    MonitorScreenView(
        wheel = monitorUiState.value.wheel,
        id = monitorUiState.value.sensorId,
        battery = monitorUiState.value.battery,
        pression = monitorUiState.value.pression.first,
        pressionStatus = monitorUiState.value.pression.second,
        temperature = monitorUiState.value.temperature.first,
        temperatureStatus = monitorUiState.value.temperature.second,
        qualityBluetooth = monitorUiState.value.signalIntensity.first,
        measuredBluetooth = monitorUiState.value.signalIntensity.second,
        timestamp = monitorViewModel.getCurrentRecordDate(),
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun MonitorScreenView(
    wheel: String,
    id: String,
    battery: String,
    pression: String,
    pressionStatus: String,
    temperature: String,
    temperatureStatus: String,
    qualityBluetooth: BluetoothSignalQuality,
    measuredBluetooth: String,
    timestamp: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color("#E9E9E9".toColorInt())),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Unidad 001", color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Box {
            Image(
                painter = painterResource(R.drawable.chasis_example),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )
        }
        Row {
            PanelSensor(modifier = Modifier.weight(1f))
            PanelLlantas(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PanelSensor(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Llanta P01",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CeldaDatosSensor()
                CeldaDatosSensor()
                CeldaDatosSensor()
            }
        }

        Card(
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Alertas activas",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CeldaAlerta()
                CeldaAlerta()
                CeldaAlerta()
            }
        }
    }
}

@Composable
fun PanelLlantas(modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(Color.White), modifier = modifier.fillMaxSize()) {}
}

@Composable
fun CeldaDatosSensor(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Color("#2E3192".toColorInt()))
    ) {
        Image(Icons.Filled.Sensors, contentDescription = null, modifier = Modifier.weight(1f))
        Column(modifier = Modifier.weight(2f)) {
            Text("Temperatura")
            Text("45 C")
        }
    }
}

@Composable
fun CeldaAlerta(modifier: Modifier = Modifier) {
    Text(
        "P07: Temperatura alta",
        color = Color("#fbcbcb".toColorInt()),
        modifier = Modifier.background(Color.Red)
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MonitorScreenPreview() {
    val data = "aaa1410e630147e85e00124f08f4"
    val sensorId = decodeDataFrame(data, MonitorDataFrame.SENSOR_ID)
    val wheel = decodeDataFrame(data, MonitorDataFrame.POSITION_WHEEL)
    val battery = decodeAlertDataFrame(data, SensorAlertDataFrame.LOW_BATTERY)
    val pressionValue = decodeDataFrame(data, MonitorDataFrame.PRESSION)
    val pressionStatus = decodeAlertDataFrame(data, SensorAlertDataFrame.PRESSURE)
    val temperatureValue = decodeDataFrame(data, MonitorDataFrame.TEMPERATURE)
    val temperatureStatus = decodeAlertDataFrame(data, SensorAlertDataFrame.HIGH_TEMPERATURE)
    val calidadBluetooth = BluetoothSignalQuality.Pobre
    val measuredBluetooth = "-70 dBm"
    ProyectoFscSoftTheme {
        MonitorScreenView(
            wheel,
            sensorId,
            battery,
            pressionValue,
            pressionStatus,
            temperatureValue,
            temperatureStatus,
            calidadBluetooth,
            measuredBluetooth,
            timestamp = "21 de Julio de 2025 | hora: 14:20:00",
            modifier = Modifier.fillMaxSize()
        )
    }
}