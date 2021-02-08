package com.youngerhousea.miraicompose

import com.youngerhousea.miraicompose.console.BufferedOutputStream
import com.youngerhousea.miraicompose.console.MiraiCompose
import net.mamoe.mirai.console.MiraiConsoleImplementation.Companion.start
import net.mamoe.mirai.utils.MiraiLogger
import java.io.PrintStream

fun main() {
    MiraiCompose.start()
    setSystemOut()
    MiraiComposeView()
}

internal fun setSystemOut() {
    System.setOut(
        PrintStream(
            BufferedOutputStream(
                logger = MiraiLogger.create("stdout").run { ({ line: String? -> info(line) }) }
            ),
            false,
            "UTF-8"
        )
    )
    System.setErr(
        PrintStream(
            BufferedOutputStream(
                logger = MiraiLogger.create("stderr").run { ({ line: String? -> warning(line) }) }
            ),
            false,
            "UTF-8"
        )
    )
}

