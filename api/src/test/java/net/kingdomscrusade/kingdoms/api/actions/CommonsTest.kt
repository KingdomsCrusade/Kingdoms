package net.kingdomscrusade.kingdoms.api.actions

import net.kingdomscrusade.kingdoms.api.KingdomsAPI
import net.kingdomscrusade.kingdoms.api.TestCommons
import net.kingdomscrusade.kingdoms.api.types.Permissions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.NoSuchElementException

internal class CommonsTest : TestCommons() {

    private val c = Commons()

    @Test
    fun `getKingdomUUID test - Kingdom exists`() {
        // Given
        val uuid = UUID.randomUUID()
        val name = "Alien"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$uuid', '$name')")
        // When
        val funReturn = c.getKingdomUUID(name, testStatement)
        // Then
        assertEquals(uuid, funReturn.get())
    }

    @Test
    fun `getKingdomUUID test - Kingdom not exists`() {
        // Given
        val name = "Alien"
        // When
        val funReturn = c.getKingdomUUID(name, testStatement)
        // Then
        assertThrows(NoSuchElementException::class.java) { funReturn.get() }
    }

    @Test
    fun `getRoleUUID test - Default roles`() {
        // Given
        // When
        val owner = c.getRoleUUID("Owner", "Whatever", testStatement)
        val member = c.getRoleUUID("Member", "Whatever", testStatement)
        val visitor = c.getRoleUUID("Visitor", "Whatever", testStatement)
        // Then
        assertEquals(KingdomsAPI.ownerUUID, owner.get().toString())
        assertEquals(KingdomsAPI.memberUUID, member.get().toString())
        assertEquals(KingdomsAPI.visitorUUID, visitor.get().toString())
    }

    @Test
    fun `getRoleUUID test - Role exists`() {
        // Given
        val kUUID = UUID.randomUUID()
        val kName = "Amazon"
        val rUUID = UUID.randomUUID()
        val rName = "Council"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUE ('$rUUID', '$rName', '$kUUID', 'ADMIN,MANAGE')")
        // When
        val funReturn = c.getRoleUUID(rName, kName, testStatement)
        // Then
        assertEquals(rUUID, funReturn.get())
    }

    @Test
    fun `getRoleUUID test - Role not exists`() {
        // When
        val funReturn = c.getRoleUUID("Council", "Amazon", testStatement)
        // Then
        assertThrows(NoSuchElementException::class.java){ funReturn.get() }
    }

    @Test
    fun `checkRoleDuplicate test - True`() {
        // Given
        val rName = "Developer"
        val kName = "Eclipse"
        val kUUID = UUID.randomUUID()
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUE  ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUE ('${UUID.randomUUID()}', '$rName', '$kUUID', 'ADMIN')")
        // When
        val funReturn = c.checkRoleDuplicate(rName, kName, testStatement)
        // Then
        assertTrue(funReturn)
    }

    @Test
    fun `checkRoleDuplicate test - False`() {
        // When
        val funReturn = c.checkRoleDuplicate("Developer", "Eclipse", testStatement)
        // Then
        assertFalse(funReturn)
    }

    @Test
    fun `permissionToString test - Multiple value`() {
        // Given
        val parameter = setOf(Permissions.ADMIN, Permissions.BUILD, Permissions.CONTAINER)
        val expected = "ADMIN,BUILD,CONTAINER"
        // When
        val funReturn = c.permissionToString(parameter)
        print(funReturn)
        // Then
        assertEquals(expected, funReturn)
    }

    @Test
    fun `permissionToString test - Single value`() {
        // Given
        val parameter = setOf(Permissions.ADMIN)
        val expected = "ADMIN"
        // When
        val funReturn = c.permissionToString(parameter)
        print(funReturn)
        // Then
        assertEquals(expected, funReturn)
    }

    @Test
    fun `stringToPermission test - Multiple Value`() {
        // Given
        val expected = setOf(Permissions.ADMIN, Permissions.BUILD, Permissions.CONTAINER)
        val parameter = "ADMIN,BUILD,CONTAINER"
        // When
        val funReturn = c.stringToPermissions(parameter)
        print(funReturn)
        // Then
        assertEquals(expected, funReturn)
    }

    @Test
    fun `stringToPermission test - Single Value`() {
        // Given
        val expected = setOf(Permissions.ADMIN)
        val parameter = "ADMIN"
        // When
        val funReturn = c.stringToPermissions(parameter)
        print(funReturn)
        // Then
    }

    @Test
    fun `getPermissions test - Not Null` () {
        // Given
        val rUUID = UUID.randomUUID()
        val rPerm = "ADMIN,MANAGE"
        val kUUID = UUID.randomUUID()
        val kName = "Empire"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUE ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUE ('$rUUID', 'Queen', '$kUUID', '$rPerm')")
        // When
        val funResult = c.getPermissions(rUUID, testStatement)
        // Then
        assertEquals(rPerm, funResult.get())
    }

    @Test
    fun `getPermissions test - Null`() {
        // Given
        val rUUID = UUID.randomUUID()
        val rPerm = ""
        val kUUID = UUID.randomUUID()
        val kName = "Empire"
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUE ('$kUUID', '$kName')")
        testStatement.executeUpdate("INSERT INTO Roles VALUE ('$rUUID', 'Queen', '$kUUID', '$rPerm')")
        // When
        val funResult = c.getPermissions(rUUID, testStatement)
        // Then
        assertEquals(rPerm, funResult.get())
    }

    @Test
    fun `getPermissions test - Role Not Exists`() {
        // When
        val funResult = c.getPermissions(UUID.randomUUID(), testStatement)
        // Then
        assertThrows(NoSuchElementException::class.java) { funResult.get() }
    }

    @AfterEach
    fun `Test clean up` () {
        restoreData(testStatement)
    }
}