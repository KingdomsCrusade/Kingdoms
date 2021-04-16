package net.kingdomscrusade.kingdoms

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

internal class KingdomsAPITest {

    @BeforeEach
    fun deleteExistingDBFile() {
        File("D:/Code/Resources/DummyPluginData.db").delete()
    }

    @Test
    @DisplayName("Database file creation test")
    fun creationTest(){

        /* Given */
        val dbFilePath = "D:/Code/Resources/DummyPluginData.db"

        /* When */
        KingdomsAPI(dbFilePath)

        /* Then */
        // Checking if a db file is created
        val dbFile = File(dbFilePath)
        assertTrue(dbFile.exists() && dbFile.isFile)

    }

    @Test
    @DisplayName("Database structure test")
    fun structureTest(){
        /* Given */
        val dbFilePath = "D:/Code/Resources/DummyPluginData.db"

        /* When */
        val api = KingdomsAPI(dbFilePath)

        /* Then */
        // Checking column counts of every table
        assertEquals( // Kingdoms
            2,
            api.executeQuery("SELECT * FROM Kingdoms").metaData.columnCount
        )
        assertEquals( // Permissions
            3,
            api.executeQuery("SELECT * FROM Permissions").metaData.columnCount
        )
        assertEquals( // Roles
            3,
            api.executeQuery("SELECT * FROM Roles").metaData.columnCount
        )
        assertEquals( // Users
            4,
            api.executeQuery("SELECT * FROM Users").metaData.columnCount
        )

    }

    @Test
    @DisplayName("Method \"executeUpdate\", \"executeQuery\" and \"closeConnection\" test")
    fun executionTest(){
        /* Given */
        val dbFilePath = "D:/Code/Resources/DummyPluginData.db"
        val kingdomUUID = UUID.randomUUID()
        val kingdomName = "Midgard"

        /* When */
        val api = KingdomsAPI(dbFilePath)

        /* Then */
        // executeUpdate test
        api.executeUpdate("INSERT INTO Kingdoms VALUES ('$kingdomUUID', '$kingdomName')")

        // executeQuery test
        val query = api.executeQuery("SELECT * FROM Kingdoms WHERE kingdom_name='$kingdomName'")
        query.next()
        assertEquals(kingdomUUID.toString(), query.getString("kingdom_uuid"))
        assertEquals(kingdomName, query.getString("kingdom_name"))

        // closeConnection test
        api.closeConnection()
        assertTrue(api.isClosed())
    }

}