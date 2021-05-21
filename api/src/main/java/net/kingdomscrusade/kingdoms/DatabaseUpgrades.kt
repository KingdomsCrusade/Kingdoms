package net.kingdomscrusade.kingdoms

import java.sql.Statement

internal class DatabaseUpgrades {
    val list = arrayOf( DatabaseUpgrades::upgradeFromV0 )
    private fun upgradeFromV0(statement: Statement) {
        // Creating tables and indices
        statement.executeUpdate("create or replace table Kingdoms ( kingdom_uuid char(36) not null primary key, kingdom_name char(12) not null unique );")
        statement.executeUpdate("create or replace index kingdom_name on Kingdoms (kingdom_name);")
        statement.executeUpdate("create or replace table Roles ( role_uuid char(36) not null primary key, role_name char(12) not null, role_kingdom char(36) references Kingdoms (kingdom_uuid) on update cascade on delete cascade, role_permissions set( 'ADMIN', 'MANAGE', 'PICK', 'CONTAINER', 'INTERACT', 'BUILD', 'KILL', 'TALK' ) );")
        statement.executeUpdate("create or replace index role_kingdom on Roles (role_kingdom);")
        statement.executeUpdate("create or replace table Users ( user_uuid char(36) not null primary key, user_name char(16) not null unique, user_kingdom char(36) not null references Kingdoms (kingdom_uuid) on update cascade on delete cascade, user_role char(36) references Roles (role_uuid) on update cascade on delete set null );")
        statement.executeUpdate("create or replace index user_kingdom on Users (user_kingdom);")
        statement.executeUpdate("create or replace unique index user_search on Users (user_uuid, user_name);")
        statement.executeUpdate("create or replace table PluginInfo ( _key enum('version_number') not null unique, _value char not null );")
        statement.executeUpdate("create or replace unique index info_key on PluginInfo (_key);")

        // Initializing default variables
        statement.executeUpdate(
            """
                INSERT INTO Roles (role_uuid, role_name, role_permissions) VALUES 
                ('${KingdomsAPI.ownerUUID}',    'Owner',    ('ADMIN')),
                ('${KingdomsAPI.memberUUID}',   'Member',   ('PICK,CONTAINER,INTERACT,BUILD,KILL,TALK')),
                ('${KingdomsAPI.visitorUUID}',  'Visitor',  ('INTERACT,TALK'));
            """.trimIndent()
        )
    }
}