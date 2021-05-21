package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.TestCommons
import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.types.Permissions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class RemoveRolePermissionTest : TestCommons() {
    @Test
    fun `Test 1 - Multiple Values` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val rName = "Developer"
        val rPerm = setOf(Permissions.INTERACT, Permissions.MANAGE)
        val rPermDel = Permissions.INTERACT
        val rPermExpect = setOf(Permissions.MANAGE)
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$rName', '$kUUID', '${Commons().permissionToString(rPerm)}')")
        // When
        testAPI.execute(RemoveRolePermission(rName, kName, rPermDel))
        // Then
        val query = testStatement.executeQuery("SELECT role_permissions FROM Roles WHERE role_uuid = '$rUUID'")
        val qPerms =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rUUID'.")
        print(qPerms)
        assertEquals(rPermExpect, Commons().stringToPermissions(qPerms))
    }
    @Test
    fun `Test 2 - Single Value` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val rName = "Developer"
        val rPerm = setOf(Permissions.INTERACT)
        val rPermDel = Permissions.INTERACT
        val rPermExpect = setOf<Permissions>()
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$rName', '$kUUID', '${Commons().permissionToString(rPerm)}')")
        // When
        testAPI.execute(RemoveRolePermission(rName, kName, rPermDel))
        // Then
        val query = testStatement.executeQuery("SELECT role_permissions FROM Roles WHERE role_uuid = '$rUUID'")
        val qPerms =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rUUID'.")
        print(qPerms)
        assertEquals(rPermExpect, Commons().stringToPermissions(qPerms))
    }
    @Test
    fun `Test 3 - Null` () {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Ravenshold"
        val rUUID = UUID.randomUUID()
        val rName = "Developer"
        val rPerm = setOf<Permissions>()
        val rPermDel = Permissions.INTERACT
        val rPermExpect = setOf<Permissions>()
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUES ('$rUUID', '$rName', '$kUUID', '${Commons().permissionToString(rPerm)}')")
        // When
        testAPI.execute(RemoveRolePermission(rName, kName, rPermDel))
        // Then
        val query = testStatement.executeQuery("SELECT role_permissions FROM Roles WHERE role_uuid = '$rUUID'")
        val qPerms =
            if (query.next())
                query.getString("role_permissions")
            else
                fail("No role found with variable 'rUUID'.")
        print(qPerms)
        assertEquals(rPermExpect, Commons().stringToPermissions(qPerms))
    }
    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}