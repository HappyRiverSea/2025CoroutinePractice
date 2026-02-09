package river.coroutinepractice

import kotlinx.coroutines.*

/**
 * launch에서 터지면 스코프 전체가 실패 → 형제 코루틴 취소 → 부모에서 catch 됨.
 * == 1) launch exception propagates ==
 * [launch] throw!
 * [sibling] cancelled because sibling failed
 * [parent] caught: IllegalStateException - boom from launch
 * */

fun main(): Unit = runBlocking {
    println("== 1) launch exception propagates ==")

    try {
        coroutineScope {
            launch {
                delay(200)
                println("[launch] throw!")
                throw IllegalStateException("boom from launch")
            }
            launch {
                try {
                    delay(1000)
                    println("[sibling] done")
                } finally {
                    println("[sibling] cancelled because sibling failed")
                }
            }
        }
    } catch (e: Throwable) {
        println("[parent] caught: ${e::class.simpleName} - ${e.message}")
    }
}