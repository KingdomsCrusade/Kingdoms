package net.kingdomscrusade.kingdoms

import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.DriverManager

class KingdomsAPI(url: String, usr: String, pwd: String) {

    private val db = DriverManager.getConnection(url, usr, pwd)

    fun defaultRoleInit(){
        val ownerUUID =     "17717970-5202-6900-2426-000000000001"
        val memberUUID =    "17717970-5202-6900-2426-000000000002"
        val visitorUUID =   "17717970-5202-6900-2426-000000000003"
        db.createStatement().execute(
            """
                INSERT INTO Roles (role_uuid, role_name, role_permissions) VALUES 
                ($ownerUUID, 'Owner', 
                    ('ADMIN')
                ),
                ($memberUUID, 'Member', 
                    ('PICK', 'CONTAINER', 'INTERACT', 'BUILD', 'KILL', 'TALK')
                ),
                ($visitorUUID, 'Visitor', 
                    ('INTERACT', 'TALK')
                );
            """.trimIndent()
        )
    }

    fun execute(action: IAction){
        action.execute(db)
    }

}