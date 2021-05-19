package net.kingdomscrusade.kingdoms.actions.users

import net.kingdomscrusade.kingdoms.TestCommons
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AddUserTest : TestCommons() {
    @Test
    fun `User addition` () {
        // Given
        val uUUID = UUID.randomUUID()
        val uName = "Vortex_Rayquaza"
        val kUUID = UUID.randomUUID()
        val kName = "PickleLand"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName');")
        // When
        testAPI.execute(AddUser(uUUID, uName, kName, "Visitor"))
        // Then
        assertTrue(exists("Users", "user_uuid", uUUID.toString(), testStatement))
    }
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}