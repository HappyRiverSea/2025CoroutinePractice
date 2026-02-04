package river.coroutinepractice

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
* 부모가 자식을 끝까지 ‘기다린다’ (작업 누락 방지)
* 자식 중 하나가 실패하면 ‘같이’ 실패한다 (에러 전파/일관성)
* 취소가 ‘하위로 전파’된다 (리소스/작업 정리)
*
*
* parent: after scope는 무조건 마지막에 찍혀야 함
* */

fun main() = runBlocking {
    println("parent: start")

    coroutineScope {
        launch {
            delay(300)
            println("child-1: done")
        }
        launch {
            delay(600)
            println("child-2: done")
        }
        println("parent: inside scope end (children still running)")
    }

    println("parent: after scope (children must be done)")
}