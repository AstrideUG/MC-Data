package de.astride.mcdata.velocity.extensions

import de.astride.mcdata.velocity.proxies.PlayerId
import de.astride.mcdata.velocity.proxies.SessionId
import java.util.*

/*
 * Created on 23.06.2019 00:45.
 * @author Lars Artmann | LartyHD
 */

val UUID.sessionId get() = SessionId(this)
val UUID.playerId get() = PlayerId(this)