package com.youngerhousea.mirai.compose.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.youngerhousea.mirai.compose.console.viewModel
import com.youngerhousea.mirai.compose.resource.R
import com.youngerhousea.mirai.compose.ui.about.About
import com.youngerhousea.mirai.compose.ui.message.BotMessage
import com.youngerhousea.mirai.compose.ui.message.Message
import com.youngerhousea.mirai.compose.ui.plugins.Plugins
import com.youngerhousea.mirai.compose.ui.setting.Setting
import com.youngerhousea.mirai.compose.viewmodel.HostRoute
import com.youngerhousea.mirai.compose.viewmodel.HostViewModel
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane

@Composable
fun HostPage() {
    NavHost()
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun NavHost(
    hostViewModel: HostViewModel = viewModel { HostViewModel() }
) {
    val state by hostViewModel.hostState

    HorizontalSplitPane {
        first(minSize = MinFirstSize) {
            NavHostFirst(
                modifier = Modifier.background(R.Colors.SplitLeft),
                navigate = state.navigate,
                onRouteMessage = { hostViewModel.dispatch(HostRoute.Message) },
                onRoutePlugins = { hostViewModel.dispatch(HostRoute.Plugins) },
                onRouteSetting = { hostViewModel.dispatch(HostRoute.Setting) },
                onRouteAbout = { hostViewModel.dispatch(HostRoute.About) }
            )
        }
        second {
            NavHostSecond(state.navigate)
        }
    }
}

@Composable
fun NavHostSecond(hostRoute: HostRoute) {
    when (hostRoute) {
        HostRoute.About -> About()
        HostRoute.Message -> Message()
        HostRoute.Plugins -> Plugins()
        HostRoute.Setting -> Setting()
        is HostRoute.BotMessage -> BotMessage()
    }
}

@Composable
fun NavHostFirst(
    modifier: Modifier = Modifier,
    navigate: HostRoute,
    onRouteMessage: () -> Unit,
    onRoutePlugins: () -> Unit,
    onRouteSetting: () -> Unit,
    onRouteAbout: () -> Unit,
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        NavHostFirstBotMenu()
        RailTab(
            onClick = onRouteMessage,
            selected = navigate == HostRoute.Message
        ) {
            Icon(R.Icon.Message, null, tint = R.Colors.SplitFront)

            if (navigate == HostRoute.Message)
                Text(R.String.RailTabFirst, color = R.Colors.SplitFront)
        }
        RailTab(
            onClick = onRoutePlugins,
            selected = navigate == HostRoute.Plugins,
        ) {
            Icon(R.Icon.Plugins, null, tint = R.Colors.SplitFront)
            if (navigate == HostRoute.Plugins)
                Text(R.String.RailTabSecond, color = R.Colors.SplitFront)
        }
        RailTab(
            onClick = onRouteSetting,
            selected = navigate == HostRoute.Setting
        ) {
            Icon(R.Icon.Setting, null, tint = R.Colors.SplitFront)
            if (navigate == HostRoute.Setting)
                Text(R.String.RailTabThird, color = R.Colors.SplitFront)
        }
        RailTab(
            onClick = onRouteAbout,
            selected = navigate == HostRoute.About
        ) {
            Icon(R.Icon.About, null, tint = R.Colors.SplitFront)
            if (navigate == HostRoute.About)
                Text(R.String.RailTabFourth, color = R.Colors.SplitFront)
        }
    }
}

private val MinFirstSize = 170.dp

// 懒的改了
@Preview
@Composable
fun NavHostFirstPreview() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        RailTab(
            onClick = {},
            selected = true
        ) {
            Icon(R.Icon.Message, null, tint = R.Colors.SplitFront)
            Text(R.String.RailTabFirst)
        }
        RailTab(
            onClick = {},
            selected = false,
        ) {
            Icon(R.Icon.Plugins, null, tint = R.Colors.SplitFront)
            Text(R.String.RailTabSecond)
        }
        RailTab(
            onClick = {},
            selected = false
        ) {
            Icon(R.Icon.Setting, null, tint = R.Colors.SplitFront)
            Text(R.String.RailTabThird)
        }
        RailTab(
            onClick = {},
            selected = false
        ) {
            Icon(R.Icon.About, null, tint = R.Colors.SplitFront)
            Text(R.String.RailTabFourth)
        }
    }
}
