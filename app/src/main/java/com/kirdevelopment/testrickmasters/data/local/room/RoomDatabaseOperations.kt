package com.kirdevelopment.testrickmasters.data.local.room

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RoomDatabaseOperations @Inject constructor(
    private val config: RealmConfiguration
) {
    suspend fun insertRoom (
        roomName: String
    ) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val room = RoomRealm(
                room = roomName
            )
            realmTransaction.copyToRealmOrUpdate(room)
        }
    }

    suspend fun retrieveRooms(): List<String> {
        val realm = Realm.getInstance(config)
        val rooms = mutableListOf<String>()

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            rooms.addAll(realmTransaction
                .where(RoomRealm::class.java)
                .findAll()
                .map { it.room }
            )
        }
        return rooms
    }
}