package com.dicoding.expert.core.database.room

import androidx.room.*
import com.dicoding.expert.core.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg user: UserEntity)

    @Query("SELECT * FROM user_table")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE id = :id)")
    suspend fun hasAdded(id: Int): Boolean

    @Query("DELETE FROM user_table WHERE user_table.id = :id")
    suspend fun deleteById(id:Int)
}