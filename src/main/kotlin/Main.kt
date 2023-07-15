import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.compose.app.ui.WinMain
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
fun main() = application {

    var windowsOpen by remember {
        mutableStateOf(true)
    }
    var isClose by remember {
        mutableStateOf(false)
    }

    if (windowsOpen) {
        Window(
            icon = painterResource("icons/logo.png"),
            onCloseRequest = { isClose = true }, title = "简易文件浏览器", state = WindowState(
                position = WindowPosition.Aligned(
                    Alignment.Center
                )
            )
        ) {
            WinMain()
            if (isClose) {
                Dialog(
                    onCloseRequest = { isClose = false },
                    resizable = false,
                    undecorated = true,
                    transparent = true,
                ) {
                    Card(
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier.padding(12.dp).fillMaxSize(),
                        elevation = CardDefaults.elevatedCardElevation()
                    ) {
                        Column(
                            modifier = Modifier.padding(28.dp).fillMaxSize()
                        ) {
                            Text("确定要退出程序吗？", style = MaterialTheme.typography.bodyLarge)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                TextButton(onClick = { isClose = false }) {
                                    Text("取消")
                                }
                                Spacer(Modifier.fillMaxSize().weight(1f))
                                Button(onClick = { windowsOpen = false }) {
                                    Text("确认")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CloseAppDialog(close: MutableState<Boolean>, windowState: MutableState<Boolean>) {
    if (close.value) {
        AlertDialog(
            onDismissRequest = { close.value = false },
            text = {
                Text("确定要退出吗？")
            },
            buttons = {
                Row {
                    Button(
                        onClick = {
                            close.value = false
                        }
                    ) {
                        Text("取消")
                    }
                    Spacer(Modifier.size(16.dp))
                    Button(
                        onClick = {
                            windowState.value = false
                        }
                    ) {
                        Text("确定")
                    }
                }
            }
        )
    }
}
