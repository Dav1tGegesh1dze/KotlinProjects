package com.example.task23.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.task23.presentation.profile.ProfileItem

@Dao
@OptIn(androidx.paging.ExperimentalPagingApi::class)
interface ProfileDao {
    @Query("SELECT * FROM profiles")
    fun getProfiles(): PagingSource<Int, ProfileItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<ProfileItem>)

    @Query("DELETE FROM profiles")
    suspend fun clearAllProfiles()
}