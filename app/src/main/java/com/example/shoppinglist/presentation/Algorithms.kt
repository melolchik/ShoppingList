package com.example.shoppinglist.presentation

import android.util.Log
import java.util.Deque
import kotlin.math.abs


object Algorithms {
    const val TAG = "Algorithms"

    private fun log(text: String){
        Log.d(COMMON_TAG,"$TAG: $text")
    }
    fun test(){
        log("fibonachi(20) = " + fibonachi(20))
        log("euclide(54,36) = " + euclide(54,36))
        log("euclide(1051,2332) = " + euclide(1051,2332))
        log("stringIsBalanced(()) = " + stringIsBalanced("(())"))
        log("stringIsBalanced[(]) = " + stringIsBalanced("[(])"))
        log("stringIsBalanced [()]{{}} = " + stringIsBalanced("[()]{{}}"))
        log("stringIsBalanced [()]{{} = " + stringIsBalanced("[()]{{}"))
        log("minInWindow () 5" + minInWindow(listOf(5,1,3,2,4,6,1,7,3,2,8,9),5))
        log("minInWindow () 4" + minInWindow(listOf(5,1,3,2,4,6,1,7,3,2,8),4))
        log("minInWindow () 3" + minInWindow(listOf(5,1,3,2,4,6,1,7,3,2,8),3))
        searchSampleInText("ababacaba", "aba")
        log("treeHeight = " + treeHeight(9,7,5,5,2,9,9,9,2,-1))
        log("twoMaxMultiple = " + twoMaxMultiple(4,3,5,2,5))
        log("twoMaxMultiple = " + twoMaxMultiple(12288 ,-10075 ,29710, 15686 ,-18900, -17715,
            15992 ,24431 ,6220, 28403 ,-23148, 18480, -22905 ,5411, -7602, 15560, -26674 ,11109, -4323 ,6146 ,-1523, 4312 ,10666,
            -15343, -17679 ,7284, 20709 ,-7103, 24305, 14334, -12281, 17314, 26061, 25616, 17453, 16618, -24230, -19788, 2117,2,
            11339, 2202, -22442 ,-20997,
            1879 ,-8773, -8736, 5310, -23372, 12621, -25596 ,-28609, -13309, -13 ,10336, 15812 ,-21193, 21576, -1897 ,
            -12311 ,-6988, -25143 ,-3501 ,23231 ,26610 ,12618 ,25834, -29140, 21011 ,23427, 1494 ,15215 ,23013, -15739, 8325 ,
            5359 ,-12932 ,18111, -72, -12509, 20116, 24390 ,1920 ,17487, 25536, 24934, -6784 ,-16417, -2222, -16569 ,-25594, 4491,
            14249, -28927 ,27281 ,3297, 5998, 6259, 4577, 12415 ,3779, -8856 ,3994, 19941, 11047 ,2866 ,-24443 ,-17299, -9556, 12244 ,
            6376 ,-13694 ,-14647 ,-22225 ,21872 ,7543 ,-6935, 17736 ,-2464 ,9390 ,1133 ,18202, -9733, -26011 ,13474 ,29793, -26628,
            -26124 ,27776, 970 ,14277 ,-23213 ,775, -9318, 29014 ,-5645, -27027, -21822, -17450, -5 ,-655, 22807, -20981, 16310, 27605, -18393 ,914,
            7323, 599, -12503, -28684, 5835 ,-5627, 25891, -11801 ,21243, -21506, 22542, -5097, 8115, 178, 10427, 25808, 10836, -11213,
            18488, 21293 ,14652, 12260, 42 ,21034, 8396 ,-27956 ,13670, -296, -757, 18076, -15597, 4135, -25222 ,-19603 ,8007 ,6012, 2704, 28935 ,16188, -20848 ,13502, -11950, -24466 ,5440, 26348, 27378, 7990, -11523, -26393 ))
        log("twoMaxMultiple = " + twoMaxMultiple(-4, 3 ,-5, 2, 5))
        val str = "12288 -10075 29710 15686 -18900 -17715 15992 24431 6220 28403 -23148 18480 -22905 5411 -7602 15560 -26674 11109 -4323 6146 -1523 4312 10666 -15343 -17679 7284 20709 -7103 24305 14334 -12281 17314 26061 25616 17453 16618 -24230 -19788 21172 11339 2202 -22442 -20997 1879 -8773 -8736 5310 -23372 12621 -25596 -28609 -13309 -13 10336 15812 -21193 21576 -1897 -12311 -6988 -25143 -3501 23231 26610 12618 25834 -29140 21011 23427 1494 15215 23013 -15739 8325 5359 -12932 18111 -72 -12509 20116 24390 1920 17487 25536 24934 -6784 -16417 -2222 -16569 -25594 4491 14249 -28927 27281 3297 5998 6259 4577 12415 3779 -8856 3994 19941 11047 2866 -24443 -17299 -9556 12244 6376 -13694 -14647 -22225 21872 7543 -6935 17736 -2464 9390 1133 18202 -9733 -26011 13474 29793 -26628 -26124 27776 970 14277 -23213 775 -9318 29014 -5645 -27027 -21822 -17450 -5 -655 22807 -20981 16310 27605 -18393 914 7323 599 -12503 -28684 5835 -5627 25891 -11801 21243 -21506 22542 -5097 8115 178 10427 25808 10836 -11213 18488 21293 14652 12260 42 21034 8396 -27956 13670 -296 -757 18076 -15597 4135 -25222 -19603 8007 6012 2704 28935 16188 -20848 13502 -11950 -24466 5440 26348 27378 7990 -11523 -26393"
        val list = str.split(" ").filter { it.isBlank() }. map { it.toLong() }
        log("twoMaxMultiple = " + twoMaxMultiple(list))
    }

