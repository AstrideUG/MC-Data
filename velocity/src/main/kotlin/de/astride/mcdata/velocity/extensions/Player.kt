package de.astride.mcdata.velocity.extensions

import com.velocitypowered.api.proxy.Player

/*
 * Created on 23.06.2019 00:39.
 * @author Lars Artmann | LartyHD
 */

val Player.playerId get() = uniqueId.playerId