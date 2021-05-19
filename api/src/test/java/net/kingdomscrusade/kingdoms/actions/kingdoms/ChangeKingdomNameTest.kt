package net.kingdomscrusade.kingdoms.actions.kingdoms

import net.kingdomscrusade.kingdoms.TestCommons
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        val quuid =
            if (query.next())
                query.getString("kingdom_uuid")
            else
                fail("No kingdom found with variable 'newName'.")
        assertEquals(uuid, quuid)
        assertFalse(exists("Kingdoms", "kingdom_name", oldName, testStatement))
    }
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}