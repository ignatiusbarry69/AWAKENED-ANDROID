package com.example.hubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
class FavoriteUser (
    @field:PrimaryKey
    @field:ColumnInfo(name= "id")
    var id: String = "",

    @field:ColumnInfo(name = "username")
    var username: String? = null,

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null


) : Parcelable {
    @Ignore
    constructor() : this("", null, null)
}