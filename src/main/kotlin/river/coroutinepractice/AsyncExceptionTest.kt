package river.coroutinepractice

import kotlinx.coroutines.*

/**
 * async 예외는 await에서만 터진다
 *
 * 참고: 부모스코프가 취소되지 않게 하기 위해 runBlocking대신 supervisorScope을 씀
 * (부모 스코프가 이미 취소되면 await까지 도달 못 함)
 * */

fun main() = runBlocking {
    supervisorScope {
        val deferred = async {
            delay(200)
            throw IllegalArgumentException("boom")
        }

        delay(400)
        println("still alive")

        try {
            deferred.await()
        } catch (e: Exception) {
            println("caught: ${e.message}")
        }
    }
}