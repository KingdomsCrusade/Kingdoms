package net.kingdomscrusade.kingdoms.actions.kingdoms

import net.kingdomscrusade.kingdoms.TestCommons
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DeleteKingdomTest : TestCommons() {
    @Test
    fun `Kingdom deletion` () {
        // Given
        val name = "Midgard"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('${UUID.randomUUID()}', '$name')")
        // When
        val uuid = testAPI.execute(DeleteKingdom(name))
        // Then
        assertFalse(exists("Kingdoms", "kingdom_uuid", uuid, testStatement))
    }
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}