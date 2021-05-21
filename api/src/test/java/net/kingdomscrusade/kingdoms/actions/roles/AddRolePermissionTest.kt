package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.TestCommons
import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.types.Permissions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.util.*

internal class AddRolePermissionTest : TestCommons() {
    @Test
    fun `Test 1 - Not Null ` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val rName = "Developer"
        val rPerm = setOf(Permissions.INTERACT, Permissions.MANAGE)
        val rPermAdd = Permissions.ADMIN
        val rPermExpect = setOf(Permissions.INTERACT, Permissions.MANAGE, Permissions.ADMIN)
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$rName', '$kUUID', '${Commons().permissionToString(rPerm)}')")
        // When
        testAPI.execute(AddRolePermission(rName, kName, rPermAdd))
        // Then
        val query = testStatement.executeQuery("SELECT role_permissions FROM Roles WHERE role_uuid = '$rUUID'")
        val qPerm =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rUUID'.")
        print(qPerm)
        assertEquals(rPermExpect, Commons().stringToPermissions(qPerm))
    }
    @Test
    fun `Test 2 - Null ` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val rName = "Developer"
        val rPermAdd = Permissions.ADMIN
        val rPermExpect = setOf(Permissions.ADMIN)
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$rName', '$kUUID', NULL)")
        // When
        testAPI.execute(AddRolePermission(rName, kName, rPermAdd))
        // Then
        val query = testStatement.executeQuery("SELECT role_permissions FROM Roles WHERE role_uuid = '$rUUID'")
        val qPerm =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rUUID'.")
        print(qPerm)
        assertEquals(rPermExpect, Commons().stringToPermissions(qPerm))
    }
    @Test
    fun `Test 3 - Duplicated Value ` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val rName = "Developer"
        val rPerm = setOf(Permissions.ADMIN)
        val rPermAdd = Permissions.ADMIN
        val rPermExpect = setOf(Permissions.ADMIN)
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$rName', '$kUUID', '${Commons().permissionToString(rPerm)}')")
        // When
        testAPI.execute(AddRolePermission(rName, kName, rPermAdd))
        // Then
        val query = testStatement.executeQuery("SELECT role_permissions FROM Roles WHERE role_uuid = '$rUUID'")
        val qPerm =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rUUID'.")
        print(qPerm)
        assertEquals(rPermExpect, Commons().stringToPermissions(qPerm))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}