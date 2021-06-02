package net.kingdomscrusade.kingdoms.api.actions.users

import net.kingdomscrusade.kingdoms.api.KingdomsAPI
import net.kingdomscrusade.kingdoms.api.TestCommons
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.util.*

internal class DeleteUserTest : TestCommons() {
    @Test
    fun `User deletion` () {
        // Given
        val uUUID = UUID.randomUUID()
        val uName = "_NARD190"
        val kUUID = UUID.randomUUID()
        val kName = "Auraxis"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Users VALUES ('$uUUID', '$uName', '$kUUID', '${KingdomsAPI.visitor}')")
        // When
        testAPI.execute(DeleteUser(uUUID, kName))
        // Then
        assertFalse(exists("Users", "user_uuid", uUUID.toString(), testStatement))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}