package com.rfz.appflotalflotas.presentation.ui.languaje

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.rfz.appflotalflotas.core.util.AppLocale

@Composable
fun LocalizedApp(content: @Composable () -> Unit) {
    val localeState = AppLocale.currentLocale.collectAsState()
    val context = LocalContext.current

    val localizedContext = remember(localeState.value) {
        val config = Configuration(context.resources.configuration)
        config.setLocale(localeState.value)
        context.createConfigurationContext(config)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        content = content
    )
}