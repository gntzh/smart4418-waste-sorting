package com.wuba.wastesorting.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wuba.wastesorting.MainViewModel
import com.wuba.wastesorting.utils.DataUtils
import com.wuba.wastesorting.utils.ThemedPreview

@Composable
fun Maintenance(vm: MainViewModel = viewModel()) {
    val rgb = vm.rgb
    var rgbColor = Color.Gray
    var rgbStr = "RGB异常"
    if (rgb != null) {
        rgbColor = DataUtils.rgb2Color(rgb)
        rgbStr = "${rgb[0]}, ${rgb[1]}, ${rgb[2]}"
    }
    var command by rememberSaveable { mutableStateOf("test") }
    var scrollState = rememberScrollState()
    val onSendCommand = {
        if (command.isNotEmpty()){
            if (command == "clear") {
                vm.clearCommandsLog()
            } else {
                vm.sendCommand(command)
            }
            command = ""
        }
    }
    Row(
        Modifier
            .fillMaxSize()
            .padding(36.dp)
    ) {
        Column(Modifier.padding(end = 50.dp).fillMaxWidth(.5f)) {
            Row(verticalAlignment = Alignment.Bottom) {
                OutlinedTextField(
                    value = command,
                    onValueChange = { command = it },
                    modifier = Modifier.height(80.dp).weight(weight = 1f,fill = true),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = { onSendCommand() }
                    )
                )
                Spacer(Modifier.width(18.dp))
                Button(
                    onClick = onSendCommand,
                    modifier = Modifier.size(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff00bea6),
                        contentColor = Color.White
                    ),
                    enabled = command.isNotEmpty(),
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.Send, null, modifier = Modifier.size(36.dp))
                }
            }
            Surface(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .verticalScroll(scrollState),
                border = BorderStroke(4.dp, Color.LightGray)
            ) {
                Text(
                    text = vm.commandsLog,
                    modifier = Modifier
                        .padding(12.dp),
                    fontFamily = FontFamily.Monospace
                )
            }
        }
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Card(
                    Modifier.size(270.dp, 210.dp),
                    backgroundColor = rgbColor,
                    elevation = 16.dp,
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = rgbStr, color = Color.White, fontSize = 42.sp)
                        Text(text = "颜色传感器 ", color = Color.White, fontSize = 32.sp)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Card(
                    Modifier.size(270.dp, 210.dp),
                    backgroundColor = Color(0xff409eff),
                    elevation = 16.dp,
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "${vm.range}mm", color = Color.White, fontSize = 42.sp)
                        Text(text = "距离传感器", color = Color.White, fontSize = 32.sp)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Card(
                    Modifier.size((270 * 2 + 12).dp, 210.dp),
                    backgroundColor = Color(0xff13ce66),
                    elevation = 16.dp,
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "良好", color = Color.White, fontSize = 46.sp)
                        Text(text = "机器运行状况 ", color = Color.White, fontSize = 32.sp)
                    }
                }
            }
        }
    }
}


@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun MaintenancePreview() {
    ThemedPreview {
        Maintenance()
    }
}
