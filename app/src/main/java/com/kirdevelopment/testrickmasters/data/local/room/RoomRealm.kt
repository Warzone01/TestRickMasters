package com.kirdevelopment.testrickmasters.data.local.room

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RoomRealm (
    @PrimaryKey
    var room: String = ""
): RealmObject()