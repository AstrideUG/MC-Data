package de.astride.mcdata.velocity.configs

import net.darkdevelopers.darkbedrock.darkness.general.configs.default
import net.darkdevelopers.darkbedrock.darkness.general.configs.getValue

/**
 * Created on 23.06.2019 01:54.
 * @author Lars Artmann | LartyHD
 */
class ConfigMongoDB(values: Map<String, Any?>) {
    val database: String by values.default { "mc-data" }
    val collectionPrefix: String by values.default { "" }
    val collectionSuffix: String by values.default { "" }
}