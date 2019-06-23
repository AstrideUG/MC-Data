package de.astride.mcdata.velocity.bson.codec

import de.astride.mcdata.velocity.extensions.playerId
import de.astride.mcdata.velocity.proxies.PlayerId
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
object CodecPlayerId : Codec<PlayerId> {

    override fun encode(writer: BsonWriter, entry: PlayerId, context: EncoderContext) {
        writer.writeString(entry.uuid.toString())
        writer.flush()
    }

    override fun decode(reader: BsonReader, p1: DecoderContext?): PlayerId = reader.readString("id").toUUID().playerId

    override fun getEncoderClass(): Class<PlayerId> = PlayerId::class.java

}
