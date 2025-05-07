package com.example.shoppinglist.presentation

import android.util.Log
import kotlin.random.Random


object QueueWithPriority {
    private val heap: MutableList<Int> = mutableListOf()
    const val TAG = "QueueWithPriority"

    private fun log(text: String){
        Log.d(COMMON_TAG,"$TAG: $text")
    }
    fun test(){
        val random = Random(100)

        val listForSort = mutableListOf<Int>().apply {
            for(i in 0 until 10){
                add(random.nextInt(1000))
            }
        }
        log("list before sort = $listForSort" )
        sort(listForSort)
        log("list after sort = $listForSort" )

//        for(i in 0 until 10){
//            insert(random.nextInt(100))
//        }
//        log("heap after inserts = $heap")
//        log("heap extractMax = " + extractMax())
//        log("heap after extractMax = $heap")
//        remove(5)
//        log("heap after remove 5 = $heap")
//        remove(5)
//        log("heap after remove 5 = $heap")

    }

    fun sort(list: MutableList<Int>){
        for(item in list){
            insert(item)
        }
        for(i in list.size -1 downTo 0){
            list[i] = extractMax()
        }
    }



    fun insert(value : Int){
        val position = heap.size
        //add to end
        heap.add(value)
        siftUp(position)
    }

    fun extractMax() : Int{
        if (heap.isEmpty()) return 0
        val result = heap[0]
        //move last to top
        heap[0] = heap[heap.size-1]
        heap.removeLast()
        siftDown(0)
        return result
    }

    fun remove(position : Int){
        if(position >= heap.size){
            return
        }
        heap[position] = Int.MAX_VALUE
        siftUp(position)
        extractMax()
    }

    private fun parent(index: Int): Int? {
        val p = (index-1)/2
        if(p < heap.size){
            return p
        }
        return null
    }
    private fun left(index: Int): Int? {
        val l = 2 * index + 1
        if (l < heap.size) {
            return l
        }

        return null
    }

    private fun right(index: Int): Int?
    {
        val r = 2 * index + 2
        if (r < heap.size) {
            return r
        }

        return null
    }


    private fun swap(index: Int, index2: Int) {
        val buffer = heap[index]
        heap[index] = heap[index2]
        heap[index2] = buffer
    }

    fun siftUp(index: Int) {
        log("siftUp $index")
        var i = index
        while (i > 0 ) {
            val p = parent(i) ?: return
            log("siftUp $p")
            if(heap[p] < heap[i]) {
                swap(p, i)
                i = p
            }else{
                return
            }
        }
    }

    fun siftDown(index: Int) {
        var maxIndex = index
        val leftChild = left(index)
        leftChild?.let {
            if(heap[leftChild] > heap[maxIndex]){
                maxIndex = leftChild
            }
        }

        val rightChild = right(index)
        rightChild?.let {
            if(heap[rightChild] > heap[maxIndex]){
                maxIndex = rightChild
            }
        }
        if(index != maxIndex){
            swap(index,maxIndex)
            siftDown(maxIndex)
        }
    }

}

