package com.youngerhousea.mirai.compose.ui.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.rememberDialogState
import com.youngerhousea.mirai.compose.MiraiComposeDialog
import com.youngerhousea.mirai.compose.console.viewModel
import com.youngerhousea.mirai.compose.resource.R
import com.youngerhousea.mirai.compose.viewmodel.LoginAction
import com.youngerhousea.mirai.compose.viewmodel.LoginViewModel


@Composable
fun LoginDialog(
    show: Boolean,
    onCloseRequest: () -> Unit
) {
    if (show)
        MiraiComposeDialog(
            onCloseRequest = onCloseRequest,
            resizable = false,
            state = rememberDialogState(size = WindowSize(400.dp, 400.dp))
        ) {
            Login()
        }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(loginViewModel: LoginViewModel = viewModel { LoginViewModel() }) {

    val data by loginViewModel.state

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = data.host),
        backgroundColor = Color(0xFFb6e5fa), // 比较浅的主题色
        modifier = Modifier.onKeyEvent {
            if (it.key == Key.Enter) {
                // TODO 此处应该有提升(账号或密码为空)
                if (loginViewModel.state.value.account.isNotEmpty() && loginViewModel.state.value.password.isNotEmpty())
                    loginViewModel.dispatch(LoginAction.Login)
                return@onKeyEvent true
            }
            false
        }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                R.Image.Mirai,
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
            )
            AccountTextField(
                account = data.account,
                onAccountTextChange = {
                    loginViewModel.dispatch(LoginAction.InputAccount(it))
                },
            )
            PasswordTextField(
                password = data.password,
                onPasswordTextChange = {
                    loginViewModel.dispatch(LoginAction.InputPassword(it))
                },
            )
            LoginButton(
                modifier = Modifier.padding(vertical = 50.dp),
                onClick = {
                    // TODO 此处应该有提升(账号或密码为空)
                    if (loginViewModel.state.value.account.isNotEmpty() && loginViewModel.state.value.password.isNotEmpty())
                        loginViewModel.dispatch(LoginAction.Login)
                },
                isLoading = data.isLoading
            )
        }
    }

}


@Composable
private fun AccountTextField(
    account: String,
    onAccountTextChange: (String) -> Unit,
) {
    var isError by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = account,
        onValueChange = {
            isError = !it.matches("^[0-9]{0,15}$".toRegex())
            onAccountTextChange(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 40.dp, vertical = 5.dp),
        label = { Text(R.String.Login) },
        leadingIcon = { Icon(Icons.Default.AccountCircle, null) },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )
}

@Composable
private fun PasswordTextField(
    password: String,
    onPasswordTextChange: (String) -> Unit,
) {
    var passwordVisualTransformation: VisualTransformation by remember { mutableStateOf(PasswordVisualTransformation()) }

    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordTextChange(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 40.dp, vertical = 5.dp),
        label = { Text(R.String.Password) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.VpnKey,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisualTransformation =
                    if (passwordVisualTransformation != VisualTransformation.None)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
            }) {
                Icon(
                    imageVector = Icons.Default.RemoveRedEye,
                    contentDescription = null
                )
            }
        },
        visualTransformation = passwordVisualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
private fun LoginButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) = Button(
    onClick = onClick,
    modifier = Modifier
        .requiredHeight(100.dp)
        .aspectRatio(2f)
        .padding(30.dp),
) {
    if (isLoading)
        HorizontalDottedProgressBar()
    else
        Text("Login")
}

@Composable
private fun HorizontalDottedProgressBar() {
    val color = MaterialTheme.colors.onPrimary
    val transition = rememberInfiniteTransition()
    val state by transition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
    ) {

        val radius = (4.dp).value
        val padding = (6.dp).value

        for (i in 1..5) {
            if (i - 1 == state.toInt()) {
                drawCircle(
                    radius = radius * 2,
                    brush = SolidColor(value = color),
                    center = Offset(
                        x = center.x + radius * 2 * (i - 3) + padding * (i - 3),
                        y = center.y
                    )
                )
            } else {
                drawCircle(
                    radius = radius,
                    brush = SolidColor(value = color),
                    center = Offset(
                        x = center.x + radius * 2 * (i - 3) + padding * (i - 3),
                        y = center.y
                    )
                )
            }
        }
    }
}