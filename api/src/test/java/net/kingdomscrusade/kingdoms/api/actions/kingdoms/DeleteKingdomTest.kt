package net.kingdomscrusade.kingdoms.api.actions.kingdoms

import net.kingdomscrusade.kingdoms.api.TestCommons
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
        val kName = "Midgard"
        val kUUID = UUID.randomUUID()
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        // When
        testAPI.execute(DeleteKingdom(kName))
        // Then
        assertFalse(exists("Kingdoms", "kingdom_uuid", kUUID.toString(), testStatement))
    }
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}