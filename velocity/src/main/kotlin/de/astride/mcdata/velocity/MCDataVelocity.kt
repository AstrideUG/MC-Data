package de.astride.mcdata.velocity

import com.google.gson.JsonObject
import com.google.inject.Inject
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.PluginDescription
import com.velocitypowered.api.plugin.annotation.DataDirectory
import de.astride.mcdata.velocity.configs.ConfigMongoDB
import de.astride.mcdata.velocity.configs.configMongoDB
import de.astride.mcdata.velocity.configs.mongoDB
import de.astride.mcdata.velocity.configs.mongoDBConfigData
import de.astride.mcdata.velocity.listener.SessionListener
import de.astride.mcdata.velocity.storage.SessionHandler
import de.astride.mcdata.velocity.storage.mongodb.MongoSessionHandler
import net.darkdevelopers.darkbedrock.darkness.general.databases.mongodb.MongoDB
import net.darkdevelopers.darkbedrock.darkness.general.functions.load
import net.darkdevelopers.darkbedrock.darkness.general.functions.toConfigData
import net.darkdevelopers.darkbedrock.darkness.general.functions.toMap
import java.nio.file.Path
import java.util.logging.Logger
import kotlin.system.measureTimeMillis

/**
 * Created on 22.06.2019 22:23.
 * @author Lars Artmann | LartyHD
 */
@Suppress("unused") //called from velocity
@Plugin(
    id = "mcdata-velocity",
    name = "MCData-velocity",
    version = "@version@",
    authors = ["Lars Artmann | LartyHD"]
)
class MCDataVelocity @Inject private constructor(
    private val eventManager: EventManager,
    private val logger: Logger,
    @DataDirectory private val path: Path,
    private val description: PluginDescription
) {

    private val sessions: SessionHandler = MongoSessionHandler

    @Suppress("UNUSED_PARAMETER")
    @Subscribe
    fun on(event: ProxyInitializeEvent) {
        start("${description.name.orElse(null)} v${description.version.orElse(null)}") { registerListener() }
    }

    private fun registerListener(): Unit = start("listener") {
        start(simpleName<ConfigMongoDB>()) {
            mongoDBConfigData = "mongodb".toConfigData(path.toFile())
            configMongoDB = ConfigMongoDB(mongoDBConfigData.load<JsonObject>().toMap())
        }
        start(simpleName<MongoDB>()) {
            mongoDB = MongoDB(mongoDBConfigData)
            mongoDB.connect()
        }
        start(simpleName<SessionListener>()) { SessionListener(sessions, logger).registerAsListener() }
    }

    private fun start(name: String, code: () -> Unit) {
        logger.info("Start $name...")
        val time = measureTimeMillis {
            try {
                code()
            } catch (ex: Exception) {
                logger.warning("Error message: ${ex.message}")
                ex.printStackTrace()
            }
        }
        logger.info("Started $name in ${time}ms")
    }

    private fun Any.registerAsListener(): Unit = eventManager.register(this@MCDataVelocity, this)

    private inline fun <reified T> simpleName(): String = T::class.java.simpleName.orEmpty()

}