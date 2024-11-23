package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.dao.GitRepoDao.Companion.TABLE_MAX_LIMIT
import com.example.data.dto.GitRepoDTO
import com.example.data.dto.UserDTO
import com.example.domain.entity.UserInfo


@Dao
interface UserDao {

    @Query("SELECT * FROM owner ORDER BY followers DESC")
    suspend fun getAllUsers(): List<UserDTO>

    @Query("SELECT * FROM owner WHERE name LIKE '%' || :username || '%' ORDER BY followers DESC")
    suspend fun getUserSearch(username: String): List<UserDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDTO: UserDTO)

    @Query("DELETE FROM owner WHERE name = (SELECT name FROM owner ORDER BY id ASC LIMIT 1)")
    suspend fun deleteOldestUser()

    @Query("SELECT COUNT(*) FROM owner")
    suspend fun getUserCount(): Int

    @Transaction
    suspend fun insertUserWithLimit(user: UserDTO) {
        val count = getUserCount()

        if (count >= TABLE_MAX_LIMIT) {
            deleteOldestUser()
        }

        insertUser(user)
    }
}