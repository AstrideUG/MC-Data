/*
 * © Copyright by Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.mcdata.velocity.configs

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import de.astride.mcdata.velocity.bson.codec.CodecPlayerEntry
import de.astride.mcdata.velocity.bson.codec.CodecPlayerId
import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.databases.mongodb.MongoDB
import org.bson.codecs.IntegerCodec
import org.bson.codecs.StringCodec
import org.bson.codecs.configuration.CodecRegistries
import org.litote.kmongo.getCollection

/*
 * Created on 23.06.2019 01:36.
 * @author Lars Artmann | LartyHD
 */

lateinit var mongoDBConfigData: ConfigData
lateinit var configMongoDB: ConfigMongoDB

lateinit var mongoDB: MongoDB


val mongoDatabase: MongoDatabase
    get() = mongoDB.client.getDatabase(configMongoDB.database).withCodecRegistry(
        CodecRegistries.fromCodecs(CodecPlayerEntry, CodecPlayerId, StringCodec(), IntegerCodec())
    )

inline fun <reified T : Any> getMongoCollection(name: String): MongoCollection<T> =
    mongoDatabase.getCollection<T>("${configMongoDB.collectionPrefix}$name${configMongoDB.collectionSuffix}")