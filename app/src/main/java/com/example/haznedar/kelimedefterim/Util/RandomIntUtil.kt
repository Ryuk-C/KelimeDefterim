package com.example.haznedar.kelimedefterim.Util

import java.util.concurrent.atomic.AtomicInteger

object RandomIntUtil {

    private val seed = AtomicInteger()

    fun getRandomInt() = seed.getAndIncrement() + System.currentTimeMillis().toInt()

}