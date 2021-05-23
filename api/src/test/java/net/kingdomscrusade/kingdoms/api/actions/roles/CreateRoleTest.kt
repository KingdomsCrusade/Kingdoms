package net.kingdomscrusade.kingdoms.api.actions.roles

import net.kingdomscrusade.kingdoms.api.TestCommons
import net.kingdomscrusade.kingdoms.api.actions.Commons
import net.kingdomscrusade.kingdoms.api.types.Permissions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class CreateRoleTest : TestCommons() {
    @Test
    fun `Role creation test` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rName = "Developer"
        val rPerm = setOf(Permissions.INTERACT, Permissions.MANAGE)
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        // When
        val uuid = testAPI.execute(CreateRole(roleName = rName, roleKingdom = kName, rolePermissions = rPerm))
        // Then
        val query = testStatement.executeQuery(
            """
                SELECT role_permissions FROM Roles 
                WHERE role_name = '$rName' AND role_kingdom = '$kUUID'
                """
        )
        val qPerm =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rName' and 'kUUID'.")
        assertTrue(exists("Roles", "role_uuid", uuid, testStatement))
        assertEquals(rPerm, Commons().stringToPermissions(qPerm))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}