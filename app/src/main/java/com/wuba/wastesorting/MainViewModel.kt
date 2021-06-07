package com.wuba.wastesorting


import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wuba.wastesorting.utils.DataUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer


@HiltViewModel
class MainViewModel @Inject constructor(
    @BindFakeCommunicator private val communicator: Communicator,
) : ViewModel() {
    private var timer: Timer
    var handler: Handler

    var locale: Locale by mutableStateOf(Locale.SIMPLIFIED_CHINESE)
        private set

    var rgb by mutableStateOf<IntArray?>(null)
        private set

    var range by mutableStateOf(99999)
        private set

    var userType by mutableStateOf(0)
        private set

    var commandsLog by mutableStateOf("")
        private set

    init {
        Log.w("VM", "onInit")
        communicator.connect()
        handler = Handler(Looper.getMainLooper()) {
            when (it.what) {
                1 -> {
                    communicator.read()?.forEach { msg ->
                        Log.w("Handler", "receive: $msg")
                        DataUtils.matchRgb(msg).apply {
                            Log.e("rgb", first.toString() + second.toString())
                            if (first){
                                rgb = second
                                return@forEach
                            }
                        }
                        DataUtils.matchRange(msg).apply {
                            Log.e("range", first.toString() + second.toString())
                            if (first){
                                range = second
                                return@forEach
                            }
                        }
                        DataUtils.matchUserType(msg).apply {
                            Log.e("usertype", first.toString() + second.toString())
                            if (first){
                                userType = second
                                return@forEach
                            }
                        }
                        commandsLog = "$msg\n$commandsLog"
                    }
                }
            }
            false
        }
        timer = fixedRateTimer(period = 500) {
            handler.sendEmptyMessage(1)
        }
    }

    override fun onCleared() {
        Log.w("VM", "onCleared")
        communicator.close()
        super.onCleared()
        timer.cancel()
    }

    fun sendCommand(command: String) {
        communicator.write(command)
        commandsLog = ">>> $command\n$commandsLog"
    }

    fun clearCommandsLog() {
        commandsLog = ""
    }

    fun switchLocale(){
        locale = if (locale == Locale.SIMPLIFIED_CHINESE){
            Locale.ENGLISH
        } else {
            Locale.SIMPLIFIED_CHINESE
        }
    }

    fun logIn(userType: Int){
        this.userType = userType
    }

    fun logOut() {
        userType = 0
    }
}
