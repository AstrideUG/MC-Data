package de.astride.mcdata.velocity.structure

import de.astride.mcdata.velocity.proxies.PlayerId

/**
 * Created on 22.06.2019 22:05.
 * @author Lars Artmann | LartyHD
 */
data class PlayerEntry(
    val uuid: PlayerId,
    val username: String,
    val sessions: Set<Session> = emptySet()
)