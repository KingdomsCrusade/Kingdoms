package net.kingdomscrusade.kingdoms.api.actions.kingdoms

import net.kingdomscrusade.kingdoms.api.TestCommons
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.util.*

internal class DeleteKingdomTest : TestCommons() {
    @Test
    fun `Kingdom deletion` () {
        // Given
        val kName = "Midgard"
        val kUUID = UUID.randomUUID()
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        // When
        testAPI.execute(DeleteKingdom(kName))
        // Then
        assertFalse(exists("Kingdoms", "kingdom_uuid", kUUID.toString(), testStatement))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}