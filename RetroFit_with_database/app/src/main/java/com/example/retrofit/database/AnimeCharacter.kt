package com.example.retrofit.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class to mention the template of the table in the database to the ROOM
 *
 * @Entity(tableName = "tableName")
 *      Explanation:
 *          '@' is the notation to mention word coming followed by as the annotation to the ROOM
 *          'Entity' is the annotation to mention the table format to the ROOM by providing a data class
 *                  each parameter in the data class is column in the table
 *          '()' content inside the parenthesis are parameter that mention the property of the db table
 *          'tableName' is the keyword to mention the name of the table mentioning explicitly
 *                  by default the name of the table is the name of the data class
 *
 * data class class_name()
 *      Explanation:
 *          each parameter in the data class is column in table
 *          each has it own unique properties(dataType, defaultValue...)
 *
 *          primary properties such as primary key, unique, etc has to mentioned explicitly
 *          '@PrimaryKey' is the annotation to mention the variable of the data class as a primary key of the table
 *          'autoGeneration' it is the argument of the any property that will indicate the value to be auto incremented for every insertion (if it is assigned as 'true')
 *          '@ColumnInfo' is the annotation to mention each column's properties such as name, defaultValue,...
 *          ==> @ColumnInfo(name="test_name")
 *
 */
@Entity(tableName = "character")
data class AnimeCharacter(
    @PrimaryKey
    val id:String,
    val name: String,
    val image: String,
    val aliveStatus:String,
    val species:String,
    val gender:String,
    val origin:String,
    val location:String,
)
