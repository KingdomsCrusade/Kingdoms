package net.kingdomscrusade.kingdoms

import java.util.*

class Actions(override val api: IDatabase): IActions {

    /* Low-level write operations */
    private val addKingdomStatement = api.prepareStatement("INSERT INTO Kingdoms VALUES(?, ?);")
    override fun addKingdom(kingdomUUID: UUID, kingdomName: String) {
        addKingdomStatement.setString(1, kingdomUUID.toString())
        addKingdomStatement.setString(2, kingdomName)
        addKingdomStatement.executeUpdate()
    }

    private val removeKingdomStatement = api.prepareStatement("DELETE FROM Kingdoms WHERE kingdom_uuid = ?;")
    override fun removeKingdom(kingdomUUID: UUID) {
        removeKingdomStatement.setString(1, kingdomUUID.toString())
        removeKingdomStatement.executeUpdate()
    }

    private val addUserStatement = api.prepareStatement("INSERT INTO Users VALUES(?, ?, ?, ?);")
    override fun addUser(userUUID: UUID, userName: String, userKingdom: UUID, userKingdomRole: UUID?) {
        addUserStatement.setString(1, userUUID.toString())
        addUserStatement.setString(2, userName)
        addUserStatement.setString(3, userKingdom.toString())
        addUserStatement.setString(4, userKingdomRole.toString())
        addUserStatement.executeUpdate()
    }

    private val removeUserStatement = api.prepareStatement("DELETE FROM Kingdoms WHERE user_uuid = ?;")
    override fun removeUser(userUUID: UUID) {
        removeUserStatement.setString(1, userUUID.toString())
        removeUserStatement.executeUpdate()
    }

    private val addRoleStatement = api.prepareStatement("INSERT INTO Roles VALUES(?, ?, ?);")
    override fun addRole(roleUUID: UUID, roleName: String, roleKingdom: UUID) {
        addRoleStatement.setString(1, roleUUID.toString())
        addRoleStatement.setString(2, roleName)
        addRoleStatement.setString(3, roleKingdom.toString())
        addRoleStatement.executeUpdate()
    }

    private val removeRoleStatement = api.prepareStatement("DELETE FROM Roles WHERE role_uuid = ?;")
    override fun removeRole(roleUUID: UUID) {
        removeRoleStatement.setString(1, roleUUID.toString())
        removeRoleStatement.executeUpdate()
    }

    private val addPermissionStatement = api.prepareStatement("INSERT INTO Permissions (permission_flag, permission_role) VALUES(?, ?);")
    override fun addPermission(permissionFlag: String, permissionRole: UUID) {
        addPermissionStatement.setString(1, permissionFlag)
        addPermissionStatement.setString(2, permissionRole.toString())
        addPermissionStatement.executeUpdate()
    }

    private val removePermissionStatement = api.prepareStatement("DELETE FROM Permissions WHERE permission_id = ?;")
    override fun removePermission(permissionID: Int) {
        removePermissionStatement.setInt(1, permissionID)
        removePermissionStatement.executeUpdate()
    }

    /* Low level read operations */
    override fun readKingdom(kingdomUUID: UUID) {
        TODO("Not yet implemented")
    }

    override fun readKingdom(kingdomName: String) {
        TODO("Not yet implemented")
    }

    override fun readUser(userUUID: UUID) {
        TODO("Not yet implemented")
    }

    override fun readUser(userName: String) {
        TODO("Not yet implemented")
    }

    override fun readRole(roleUUID: UUID) {
        TODO("Not yet implemented")
    }

    override fun readRole(roleName: String, roleKingdom: UUID) {
        TODO("Not yet implemented")
    }

    override fun readPermission(permissionID: Int) {
        TODO("Not yet implemented")
    }

    override fun readPermission(permissionFlag: String, permissionRole: UUID) {
        TODO("Not yet implemented")
    }
}
