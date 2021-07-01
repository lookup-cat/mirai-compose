package com.youngerhousea.miraicompose.app.ui.log

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import com.youngerhousea.miraicompose.app.ui.shared.CommandSendBox
import com.youngerhousea.miraicompose.app.ui.shared.LogBox
import com.youngerhousea.miraicompose.core.component.log.ConsoleLog

@Composable
fun ConsoleLogUi(consoleLog: ConsoleLog) {
    var isShowSearch by remember { mutableStateOf(true) }

    val model by consoleLog.model.collectAsState()

    Scaffold(
        modifier = Modifier.onPreviewKeyEvent {
            if (it.isCtrlPressed && it.key == Key.F) {
                isShowSearch = !isShowSearch
                return@onPreviewKeyEvent true
            }
            false
        },
        topBar = {
            if (isShowSearch)
                TextField(
                    model.searchContent,
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
        Column {
            LogBox(
                Modifier
                    .fillMaxSize()
                    .weight(8f)
                    .padding(horizontal = 40.dp, vertical = 20.dp),
                model.log,
                model.searchContent
            )
            CommandSendBox(
                consoleLog.logger,
                Modifier
                    .weight(1f)
                    .padding(horizontal = 40.dp),
            )
        }
    }
}