    private fun fibonachi(n :Int) : Int{
        val f = arrayOfNulls<Int?>(n)
        f[0] = 0
        f[1] = 1
        for(i in 2 until n){
            f[i] = f[i-1]?.plus(f[i-2]!!)
        }
        return f[n - 1] ?: 0
    }

    private fun euclide(a : Int, b : Int) : Int{
        if(a == 0) return b;
        if(b == 0) return a;
        if(a >= b) {
            return euclide(a.mod(b), b)
        }
        else {
            return euclide(a, b.mod(a))
        }
    }

    private fun stringIsBalanced(str : String) : Boolean{
        val left = "{[("
        val right = "}])"
        val stack: Deque<Char> = java.util.ArrayDeque<Char>()
        for(char in str){
            if(left.contains(char)){
                stack.push(char)
                continue
            }
            if(stack.isEmpty()) return false
            if(right.contains(char)){
               val topInStack = stack.pop()
                val leftPos = left.indexOf(topInStack)
                val rightPos = right.indexOf(char)
                if(leftPos != rightPos) return false
                continue
            }
        }
        return stack.isEmpty()
    }

    private fun minInWindow(list : List<Int>, windowSize : Int = 3 ) : List<Int>{


        log("minInWindow started with params list = $list windowSize = $windowSize")
        val result : MutableList<Int> = mutableListOf()
        if(list.size < windowSize){
            return result
        }

        if(windowSize < 2){
            return list
        }
        val window : ArrayDeque<Int> = ArrayDeque()
        val dequeOfMinimums: ArrayDeque<Int> = ArrayDeque()
        dequeOfMinimums.addLast(list[windowSize - 1])
        for(i in windowSize - 2 downTo 0){
            dequeOfMinimums.addLast(minOf(list[i],dequeOfMinimums.last()))
            log("minInWindow dequeOfMinimums = $dequeOfMinimums")
        }
        result.add(dequeOfMinimums.last())
        log("minInWindow result = $result")
        dequeOfMinimums.removeLast()
        log("minInWindow dequeOfMinimums = $dequeOfMinimums")
        var minInWindow : Int?= null
        for(i in windowSize until list.size){
            val itemInList = list[i]
            log("minInWindow see item = $itemInList dequeOfMinimums = $dequeOfMinimums")
            log("minInWindow see item = $itemInList window = $window")
            log("minInWindow see item = $itemInList minInWindow = $minInWindow")
            if(minInWindow == null){
                minInWindow = itemInList
            }else{
                minInWindow = minOf(minInWindow,itemInList)
            }
            window.addLast(itemInList)
            result.add(minOf(dequeOfMinimums.last(),minInWindow))
            log("minInWindow see item = $itemInList dequeOfMinimums = $dequeOfMinimums")
            log("minInWindow see item = $itemInList minInWindow = $minInWindow")
            log("minInWindow result = $result")
            dequeOfMinimums.removeLast()
            if(dequeOfMinimums.isEmpty()){
                dequeOfMinimums.addLast(window.last())
                window.removeLast()
                while (window.isNotEmpty()){
                   dequeOfMinimums.addLast(minOf(dequeOfMinimums.last(),window.last()))
                   window.removeLast()
                }
                minInWindow = null
            }

        }
        return result

    }


