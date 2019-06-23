/*
 * © Copyright by Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.mcdata.velocity.bson.codec

import de.astride.mcdata.velocity.extensions.playerId
import de.astride.mcdata.velocity.structure.PlayerEntry
import net.darkdevelopers.darkbedrock.darkness.general.functions.toUUID
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

/**
 * Created on 23.06.2019 07:56.
 * @author Lars Artmann | LartyHD
 */
object CodecPlayerEntry : Codec<PlayerEntry> {

    override fun encode(writer: BsonWriter, entry: PlayerEntry, context: EncoderContext) {
        writer.writeStartDocument()
        writer.writeString("id", entry.uuid.toString())
        writer.writeString("username", entry.username)
        writer.writeStartArray("sessions")
        entry.sessions.forEach {
            writer.writeString(it.toString())
        }
        writer.writeEndArray()
        writer.writeEndDocument()
        writer.flush()
    }

    override fun decode(reader: BsonReader, p1: DecoderContext?): PlayerEntry {
        reader.readStartDocument()
        val uuid = reader.readString("id").toUUID()
        val name = reader.readString("username")
//                reader.readStartArray()
////                "sessions"
//                reader.readEndArray()
        reader.readEndDocument()
        return PlayerEntry(uuid.playerId, name)
    }

    override fun getEncoderClass(): Class<PlayerEntry> = PlayerEntry::class.java

}