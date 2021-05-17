package net.kingdomscrusade.kingdoms

import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class KingdomsAPI(url: String, usr: String, pwd: String) {

    private val database: Connection = DriverManager.getConnection(url, usr, pwd)
    val statement: Statement = database.createStatement()

    init {
        databaseInit()
    }

    // Default Role UUIDs
    companion object {
        const val ownerUUID =   "66e00734-bde4-43c0-a426-46b79075cbb1"
        const val memberUUID =  "66e00734-bde4-43c0-a426-46b79075cbb2"
        const val visitorUUID = "66e00734-bde4-43c0-a426-46b79075cbb3"
    }

    fun execute(action: IAction): String = action.execute(statement)

    fun isConnected(): Boolean = !database.isClosed

    // Initialization
    fun databaseInit() {
        tablesInit()
        rolesInit()
    }

    private fun tablesInit(){
        // TODO Implement database upgrade: https://stackoverflow.com/questions/30832215/kotlin-when-statement-vs-java-switch
        statement.executeUpdate("set foreign_key_checks = 0;")
        statement.executeUpdate("create or replace table Kingdoms ( kingdom_uuid char(36) not null primary key, kingdom_name char(12) not null unique );")
        statement.executeUpdate("create or replace index kingdom_name on Kingdoms (kingdom_name);")
        statement.executeUpdate("create or replace table Roles ( role_uuid char(36) not null primary key, role_name char(12) not null, role_kingdom char(36) references Kingdoms (kingdom_uuid) on update cascade on delete cascade, role_permissions set( 'ADMIN', 'MANAGE', 'PICK', 'CONTAINER', 'INTERACT', 'BUILD', 'KILL', 'TALK' ) );")
        statement.executeUpdate("create or replace index role_kingdom on Roles (role_kingdom);")
        statement.executeUpdate("create or replace table Users ( user_uuid char(36) not null primary key, user_name char(16) not null unique, user_kingdom char(36) not null references Kingdoms (kingdom_uuid) on update cascade on delete cascade, user_role char(36) references Roles (role_uuid) on update cascade on delete set null );")
        statement.executeUpdate("create or replace index user_kingdom on Users (user_kingdom);")
        statement.executeUpdate("create or replace unique index user_search on Users (user_uuid, user_name);")
        statement.executeUpdate("create or replace table PluginInfo ( _key enum('version_number') not null unique, _value char not null );")
        statement.executeUpdate("create or replace unique index info_key on PluginInfo (_key);")
        statement.executeUpdate("set foreign_key_checks = 1;")
    }

    private fun rolesInit(){
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