package net.kingdomscrusade.kingdoms.actions.kingdoms

import net.kingdomscrusade.kingdoms.TestCommons
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}