package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.TestCommons
import net.kingdomscrusade.kingdoms.actions.kingdoms.CreateKingdom
import net.kingdomscrusade.kingdoms.actions.roles.CreateRole
import net.kingdomscrusade.kingdoms.data.Permissions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommonsTest: TestCommons() {

    // TODO Convert all testAPI.execute to use statement.executeUpdate

    private val commons = Commons()

    @Test
    fun getGetKingdomUUID() {
        val uuid = testAPI.execute(CreateKingdom(kingdomName = "Midgard", userUUID = UUID.randomUUID(), userName = "Toger8Don"))
        assertEquals(uuid, commons.getKingdomUUID("Midgard", testStatement))
    }

    @Test
    fun getGetRoleUUID() {
        val uuid = testAPI.execute(
            CreateRole(
                roleName = "Soldier",
                roleKingdom = "Midgard",
                rolePermissions = setOf(
                    Permissions.BUILD,
                    Permissions.CONTAINER
                )
            )
        )
        assertEquals(uuid, commons.getRoleUUID("Soldier", "Midgard", testStatement))
    }

    @Test
    fun getCheckRoleDuplicate() {
        assertEquals(true, commons.checkRoleDuplicate("Soldier", "Midgard", testStatement))
    }

    @Test
    fun getPermissionsToCleanString() {
        assertEquals("ADMIN,CONTAINER", commons.permissionsToCleanString(setOf(Permissions.ADMIN, Permissions.CONTAINER)))
    }

    @Test
    fun getGetPermissions() {
        assertEquals("BUILD,CONTAINER", commons.getPermissions(commons.getRoleUUID("Soldier", "Midgard", testStatement).get(), testStatement))
    }


    @Test
    @AfterAll
    @DisplayName("Test clean up")
    fun cleanUp(){
        restoreData(testStatement)
    }

}