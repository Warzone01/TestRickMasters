package com.kirdevelopment.testrickmasters.data.local.door

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class DoorRealm (
    var favourites: Boolean = false,
    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    var name: String = "",
    var room: String? = "",
    var snapshot: String? = "",
    var isBlock: Boolean = false
): RealmObject()