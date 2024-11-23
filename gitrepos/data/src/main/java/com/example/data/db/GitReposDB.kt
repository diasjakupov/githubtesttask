package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.UserDao
import com.example.data.dao.GitRepoDao
import com.example.data.dto.UserDTO
import com.example.data.dto.GitRepoDTO

@Database(entities = [GitRepoDTO::class, UserDTO::class], version = 1, exportSchema = false)
abstract class GitReposDB : RoomDatabase() {
    abstract fun gitReposDao(): GitRepoDao
    abstract fun getOwnerDao(): UserDao

    companion object{
        const val DB_NAME = "git_repo"
    }
}