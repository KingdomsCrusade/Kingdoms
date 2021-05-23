package net.kingdomscrusade.kingdoms.api.actions.roles

import net.kingdomscrusade.kingdoms.api.TestCommons
import net.kingdomscrusade.kingdoms.api.actions.Commons
import net.kingdomscrusade.kingdoms.api.types.Permissions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

internal class ChangeRoleNameTest : TestCommons() {
    @Test
    fun `Role name change test` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val oldRName = "Developer"
        val newRName = "Senior"
        val rPerm = setOf(Permissions.INTERACT, Permissions.MANAGE)
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$oldRName', '$kUUID', '${Commons().permissionToString(rPerm)}')")
        // When
        testAPI.execute(ChangeRoleName(oldRName, newRName, kName))
        // Then
        assertFalse(exists("Roles", "role_name", oldRName, testStatement))
        assertTrue(exists("Roles", "role_name", newRName, testStatement))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}