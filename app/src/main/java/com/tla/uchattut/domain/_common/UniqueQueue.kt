package com.tla.uchattut.domain._common

import java.util.*

class UniqueQueue<E> : AbstractQueue<E>() {

    private val list = arrayListOf<E>()

    override val size: Int
        get() = list.size

    override fun offer(e: E): Boolean {
        if (list.isNotEmpty() && list[0] == e) return false

        if (list.contains(e)) {
            list.remove(e)
        }

        list.add(0, e)
        return true
    }

    override fun iterator(): MutableIterator<E> {
        return list.iterator()
    }

    override fun peek(): E? {
        if (list.isNullOrEmpty()) return null

        return list[0]
    }

    override fun poll(): E? {
        if (list.isNullOrEmpty()) return null

        val e = list[0]
        list.remove(e)
        return e
    }
}