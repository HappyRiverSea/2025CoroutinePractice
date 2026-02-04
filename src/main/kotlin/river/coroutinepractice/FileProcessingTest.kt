package river.coroutinepractice

import kotlinx.coroutines.*
import java.io.File

fun main() = runBlocking {
    val processor = FileProcessor()
    processor.process100FilesInParallel()
}

class FileProcessor {
    suspend fun process100FilesInParallel() {
        val tempDir = File("tmp/input-files-test")
        if (!tempDir.exists()) tempDir.mkdirs()

        repeat(100) { idx ->
            File(tempDir, "file-$idx.txt")
                .writeText("test file $idx")
        }

        val result = coroutineScope {
            tempDir.listFiles()?.map { file ->
                async(Dispatchers.IO) {
                    file.readText().length
                }
            }?.awaitAll()
        }

        println("▶ processed ${result?.size} files")

        tempDir.deleteRecursively()
    }
}