    fun twoMaxMultiple(vararg list : Long) : List<Long>{
        return twoMaxMultiple(list.toList())
    }

    fun twoMaxMultiple(list : List<Long>) : List<Long>{
        val dequePositiv : ArrayDeque<Long> = ArrayDeque()
        val dequeNegativ : ArrayDeque<Long> = ArrayDeque()
        for (item in list){
            val deque = if(item > 0) dequePositiv else dequeNegativ
            //val absItem = abs(item)
           // log("deque = $deque")
           
                when(deque.size){
                    0 -> deque.addFirst(item)
                    1 -> {
                        if(abs(deque.first()) > abs(item)) deque.addFirst(item) else deque.addLast(item)
                    }
                    2 -> {
                        if(abs(item) >= abs( deque.last())){
                            deque.addLast(item)
                            deque.removeFirst()
                        }
                    }
                }
            
        }
        val positivMultiple = dequePositiv.reduceOrNull { multiple, it -> multiple * it } ?: 0
        val negativeMultiple = dequeNegativ.reduceOrNull { multiple, it -> multiple * it } ?: 0
        if(positivMultiple > negativeMultiple){
            return dequePositiv.toList()
        }else{
            return dequeNegativ.toList()
        }
    }

    data class Item(val value : Int){
        val childList : MutableList<Item>  = mutableListOf()
        fun addChild(item : Item){
            childList.add(item)
        }

        override fun toString(): String {
            return "Item($value) childs = $childList "
        }
    }

    private fun treeHeight(item : Item) : Int{
        var height = 1;
        for(childItem in item.childList){
            height = maxOf(height, 1 + treeHeight(childItem))
        }
        return height
    }


    private fun treeHeight(vararg list : Int)  : Int{

        //build tree
        val listItems = ArrayList<Item>(list.size)

        var mainItem : Item? = null

        for((child,parent) in list.withIndex()){

            if(parent == -1){
                mainItem = listItems.find { it.value == child }
                continue
            }
            val parentItem = listItems.find { it.value == parent } ?: Item(parent)
            if(!listItems.contains(parentItem)) {
                listItems.add(parentItem)
            }

            val childItem = listItems.find { it.value == child } ?:Item(child)
            if(!listItems.contains(childItem)) {
                listItems.add(childItem)
            }
            parentItem.addChild(childItem)
        }

        log("treeHeight tree build  = $mainItem")

        return mainItem?.let { treeHeight(mainItem)} ?: 0
    }


    fun searchSampleInText(text : String , sample : String){
        log("searchSampleInText text = $text sample = $sample")
        val sampleSize = sample.length
        val x = 31
        val p = 321;
        val powerList : List<Int> = mutableListOf<Int>().apply {
            var value = 1
            for(i in 0 until sampleSize){
                add(value)
                value *= x
            }
        }
        log("searchSampleInText powerList = $powerList ")

        var sampleHash = 0;
        for((index,symbol) in sample.withIndex()){
            sampleHash += (symbol.code * powerList[index]).mod(p)
        }
        sampleHash = sampleHash.mod(p)
        log("searchSampleInText sampleHash = $sampleHash ")
        var hPrev = 0;
        val window : ArrayDeque<Char>  = ArrayDeque()
        for(i in 0 until sampleSize){
            val symbol = text[i]
            window.addLast(symbol)
            hPrev += (symbol.code * powerList[sampleSize - i - 1]).mod(p)

        }
        hPrev = hPrev.mod(p)
        log("searchSampleInText hPrev = $hPrev ")
        if(hPrev == sampleHash){
            log("searchSampleInText position = 0" )
        }

        for (i in sampleSize until text.length ){
            val symbolRemove = window.first()
            val symbolAdd = text[i]
            window.removeFirst()
            window.addLast(symbolAdd)
            val hNext =  ((hPrev - (symbolRemove.code * powerList.last()).mod(p)) * x + symbolAdd.code).mod(p)
            log("searchSampleInText hNext = $hNext ")
            if(hNext == sampleHash){
                log("searchSampleInText position = ${(i - sampleSize + 1)} ")
            }
            hPrev = hNext
        }
    }
}