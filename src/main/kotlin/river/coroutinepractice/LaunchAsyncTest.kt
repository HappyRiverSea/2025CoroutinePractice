package river.coroutinepractice

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Start")

    val job = launch {
        delay(1000)
        println("launch finished")
    }

    val deferred = async {
        delay(1000)
        "async result"
    }

    job.join()
    println(deferred.await())
}