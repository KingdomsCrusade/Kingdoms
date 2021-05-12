package net.kingdomscrusade.kingdoms.actions.users

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Statement
import java.util.*

class AddUser
    (
    private val userUUID: UUID,
    private val userName: String,
    private val userKingdom: String,
    private val userRole: String
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String{
        statement.executeUpdate(
            """
                INSERT INTO Users(user_uuid, user_name, user_kingdom, user_role) 
                    VALUE (
                        '$userUUID', 
                        '$userName', 
                        '${getKingdomUUID(userKingdom, statement).get()}', 
                        '${getRoleUUID(userRole, userKingdom, statement).get()}'
                    );
            """.trimIndent()
        )
        return userUUID.toString()
    }
}