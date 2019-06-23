package de.astride.mcdata.velocity.structure

/**
 * Created on 23.06.2019 00:43.
 * @author Lars Artmann | LartyHD
 */
data class Timed<T>(val value: T, val time: Long = System.currentTimeMillis())

fun <T> T.toTimed(): Timed<T> = Timed(this)