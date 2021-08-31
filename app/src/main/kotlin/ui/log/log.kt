package com.youngerhousea.mirai.compose.ui.log

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import com.youngerhousea.mirai.compose.console.viewModel
import com.youngerhousea.mirai.compose.viewmodel.LogViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Log(logViewModel: LogViewModel = viewModel { LogViewModel() }) {
    Scaffold(
        modifier = Modifier.onPreviewKeyEvent {
            if (it.isCtrlPressed && it.key == Key.F) {
                logViewModel.isShowSearch = !logViewModel.isShowSearch
                return@onPreviewKeyEvent true
            }
            false
        },
        topBar = {
            if (logViewModel.isShowSearch)
                TextField(
                    logViewModel.searchContent,
                    consoleLog::setSearchContent,
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                        .animateContentSize(),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
        }, floatingActionButton = {
//        FloatingActionButton(onClick = {
//
//        }) {
//        }
        }) {
        Column(Modifier.padding(it)) {
            LogBox(
                Modifier
                    .fillMaxSize()
                    .weight(8f)
                    .padding(horizontal = 40.dp, vertical = 20.dp),
                log,
                searchContent,
                logColor
            )
            CommandSendBox(
                command = command,
                onCommandChange = consoleLog::setCurrentCommand,
                onClick = consoleLog::onSearchClick,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 40.dp),
            )
        }
    }
}
