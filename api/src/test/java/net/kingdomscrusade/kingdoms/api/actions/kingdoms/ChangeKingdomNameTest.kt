package net.kingdomscrusade.kingdoms.api.actions.kingdoms

import net.kingdomscrusade.kingdoms.api.TestCommons
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class ChangeKingdomNameTest : TestCommons() {
    @Test
    fun `Kingdom name change` () {
        // Given
        val oldName = "Midgard"
        val newName = "Stardrop"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('${UUID.randomUUID()}', '$oldName');")
        // When
        val uuid = testAPI.execute(ChangeKingdomName(oldName, newName))
        // Then
        val query = testStatement.executeQuery("SELECT kingdom_uuid FROM Kingdoms WHERE kingdom_name = '$newName';")
        val qUUID =
            if (query.next())
                query.getString("kingdom_uuid")
            else
                fail("No kingdom found with variable 'newName'.")
        assertEquals(uuid, qUUID)
        assertFalse(exists("Kingdoms", "kingdom_name", oldName, testStatement))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}