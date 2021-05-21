package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.TestCommons
import net.kingdomscrusade.kingdoms.types.Permissions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommonsTest: TestCommons() {

    // TODO Convert all testAPI.execute to use statement.executeUpdate

    private val commons = Commons()

    @Test
    fun getGetKingdomUUID() {
        // Given
        val uuid = UUID.randomUUID()
        val name = "Midgard"
        // When
        testStatement.executeUpdate("INSERT INTO Kingdoms VALUES ('$uuid', '$name')")
        // Then
        assertEquals(uuid, commons.getKingdomUUID("Midgard", testStatement))
    }

    @Test
    fun getGetRoleUUID() {
        // When
        testStatement.executeUpdate("INSERT INTO Roles VALUES ()")
    }

    @Test
    fun getCheckRoleDuplicate() {
        assertEquals(true, commons.checkRoleDuplicate("Soldier", "Midgard", testStatement))
    }

    @Test
    fun getPermissionsToCleanString() {
        assertEquals("ADMIN,CONTAINER", commons.permissionToString(setOf(Permissions.ADMIN, Permissions.CONTAINER)))
    }

    @Test
    fun getGetPermissions() {
        assertEquals("BUILD,CONTAINER", commons.getPermissions(commons.getRoleUUID("Soldier", "Midgard", testStatement).get(), testStatement))
    }


    @AfterAll
    fun cleanUp(){
        restoreData(testStatement)
    }

}