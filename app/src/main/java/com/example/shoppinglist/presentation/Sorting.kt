package com.example.shoppinglist.presentation

import android.util.Log
import kotlin.random.Random

object Sorting {
    const val TAG = "Sorting"

    private fun log(text: String){
        Log.d(COMMON_TAG,"$TAG: $text")
    }
    fun test() {
        val random = Random(100)

        val listForSort = mutableListOf<Int>().apply {
            for (i in 0 until 10) {
                add(random.nextInt(1000))
            }
        }
        log("init listForSort = $listForSort")
//        selectionSort(listForSort)
//        log("listForSort after selectionSort = $listForSort")

        bubbleSort(listForSort)
        log("listForSort after bubbleSort = $listForSort")
    }

    private fun MutableList<Int>.swap(index: Int, index2: Int) {
        if(index == index2){
            return
        }
        val buffer = this[index]
        this[index] = this[index2]
        this[index2] = buffer
    }

    //O (n2)
    fun selectionSort(list: MutableList<Int>){
        for (i in 0 until list.size){
            var minIndex = i //set min first item
            //search in rest item < first
            for (j in i + 1 until list.size){
                if(list[j] < list[minIndex]){
                    minIndex = j
                }
            }
            if(minIndex != i){
                list.swap(i,minIndex)
            }
        }
    }
    //O (n2)
    fun bubbleSort(list: MutableList<Int>){
        for (i in 0 until list.size){
            for(j in 0 until list.size - i - 1){
                if(list[j] > list[j + 1]){
                    list.swap(j,j + 1)
                }
            }
        }
    }
}