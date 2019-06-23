package de.astride.mcdata.velocity.listener

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.proxy.Player
import de.astride.mcdata.velocity.extensions.playerId
import de.astride.mcdata.velocity.storage.SessionHandler
import de.astride.mcdata.velocity.structure.Session
import de.astride.mcdata.velocity.structure.toTimed
import net.kyori.text.TextComponent
import net.kyori.text.serializer.legacy.LegacyComponentSerializer
import java.util.logging.Logger

/**
 * Created on 22.06.2019 23:23.
 * @author Lars Artmann | LartyHD
 */
class SessionListener(private val sessions: SessionHandler, private val logger: Logger) {

    @Subscribe(order = PostOrder.LAST)
    fun on(event: LoginEvent) {
        val player: Player = event.player
        sessions[player.playerId] = player.createSession(event.result.convertResult())
    }

    @Subscribe(order = PostOrder.LATE)
    fun on(event: DisconnectEvent) {
        val player: Player = event.player
        val session = sessions[player.playerId]
        if (session != null) {
            session.invalidTime = System.currentTimeMillis()
            logger.info("Session of ${player.uniqueId} (${player.username}) is $session")
        } else logger.warning("${player.uniqueId} (${player.username}) has no session!")
        sessions.save(player.playerId, player.username)
        logger.info("Saved ${player.uniqueId} (${player.username})")
    }

    private fun ResultedEvent.ComponentResult.convertResult(): String? {
        val rawResult = (if (isAllowed) null else reason.orElse(null)) ?: TextComponent.empty()
        return LegacyComponentSerializer.legacyLinking().serialize(rawResult).ifEmpty { null }
    }

    private fun Player.createSession(result: String?): Session = Session(
        remoteAddress,
        virtualHost.orElse(null),
        "${protocolVersion.protocol}/${protocolVersion.getName()}",
        result,
        modInfo.orElse(null),
        mutableSetOf(playerSettings.toTimed())
    )


}