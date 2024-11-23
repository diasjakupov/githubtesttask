package com.example.data.db

import androidx.room.TypeConverter
import com.example.data.dto.Owner
import com.google.gson.Gson

class OwnerTypeConverter {
    @TypeConverter
    fun fromOwner(owner: Owner?): String? {
        return owner?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toOwner(ownerString: String?): Owner? {
        return ownerString?.let {
            Gson().fromJson(it, Owner::class.java)
        }
    }
}