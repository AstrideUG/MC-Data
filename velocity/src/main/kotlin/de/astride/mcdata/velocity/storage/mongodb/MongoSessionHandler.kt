/*
 * © Copyright by Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.mcdata.velocity.storage.mongodb

import de.astride.mcdata.velocity.proxies.PlayerId
import de.astride.mcdata.velocity.storage.PlayerEntryHandler
import de.astride.mcdata.velocity.storage.SessionHandler
import de.astride.mcdata.velocity.structure.Session

/**
 * Created on 23.06.2019 00:51.
 * @author Lars Artmann | LartyHD
 */
object MongoSessionHandler : SessionHandler {

    private val playerEntryHandler: PlayerEntryHandler = MongoPlayerEntryHandler

    override val activeSessions: MutableMap<PlayerId, Session> = mutableMapOf()

    override fun set(id: PlayerId, session: Session) {
        activeSessions[id] = session
    }

    override fun save(id: PlayerId, username: String) {
        val session = activeSessions[id] ?: return
        val entry = playerEntryHandler.getOrCreate(id, username)
        val newEntry = entry.copy(sessions = entry.sessions + session)
        playerEntryHandler.add(newEntry)
        activeSessions -= id
    }

}