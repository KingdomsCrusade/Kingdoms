package net.kingdomscrusade.kingdoms

import java.util.*

interface IActions {

    val api:IDatabase

    /* Low-level write operations */
    /**
     * Adds a new row to Kingdoms.
     *
     * @param[kingdomUUID] A unique, not null UUID representing the kingdom
     * @param[kingdomName] A unique, not null name representing the kingdom
     */
    fun addKingdom(
        kingdomUUID: UUID,
        kingdomName: String
    )

    /**
     * Removes a row from Kingdoms.
     *
     * @param[kingdomUUID] The UUID of the kingdom
     */
    fun removeKingdom(
        kingdomUUID: UUID
    )

    /**
     * Adds a new row to Users.
     *
     * @param[userUUID] User's Player UUID
     * @param[userName] User's Player name
     * @param[userKingdom] The UUID of the kingdom the user is in
     * @param[userKingdomRole] The UUID of the kingdom role the user is, this parameter is nullable
     */
    fun addUser(
        userUUID: UUID,
        userName: String,
        userKingdom: UUID,
        userKingdomRole: UUID?
    )
    /**
     * Removes a row from Users.
     *
     * @param[userUUID] The user's Player UUID
     */
    fun removeUser(
        userUUID: UUID
    )

    /**
     * Adds a new row to Rows.
     *
     * @param[roleUUID] A unique, not null UUID representing the role
     * @param[roleName] A not null name representing the role
     * @param[roleKingdom] The UUID of the kingdom the role is of
     */
    fun addRole(
        roleUUID: UUID,
        roleName: String,
        roleKingdom: UUID
    )
    /**
     * Removes a row from Rows.
     *
     * @param[roleUUID] The UUID of the role
     */
    fun removeRole(
        roleUUID: UUID
    )

    /**
     * Adds a new row to Permissions.
     *
     * @param[permissionFlag] A not null flag name
     * @param[permissionRole] The UUID of the role the permission data is from
     */
    fun addPermission(
        /* permission_id is auto generated */
        permissionFlag: String,
        permissionRole: UUID
    )
    /**
     * Removes a row from Permissions.
     *
     * @param[permissionID] The ID of the permission data
     */
    fun removePermission(
        permissionID: Int
    )
}
