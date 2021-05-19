package net.kingdomscrusade.kingdoms.actions.users

import net.kingdomscrusade.kingdoms.KingdomsAPI
import net.kingdomscrusade.kingdoms.TestCommons
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DeleteUserTest : TestCommons() {
    @Test
    fun `User deletion` () {
        // Given
        val uUUID = UUID.randomUUID()
        val uName = "_NARD190"
        val kUUID = UUID.randomUUID()
        val kName = "Auraxis"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Users VALUES ('$uUUID', '$uName', '$kUUID', '${KingdomsAPI.visitorUUID}')")
        // When
        testAPI.execute(DeleteUser(uUUID, kName))
        // Then
        assertFalse(exists("Users", "user_uuid", uUUID.toString(), testStatement))
    }
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}