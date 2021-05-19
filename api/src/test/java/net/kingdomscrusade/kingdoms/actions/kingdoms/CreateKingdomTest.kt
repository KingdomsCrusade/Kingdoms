package net.kingdomscrusade.kingdoms.actions.kingdoms

import net.kingdomscrusade.kingdoms.TestCommons
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CreateKingdomTest: TestCommons(){
    @Test
    @DisplayName("Kingdom creation test")
    fun kingdomCreationTest(){
        val uuid = testAPI.execute(CreateKingdom(kingdomName = "England", userUUID = UUID.randomUUID(), userName = "RoyalHighness"))
        assertTrue(exists("Kingdoms", "kingdom_uuid", uuid, testStatement))
    }
    @AfterAll
    fun cleanUp(){
        restoreData(testStatement)
    }
}