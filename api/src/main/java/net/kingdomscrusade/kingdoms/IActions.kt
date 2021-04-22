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

    /* Low level read operations */
    /**
     * Read data from Kingdoms with UUID.
     *
     * @param[kingdomUUID] The UUID of the kingdom
     */
    fun readKingdom(
        kingdomUUID: UUID
    )

    /**
     * Read data from Kingdoms with name.
     *
     * @param[kingdomName] The name of the kingdom
     */
    fun readKingdom(
        kingdomName: String
    )

    /**
     * Read data from Users with UUID.
     *
     * @param[userUUID] The UUID of the user
     */
    fun readUser(
        userUUID: UUID
    )

    /**
     * Read data from Users with name.
     *
     * @param[userName] The name of the user
     */
    fun readUser(
        userName: String
    )

    /**
     * Read data from Roles with UUID.
     *
     * @param[roleUUID] The UUID of the role
     */
    fun readRole(
        roleUUID: UUID
    )

    /**
     * Read data from Roles with name and kingdom.
     *
     * @param[roleName] The name of the role
     * @param[roleKingdom] The name of the role kingdom
     */
    fun readRole(
        roleName: String,
        roleKingdom: UUID
    )

    /**
     * Read data from Permissions with ID.
     *
     * @param[permissionID] The ID of the permission
     */
    fun readPermission(
        permissionID: Int
    )

    /**
     * Read data from Permissions with flag and role.
     *
     * @param[permissionFlag] The permission flag
     * @param[permissionRole] The role of the permission
     */
    fun readPermission(
        permissionFlag: String,
        permissionRole: UUID
    )

}
