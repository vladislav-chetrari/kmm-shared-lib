package coroutines

import kotlinx.coroutines.*

class KmmScoped : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun launchCoroutine() {
        println("launchCoroutine before")
        launch {
            delay(1000L)
            println("launchCoroutine Job finish")
        }
    }

    fun launchAsyncCoroutines() {
        launch {
            (0 until 100).map { int ->
                async {
                    delay(int * 100L)
                    println("async[$int] finished")
                }
            }.awaitAll()
            println("all finished")
        }
    }
}