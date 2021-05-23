package net.kingdomscrusade.kingdoms.api.actions.users

import net.kingdomscrusade.kingdoms.api.TestCommons
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

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
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}