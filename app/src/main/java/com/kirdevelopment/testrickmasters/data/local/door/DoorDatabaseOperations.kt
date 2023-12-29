package com.kirdevelopment.testrickmasters.data.local.door

import com.kirdevelopment.testrickmasters.data.remote.dto.Door
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DoorDatabaseOperations @Inject constructor(
    private val config: RealmConfiguration
) {
    suspend fun insertDoor(
        id: String,
        name: String,
        favourite: Boolean,
        room: String?,
        snapshot: String,
        isBlocked: Boolean
    ) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val door = DoorRealm(
                id = id,
                name = name,
                favourites = favourite,
                room = room,
                snapshot = snapshot,
                isBlock = isBlocked
            )
            realmTransaction.copyToRealmOrUpdate(door)
        }
    }

    suspend fun retrieveDoor(): List<Door> {
        val realm = Realm.getInstance(config)
        val doors = mutableListOf<Door>()

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            doors.addAll(realmTransaction
                .where(DoorRealm::class.java)
                .findAll()
                .map {
                    mapDoor(it)
                }
            )
        }
        return doors
    }

    suspend fun changeDoorFavouriteStatus(doorId: String, isFavourite: Boolean) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val doorToChange = realmTransaction
                .where(DoorRealm::class.java)
                .equalTo("id", doorId)
                .findFirst()
            doorToChange?.favourites = isFavourite
        }
    }

    suspend fun editDoorName(doorId: String, name: String) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val doorToChange = realmTransaction
                .where(DoorRealm::class.java)
                .equalTo("id", doorId)
                .findFirst()
            doorToChange?.name = name
        }
    }

    suspend fun editDoorBlocked(doorId: String, isBlocked: Boolean) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val doorToChange = realmTransaction
                .where(DoorRealm::class.java)
                .equalTo("id", doorId)
                .findFirst()
            doorToChange?.isBlock = isBlocked
        }
    }

    private fun mapDoor(door: DoorRealm): Door {
        return Door(
            favorites = door.favourites,
            id = door.id.toInt(),
            name = door.name,
            room = door.room ?: "",
            snapshot = door.snapshot,
            isBlocked = door.isBlock
        )
    }
}