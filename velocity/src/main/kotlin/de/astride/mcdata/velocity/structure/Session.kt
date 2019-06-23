/*
 * © Copyright by Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.mcdata.velocity.structure

import com.velocitypowered.api.proxy.player.PlayerSettings
import com.velocitypowered.api.util.ModInfo
import de.astride.mcdata.velocity.extensions.sessionId
import de.astride.mcdata.velocity.proxies.SessionId
import java.net.InetSocketAddress
import java.util.*

/**
 * Created on 22.06.2019 21:49.
 * @author Lars Artmann | LartyHD
 */
data class Session(
    val remoteAddress: InetSocketAddress,
    val virtualHost: InetSocketAddress?,
    val protocolVersion: String,
    val loginResult: String?,
    val modInfo: ModInfo?,
    val settings: MutableSet<Timed<PlayerSettings>>,
    val createTime: Long = System.currentTimeMillis(),
    var invalidTime: Long? = null,
    val id: SessionId = UUID.randomUUID().sessionId
)
