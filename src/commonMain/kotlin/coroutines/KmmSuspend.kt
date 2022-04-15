package coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class KmmSuspend {

    private val intChannel = Channel<Int>()
    val intReceiveChannel: ReceiveChannel<Int> = intChannel

    val infiniteIntTickerFlow = flow<Int> {
        var i = 0
        while (true) {
            i += 1
            emit(i)
            delay(1000L)
        }
    }

    suspend fun suspendEpochMilliseconds(): Long {
        delay(2_000L)
        return Clock.System.now().toEpochMilliseconds()
    }

    suspend fun suspendEpochMillisOnIO(): Long = withContext(Dispatchers.Default) {
        suspendEpochMilliseconds()
    }

    suspend fun sendToIntChannel(value: Int) = intChannel.send(value)
}