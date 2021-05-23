package net.kingdomscrusade.kingdoms.api.actions.kingdoms

import net.kingdomscrusade.kingdoms.api.TestCommons
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

internal class CreateKingdomTest : TestCommons(){
    @Test
    fun `Kingdom creation` () {
        // Given
        val name = "Midgard"
        val uUUID = UUID.randomUUID()
        val uName = "Toger8Don"
        // When
        val uuid = testAPI.execute(CreateKingdom(kingdomName = name, userUUID = uUUID, userName = uName))
        // Then
        assertTrue(exists("Kingdoms", "kingdom_uuid", uuid, testStatement))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}