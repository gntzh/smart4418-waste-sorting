package com.wuba.wastesorting.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wuba.wastesorting.MainViewModel
import com.wuba.wastesorting.R
import com.wuba.wastesorting.components.localeStringResource


@Composable
fun Login(
    vm: MainViewModel = viewModel(),
    onLogIn: (userType: Int) -> Unit = {},
) {
    val userType = vm.userType
    SideEffect {
        if (userType > 0) {
            onLogIn(userType)
        }
    }
    Row(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(.35f)
                .background(Color(0xff3b3b4d)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                localeStringResource(R.string.log_in),
                style = MaterialTheme.typography.h3,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                Image(
                    painter = painterResource(R.drawable.login_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable(onClick = { vm.logIn(2) }) // FIXME Remove
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                localeStringResource(R.string.welcome),
                style = MaterialTheme.typography.h5,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color(0xff2d2d3b)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                localeStringResource(R.string.login_required_prompt),
                style = MaterialTheme.typography.h3,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(36.dp))
            Image(
                painter = painterResource(R.drawable.qr_code),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .clickable(onClick = { vm.logIn(1) }) // FIXME Remove
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                localeStringResource(R.string.login_prompt),
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
        }
    }
}

//
//@Preview(widthDp = 1280, heightDp = 800)
//@Composable
//fun LoginPreview(){
//    Login()
//}