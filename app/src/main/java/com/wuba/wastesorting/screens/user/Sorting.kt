package com.wuba.wastesorting.screens.user

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wuba.wastesorting.MainViewModel
import com.wuba.wastesorting.R
import com.wuba.wastesorting.components.*
import com.wuba.wastesorting.utils.ThemedPreview


@Composable
fun Sorting(vm: MainViewModel = viewModel()) {
    var complete by rememberSaveable { mutableStateOf(1) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 48.dp, bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Stepper(complete = complete, space = 300.dp, labelSize = 48.dp) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // TODO: move the width and offsets to parent Stepper component
                Row(Modifier.offset(x = 126.dp), horizontalArrangement = Arrangement.Center) {
                    Step(
                        1,
                        title = localeStringResource(R.string.step1_title),
                        description = localeStringResource(R.string.step1_desc)
                    )
                    Step(
                        2,
                        title = localeStringResource(R.string.step2_title),
                        description = localeStringResource(R.string.step2_desc)
                    )
                    Step(
                        3,
                        title = localeStringResource(R.string.step3_title),
                        description = localeStringResource(R.string.step3_desc),
                        last = true
                    )
                }
                Spacer(Modifier.height(36.dp))
                StepContent(1) {
                    Column {
                        Row {
                            Text(
                                localeStringResource(R.string.current_range_prompt),
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier.alignByBaseline()
                            )
                            Text(
                                "8.34cm",
                                style = MaterialTheme.typography.h2,
                                color = Color.Gray,
                                modifier = Modifier.alignByBaseline()
                            )
                        }
                        Text(
                            buildAnnotatedString {
                                append(localeStringResource(R.string.range_prompt1))
                                withStyle(SpanStyle(fontSize = 48.sp, color = Color.Gray)) {
                                    append("10cm") // FIXME 无法使用另一个composable
                                }
                                append(localeStringResource(R.string.range_prompt2))
                            },
                            fontSize = 24.sp,
                            modifier = Modifier.padding(top = 36.dp)
                        )
                    }
                }
                StepContent(2) {
                    val currentRgb = vm.rgb
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("检测到有效颜色：", fontSize = 32.sp)
                        Surface(
                            modifier = Modifier
                                .size(150.dp)
                                .padding(top = 16.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xfff74c31)
                        ) {}
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = { complete++
                                      Log.e("step", "$complete click")},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00bea6)),
                            modifier = Modifier.size(128.dp, 48.dp)
                        ) {
                            Text(
                                localeStringResource(R.string.confirm),
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                StepContent(3) {
                    Log.w("埋点", "第三步执行初始化")
//                    var mediaPlayer = MediaPlayer().apply {
//                        val fd = LocalContext.current.resources.openRawResourceFd(R.raw.test)
//                        setAudioAttributes()
//                        setDataSource()
//                    }
                    val context = LocalLocaleContext.current
//                    var mediaPlayer = remember(context) {
//                        Log.e("性能埋点", "改变mediaPlayer")
//                        val mp = MediaPlayer.create(context, R.raw.test)
//                        Log.e("性能埋点", "改变mediaPlayer玩车完成")
//                        return@remember mp
//                    }
                    var mediaPlayer: MediaPlayer? = null
//                    Log.e("性能埋点", "mediaPlayer创建")
//                    var mediaPlayer = MediaPlayer.create(context, R.raw.test)
//                    Log.e("性能埋点", "mediaPlayer创建完成")
                    DisposableEffect(context) {
                        Log.w("性能埋点", "mediaPlayer开始创建")
                        mediaPlayer = MediaPlayer.create(context, R.raw.test)
                        Log.w("性能埋点", "mediaPlayer创建完成")
                        mediaPlayer?.start()
                        Log.w("性能埋点", "mediaPlayer开始播放")
                        onDispose {
                            mediaPlayer?.release()
                            mediaPlayer = null
                            Log.w("性能埋点", "清理mediaPlayer")
                        }
                    }
                    Column {
                        Text("请根据提示投放垃圾", modifier = Modifier.padding(bottom = 16.dp))
                        Button(onClick = { mediaPlayer?.start() }) {
                            Text("播放")
                        }
                    }
                }
            }
        }
        // TODO remove
        Row {
            Button(onClick = { complete++ }) {
                Text("Next")
            }
            Button(onClick = { complete = 0 }) {
                Text("Reset")
            }
        }
        Column(
            Modifier
                .fillMaxHeight()
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                DullProgressBar(width = 500.dp, percentage = 75, color = Color(0xff13ce66)) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${localeStringResource(R.string.kitchen_waste)}: 75%",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.width(42.dp))
                DullProgressBar(width = 500.dp, percentage = 73, color = Color(0xff409eff)) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${localeStringResource(R.string.recyclable_waste)}: 73%",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                DullProgressBar(width = 500.dp, percentage = 43, color = Color(0xfff56c6c)) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${localeStringResource(R.string.harmful_waste)}: 43%",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                    }
                }
//                Spacer(modifier = Modifier.width(42.dp))
//                DullProgressBar(width = 500.dp, percentage = 100, color = Color(0xff666666)) {
//                    Row(
//                        Modifier
//                            .fillMaxHeight()
//                            .padding(start = 12.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text("其他垃圾：100%", color = Color.White, fontSize = 30.sp)
//                    }
//                }
            }
        }
    }
}

@Composable
fun DullProgressBar(
    width: Dp,
    height: Dp = 56.dp,
    percentage: Int = 0,
    color: Color = Color(0xff13ce66),
    content: @Composable () -> Unit = {} // TODO it function
) {
    Box(
        Modifier
            .height(height)
            .width(width)
            .background(Color(0xffe5e9f2), shape = RoundedCornerShape(20.dp)),
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .width(width * percentage / 100)
                .background(color, shape = RoundedCornerShape(24.dp)),
        ) {}
        content()
    }
}

@Preview
@Composable
fun DullProgressBarPreview() {
    ThemedPreview {
        DullProgressBar(width = 400.dp, percentage = 43)
    }
}


//@Preview(widthDp = 1280, heightDp = 800)
//@Composable
//fun SortingPreview() {
//    Sorting()
//}
