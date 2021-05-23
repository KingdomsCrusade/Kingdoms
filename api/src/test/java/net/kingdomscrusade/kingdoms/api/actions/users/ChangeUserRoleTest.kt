package net.kingdomscrusade.kingdoms.api.actions.users

import net.kingdomscrusade.kingdoms.api.KingdomsAPI
import net.kingdomscrusade.kingdoms.api.TestCommons
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.util.*

internal class ChangeUserRoleTest : TestCommons() {
    @Test
    fun `Change user role` () {
        // Given
        val uUUID = UUID.randomUUID()
        val uName = "MrRazamataz"
        val kUUID = UUID.randomUUID()
        val kName = "AlienEmpire"
        val oldRoleUUID = KingdomsAPI.visitorUUID
        val newRole = "Member"
        val newRoleUUID = KingdomsAPI.memberUUID
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Users VALUES ('$uUUID', '$uName', '$kUUID', '$oldRoleUUID')")
        // When
        testAPI.execute(ChangeUserRole(uUUID, kName, newRole))
        // Then
        val query = testStatement.executeQuery("SELECT user_role FROM Users WHERE user_uuid = '$uUUID'")
        val qRole =
            if (query.next())
                query.getString("user_role")
            else
                fail("No user found with variable 'uUUID'.")
        assertEquals(newRoleUUID, qRole)
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}