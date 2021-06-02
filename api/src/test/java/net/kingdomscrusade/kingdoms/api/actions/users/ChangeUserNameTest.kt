package net.kingdomscrusade.kingdoms.api.actions.users

import net.kingdomscrusade.kingdoms.api.KingdomsAPI
import net.kingdomscrusade.kingdoms.api.TestCommons
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class ChangeUserNameTest : TestCommons() {
    @Test
    fun `Change user name` () {
        // Given
        val uUUID = UUID.randomUUID()
        val oldName = "DarkGeneral"
        val newName = "DarkGen"
        val kUUID = UUID.randomUUID()
        val kName = "Elvander"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Users VALUES ('$uUUID', '$oldName', '$kUUID', '${KingdomsAPI.visitor}')")
        // When
        val uuid = testAPI.execute(ChangeUserName(uUUID, newName))
        // Then
        val query = testStatement.executeQuery("SELECT user_uuid FROM Users WHERE user_name = '$newName'")
        val qUUID =
            if (query.next())
                query.getString("user_uuid")
            else
                fail("No user found with variable 'newName'.")
        assertEquals(uuid, qUUID)
        assertFalse(exists("Users", "user_name", oldName, testStatement))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}