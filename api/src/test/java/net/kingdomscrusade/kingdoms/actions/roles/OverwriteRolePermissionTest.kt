package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.TestCommons
import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.types.Permissions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class OverwriteRolePermissionTest : TestCommons() {
    @Test
    fun `Role permission overwrite test` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val rName = "Developer"
        val rPerm = setOf(Permissions.INTERACT, Permissions.MANAGE)
        val rPermOw = setOf(Permissions.ADMIN, Permissions.BUILD)
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$rName', '$kUUID', '${Commons().permissionToString(rPerm)}')")
        // When
        testAPI.execute(OverwriteRolePermission(rName, kName, rPermOw))
        // Then
        val query = testStatement.executeQuery("SELECT role_permissions FROM Roles WHERE role_uuid = '$rUUID'")
        val qPerm =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rUUID'.")
        print(qPerm)
        assertEquals(rPermOw, Commons().stringToPermissions(qPerm))
    }
    @AfterAll
    fun `Test clean up` () {
        restoreData(testStatement)
    }

}