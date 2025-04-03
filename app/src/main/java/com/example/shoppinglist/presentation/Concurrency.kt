package com.example.shoppinglist.presentation

import android.util.Log
import java.util.Collections
import java.util.LinkedList
import java.util.Objects
import java.util.Queue
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.concurrent.thread


class BlockingQueue {
    private val queue: Queue<Runnable> = LinkedList<Runnable>()
    private val monitor = Object()

    fun add(task: Runnable) {
        synchronized(monitor) {
            queue.add(task)
            monitor.notify()
        }
    }

    fun take(): Runnable? {
        synchronized(monitor) {
            while (queue.isEmpty()) {
                monitor.wait()
            }
            return queue.poll()
        }
    }

}

object Concurrency {
    const val TAG = "Concurrency"

    const val A = "A"
    const val B = "B"
    const val C = "C"
    private val monitor = Object()
    private var nextLetter = A

    private fun log(text: String) {
        Log.d(TAG, text)
    }

    fun main1() {
        val blockingQueue = BlockingQueue()

        thread {
            var i: Int = 0
            while (true) {
                val task = blockingQueue.take()
                log("Counter = $i")
                i++
                if (task != null) {
                    Thread(task).start()
                }
            }
        }

        for (i in 0..10) {
            blockingQueue.add(Runnable {
                try {
                    Thread.sleep(1000)
                } catch (ex: InterruptedException) {
                    log("InterruptedException")
                }
                log("--- $i")
            })

        }
    }

    fun main() {

        log("main")
        thread {

            synchronized(monitor) {
                for (i in 1..5) {
                    while (nextLetter != A) {
                        monitor.wait()
                    }
                    Log.d(TAG, A)
                    nextLetter = B
                    monitor.notifyAll()

                }
            }
        }

        thread {

            synchronized(monitor) {
                for (i in 1..5) {
                    while (nextLetter != B) {
                        monitor.wait()
                    }
                    Log.d(TAG, B)
                    nextLetter = C
                    monitor.notifyAll()

                }
            }
        }

        thread {

            synchronized(monitor) {
                for (i in 1..5) {
                    while (nextLetter != C) {
                        monitor.wait()
                    }
                    Log.d(TAG, C)
                    nextLetter = A
                    monitor.notifyAll()

                }
            }
        }

    }
}