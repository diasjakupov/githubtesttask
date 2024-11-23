package com.example.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.dao.GitRepoDao
import com.example.data.dao.UserDao
import com.example.data.dto.UserDTO
import com.example.data.db.GitReposDB
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var database: GitReposDB
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                GitReposDB::class.java
        ).allowMainThreadQueries().build()
        userDao = database.getOwnerDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveAllUsers() = runBlocking {
        val user = UserDTO(
                name = "John Doe",
                followers = 100,
                bio = "Developer",
                image = "http://avatar.com/johndoe",
                publicRepos = 50
        )
        userDao.insertUser(user)

        val users = userDao.getAllUsers()
        assertEquals(1, users.size)
        assertEquals(user.name, users[0].name)
        assertEquals(user.followers, users[0].followers)
    }

    @Test
    fun searchUserByUsername() = runBlocking {
        val user1 = UserDTO(
                name = "Alice",
                followers = 120,
                bio = "Kotlin Developer",
                image = "http://avatar.com/alice",
                publicRepos = 10
        )
        val user2 = UserDTO(
                name = "Bob",
                followers = 80,
                bio = "Java Developer",
                image = "http://avatar.com/bob",
                publicRepos = 20
        )
        userDao.insertUser(user1)
        userDao.insertUser(user2)

        val results = userDao.getUserSearch("Alice")
        assertEquals(1, results.size)
        assertEquals(user1.name, results[0].name)
    }

    @Test
    fun insertUserWithLimitRemovesOldestWhenFull() = runBlocking {
        for (i in 1..GitRepoDao.TABLE_MAX_LIMIT + 1) {
            userDao.insertUserWithLimit(
                    UserDTO(
                            name = "User$i",
                            followers = i * 10,
                            bio = "Bio $i",
                            image = "http://avatar.com/user$i",
                            publicRepos = i
                    )
            )
        }

        val users = userDao.getAllUsers()
        assertEquals(GitRepoDao.TABLE_MAX_LIMIT, users.size)
        assertNull(users.find { it.name == "User1" })
    }

}
