package com.example.retrofit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * CharacterDatabase is an Abstract class that extends RoomDatabase
 *     > it creates the instance of the CharacterDatabase for interacting with room database
 *     Class is abstract to leave unimplemented method as it is.
 *
 */
@Database(entities = [AnimeCharacter::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    /*
     * characterDAO is an abstract variable of the CharacterDAO interface
     */
    abstract val characterDAO: CharacterDAO


    companion object {
        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        /**
         * getInstance is a method that creates a singleton object of the interface characterDAO
         *  and returns it
         *  Things inside this method are synchronised as creating multi instance is not a singleton
         *  object which occurs during multi threading
         */
        fun getINSTANCE(context: Context): CharacterDatabase {
            synchronized(this) {
                /*
                 * using kotlin smart cast for flow control statement
                 */
                var instance = INSTANCE
                if (instance == null) {
                    // creating instance of the CharacterDatabase class
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharacterDatabase::class.java,
                        "CharacterDatabase"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }

}


/**
 * List<AnimeCharacter>.asDomainModel() is a method that maps the list value to the data class
 * AnimeCharacter
 */
fun List<AnimeCharacter>.asDomainModel(): List<AnimeCharacter> {
    return map {
        AnimeCharacter(
            name=it.name,
            image = it.image
        )
    }
}
