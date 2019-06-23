package de.astride.mcdata.velocity.storage.mongodb

import com.mongodb.client.model.Filters
import de.astride.mcdata.velocity.configs.getMongoCollection
import de.astride.mcdata.velocity.functions.exists
import de.astride.mcdata.velocity.functions.firstOrNull
import de.astride.mcdata.velocity.proxies.PlayerId
import de.astride.mcdata.velocity.storage.PlayerEntryHandler
import de.astride.mcdata.velocity.structure.PlayerEntry

/**
 * Created on 23.06.2019 00:52.
 * @author Lars Artmann | LartyHD
 */
object MongoPlayerEntryHandler : PlayerEntryHandler {

    private val collection get() = getMongoCollection<PlayerEntry>("players")

    override fun exists(uuid: PlayerId): Boolean = collection.exists(Filters.eq(PlayerEntry::uuid.name, uuid))

    override fun get(uuid: PlayerId): PlayerEntry? = collection.firstOrNull(Filters.eq(PlayerEntry::uuid.name, uuid))

    override fun getOrCreate(uuid: PlayerId, username: String): PlayerEntry = get(uuid) ?: PlayerEntry(uuid, username)

    override fun add(entry: PlayerEntry) {
//        collection.save(entry)
        val id = entry.uuid
//        collection.replaceOneById(id, entry, ReplaceOptions().upsert(true))
        if (exists(id))
            collection.insertOne(entry)
        else {
            collection.updateOne(
                Filters.eq(PlayerEntry::uuid.name, id),
                Filters.eq(PlayerEntry::username.name, entry.username)
            )
            collection.updateOne(
                Filters.eq(PlayerEntry::uuid.name, id),
                Filters.eq(PlayerEntry::sessions.name, entry.sessions)
            )
        }
    }

}
