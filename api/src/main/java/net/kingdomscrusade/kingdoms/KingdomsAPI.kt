package net.kingdomscrusade.kingdoms

import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class KingdomsAPI(url: String, usr: String, pwd: String) {

    private val database: Connection = DriverManager.getConnection(url, usr, pwd)
    val statement: Statement = database.createStatement()

    // Default Role UUIDs
    companion object {
        const val ownerUUID =   "66e00734-bde4-43c0-a426-46b79075cbb1"
        const val memberUUID =  "66e00734-bde4-43c0-a426-46b79075cbb2"
        const val visitorUUID = "66e00734-bde4-43c0-a426-46b79075cbb3"
    }

    fun execute(action: IAction){
        action.execute(statement)
    }

    fun isConnected(): Boolean = !database.isClosed

    fun defaultRolesInit(){
        statement.executeUpdate(
            """
                INSERT INTO Roles (role_uuid, role_name, role_permissions) VALUES 
                ('$ownerUUID',    'Owner',    ('ADMIN')),
                ('$memberUUID',   'Member',   ('PICK,CONTAINER,INTERACT,BUILD,KILL,TALK')),
                ('$visitorUUID',  'Visitor',  ('INTERACT,TALK'));
            """.trimIndent()
        )
    }

}