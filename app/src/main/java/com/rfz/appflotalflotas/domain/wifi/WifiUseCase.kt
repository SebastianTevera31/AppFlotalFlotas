package com.rfz.appflotalflotas.domain.wifi

import com.rfz.appflotalflotas.data.repository.wifi.NetworkStatus
import com.rfz.appflotalflotas.data.repository.wifi.WifiRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class WifiUseCase @Inject constructor(private val wifiRepository: WifiRepository) {
    operator fun invoke(): StateFlow<NetworkStatus> {
        return wifiRepository.wifiConnectionState
    }

    fun doConnect() = wifiRepository.connect()
}