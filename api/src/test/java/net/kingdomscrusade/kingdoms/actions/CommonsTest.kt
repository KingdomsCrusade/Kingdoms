package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.KingdomsAPI
import net.kingdomscrusade.kingdoms.actions.kingdoms.CreateKingdom
import net.kingdomscrusade.kingdoms.actions.roles.CreateRole
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import net.kingdomscrusade.kingdoms.data.Permissions
import java.util.*

internal class CommonsTest {

    // TODO Finish all action tests
    /*
    Requires table initialization before starting test.
     */

    private val testAPI: KingdomsAPI= KingdomsAPI(
        url = "jdbc:mariadb://dev.kingdomscrusade.net:33061/test",
        usr = "root",
        pwd = "test"
    )
    private val testStatement = testAPI.statement
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
}