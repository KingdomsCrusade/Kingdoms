package net.kingdomscrusade.kingdoms.api.util

import java.nio.ByteBuffer
import java.util.*

fun ByteArray.toUUID() : UUID {
    val wrap = ByteBuffer.wrap(this)
    return UUID(wrap.long, wrap.long)
}

fun UUID.toByteArray() : ByteArray =
    ByteBuffer.wrap(ByteArray(16)).apply {
        putLong(mostSignificantBits)
        putLong(leastSignificantBits)
    }.array()
