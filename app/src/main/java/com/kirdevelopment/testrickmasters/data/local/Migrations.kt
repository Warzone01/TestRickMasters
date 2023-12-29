package com.kirdevelopment.testrickmasters.data.local

import io.realm.FieldAttribute
import io.realm.RealmMigration

val migration = RealmMigration { realm, oldVersion, newVersion ->
    if (oldVersion == 1L) {
        val schema = realm.schema
        schema
            .create("RoomRealm")
            .addField("room", String::class.java, FieldAttribute.REQUIRED)
            .addIndex("room")
            .addPrimaryKey("room")
    }
}