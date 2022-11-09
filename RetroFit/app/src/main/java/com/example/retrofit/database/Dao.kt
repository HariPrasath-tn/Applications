package com.example.retrofit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO Interface to access the database
 *
 * 1.@insert -> input: object of AttendanceList
 *              [+] onConflict is raised when the primary key already exists
 *                  in order to solve onConflict is assigned with OnConflictStrategy.REPLACE inorder
 *                  replace the already existing data
 *              functionality: insert a new row of data according the dataClass's(AttendanceList) data
 * 2.@update -> input: object of the AttendanceList of Already existing data
 *              functionality: update values already inserted into the database table based on the primary key
 *
 * 3.delete -> input: id/Primary key reference of that data
 *             functionality: delete that particular record if available
 *
 *4.@Query -> in case of any non pre-defined query we can use this annotation pass any query as value
 *              eg : @Query("SELECT * FROM table_name)
 *              input : if any needed & if any passed we can mention it with ':' symbol
 *              eg : (if id passed) @Query("SELECT * FROM table_name WHERE id=:id)
 *              functionality: execute the query as the normal sql query
 */
@Dao
interface CharacterDAO {
    @Query("Select * from character")
    fun getAllCharacter(): List<AnimeCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg params:AnimeCharacter)
}
