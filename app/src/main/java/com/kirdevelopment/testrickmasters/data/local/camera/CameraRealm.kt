package com.kirdevelopment.testrickmasters.data.local.camera

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class CameraRealm(
    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    var favourites: Boolean = false,
    var name: String = "",
    var rec: Boolean = false,
    var room: String? = null,
    var snapshot: String = ""
): RealmObject()