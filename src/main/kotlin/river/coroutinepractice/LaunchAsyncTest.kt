package river.coroutinepractice

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("== launch vs async: return value 체감 ==")

    val elapsed = measureTimeMillis {

        // 1) launch: Job만 반환 (결과값 없음)
        val job: Job = launch {
            delay(300)
            println("[launch] finished (but no return value)")
            // 여기서 return "x" 같은 건 못함
        }

        // 2) async: Deferred<Int> 반환 (결과값 있음)
        val deferred: Deferred<Int> = async {
            println("[async] start computing...")
            delay(700)
            val value = 40 + 2
            println("[async] computed value = $value (still inside coroutine)")
            value // ✅ 이게 async의 "결과"
        }

        val secondDeferred: Deferred<String> = async {
            println("[async] start computing...")
            delay(700)
            val value = "meow"
            println("[async] computed value = $value (still inside coroutine)")
            value // ✅ 이게 async의 "결과"
        }

        println("[main] right after launch/async: job=$job, deferred=$deferred")
        println("[main] 지금은 결과가 아직 없어서 deferred.get() 같은 건 없음. await()만 가능")

        // launch는 join으로 "끝났는지"만 기다림
        job.join()
        println("[main] launch joined ✅ (끝난 것만 확인)")

        // async는 await로 "끝날 때까지 기다리고 + 결과를 받음"
        println("[main] 이제 async 결과를 await()로 꺼내기")
        val result = deferred.await() // ✅ 여기서 기다렸다가 결과를 받음
        println("[main] async result = $result ✅ (결과를 실제로 받았다)")

        val secondResult = secondDeferred.await() // ✅ 여기서 기다렸다가 결과를 받음
        println("[main] second async result = $secondResult ✅ (두번째 결과를 실제로 받았다)")
    }

    println("== total elapsed: ${elapsed}ms ==")
}