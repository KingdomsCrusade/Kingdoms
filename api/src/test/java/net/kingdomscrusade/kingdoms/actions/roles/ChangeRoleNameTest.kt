package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.TestCommons
import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.types.Permissions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}