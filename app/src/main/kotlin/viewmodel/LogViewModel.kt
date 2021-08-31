package com.youngerhousea.mirai.compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.youngerhousea.mirai.compose.console.ViewModelScope
import com.youngerhousea.mirai.compose.ui.log.Log
import com.youngerhousea.mirai.compose.ui.log.LogColor
import kotlinx.coroutines.flow.StateFlow

interface ConsoleLog {

    fun setSearchContent(content: String)

    fun setCurrentCommand(content: String)

    fun onSearchClick()

    val log: StateFlow<List<Log>>

    val searchContent: StateFlow<String>

    val command: StateFlow<String>

    val logColor: StateFlow<LogColor>
}

class LogViewModel : ViewModelScope() {
    var isShowSearch = mutableStateOf(true)

    val searchContent by consoleLog.searchContent.collectAsState()

    val command by consoleLog.command.collectAsState()

    val log by consoleLog.log.collectAsState()

    val logColor by consoleLog.logColor.collectAsState()
}