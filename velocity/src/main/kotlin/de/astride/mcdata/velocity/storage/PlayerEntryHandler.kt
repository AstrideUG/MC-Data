/*
 * © Copyright by Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.mcdata.velocity.storage

import de.astride.mcdata.velocity.proxies.PlayerId
import de.astride.mcdata.velocity.structure.PlayerEntry

/**
 * Created on 23.06.2019 00:33.
 * @author Lars Artmann | LartyHD
 */
interface PlayerEntryHandler {

    fun exists(uuid: PlayerId): Boolean

    operator fun get(uuid: PlayerId): PlayerEntry?
    fun getOrCreate(uuid: PlayerId, username: String): PlayerEntry

    fun add(entry: PlayerEntry)

}

operator fun PlayerEntryHandler.plusAssign(entry: PlayerEntry): Unit = add(entry)
