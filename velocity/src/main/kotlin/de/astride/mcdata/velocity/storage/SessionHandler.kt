package de.astride.mcdata.velocity.storage

import de.astride.mcdata.velocity.proxies.PlayerId
import de.astride.mcdata.velocity.structure.Session

/**
 * Created on 23.06.2019 00:32.
 * @author Lars Artmann | LartyHD
 */
interface SessionHandler {

    val activeSessions: Map<PlayerId, Session>

    fun save(id: PlayerId, username: String)

    operator fun set(id: PlayerId, session: Session)
    operator fun get(id: PlayerId): Session? = activeSessions[id]

}