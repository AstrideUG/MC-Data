@file:Suppress("unused")

package de.astride.mcdata.velocity.functions

import com.mongodb.client.MongoCollection
import org.bson.conversions.Bson

/*
 * Created on 23.06.2019 02:39.
 * @author Lars Artmann | LartyHD
 */

//TODO crate a issue for darkness to a this

fun MongoCollection<*>.exists(bson: Bson): Boolean = firstOrNull(bson) != null
fun <T> MongoCollection<T>.firstOrNull(bson: Bson): T? = find(bson).limit(1).firstOrNull()