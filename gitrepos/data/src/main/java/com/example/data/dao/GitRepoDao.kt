package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.dto.GitRepoDTO


@Dao
interface GitRepoDao {


    @Query("SELECT * FROM git_repo")
    suspend fun getAllRepos(): List<GitRepoDTO>

    @Query("""
        SELECT * FROM git_repo 
        WHERE name LIKE '%' || :searchQuery || '%' 
           OR description LIKE '%' || :searchQuery || '%' 
           OR fullName LIKE '%' || :searchQuery || '%'
    """)
    suspend fun getReposSearch(searchQuery: String): List<GitRepoDTO>

    @Query("""
        SELECT * FROM git_repo 
        WHERE (name LIKE '%' || :searchQuery || '%' 
           OR description LIKE '%' || :searchQuery || '%' 
           OR fullName LIKE '%' || :searchQuery || '%')  AND (owner LIKE '%' || :username || '%')
    """)
    suspend fun getReposSearch(searchQuery: String, username: String): List<GitRepoDTO>

    @Query("DELETE FROM git_repo WHERE id = (SELECT id FROM git_repo ORDER BY primeId ASC LIMIT 1)")
    suspend fun deleteOldestRepo()

    @Query("SELECT COUNT(*) FROM git_repo")
    suspend fun getRepoCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: GitRepoDTO)

    @Transaction
    suspend fun insertRepoWithLimit(repo: GitRepoDTO) {
        val count = getRepoCount()

        if (count >= TABLE_MAX_LIMIT) {
            deleteOldestRepo()
        }

        insertRepo(repo)
    }

    companion object{
        const val TABLE_MAX_LIMIT = 20
    }
}