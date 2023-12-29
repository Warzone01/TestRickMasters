package com.kirdevelopment.testrickmasters.data.local.camera

import com.kirdevelopment.testrickmasters.data.remote.dto.Camera
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CameraDatabaseOperations @Inject constructor (
    private val config: RealmConfiguration
) {
    suspend fun insertCamera(
        id: String,
        name: String,
        favourite: Boolean,
        rec: Boolean,
        room: String?,
        snapshot: String
    ) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val camera = CameraRealm(
                id = id,
                name = name,
                favourites = favourite,
                rec = rec,
                room = room,
                snapshot = snapshot
            )
            realmTransaction.copyToRealmOrUpdate(camera)
        }
    }

    suspend fun retrieveCamera(): List<Camera> {
        val realm = Realm.getInstance(config)
        val camerasToAdopt = mutableListOf<Camera>()

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            camerasToAdopt.addAll(realmTransaction
                .where(CameraRealm::class.java)
                .findAll()
                .map {
                    mapCamera(it)
                }
            )
        }
        return camerasToAdopt
    }

    suspend fun changeCameraFavouriteStatus(cameraId: String, isFavourite: Boolean) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val cameraToChange = realmTransaction
                .where(CameraRealm::class.java)
                .equalTo("id", cameraId)
                .findFirst()
            cameraToChange?.favourites = isFavourite
        }
    }

    private fun mapCamera(camera: CameraRealm): Camera {
        return Camera(
            favorites = camera.favourites,
            id = camera.id.toInt(),
            name = camera.name,
            rec = camera.rec,
            room = camera.room ?: "",
            snapshot = camera.snapshot
        )
    }
}