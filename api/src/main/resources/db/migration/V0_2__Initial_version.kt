@file:Suppress("ClassName", "unused", "MemberVisibilityCanBePrivate", "SqlNoDataSourceInspection", "SqlResolve")

package db.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class V0_2__Initial_version: BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            // Create tables
            SchemaUtils.create(Kingdoms, Roles, Permissions, Users)
            // Insert Default Roles
            for (defaultRole in defaultRoles)
                Roles.insert {
                    it[name] = defaultRole.key
                    it[id] = defaultRole.value
                }
            // Insert default permissions
            defaultPermissions.forEach { (u, ps) ->
                ps.forEach { p ->
                    Permissions.insert {
                        it[ref] = u
                        it[value] = p
                    }
                }
            }
        }
    }
}

// Tables
object Kingdoms : Table() {
    val id = uuid("kingdom_id")
    val name = varchar("kingdom_name", 255).uniqueIndex()
    override val primaryKey = PrimaryKey(id)
}
object Roles : Table() {
    val id = uuid("role_id")
    val name = varchar("role_name", 255)
    val kingdom = reference( "role_kingdom", Kingdoms.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE ).nullable()
    override val primaryKey = PrimaryKey(id)
}
object Permissions : Table() {
    val allPermissions : () -> String = { val builder = StringBuilder(); PermissionType.values().forEachIndexed { index, permissions -> if (index != 0) builder.append(","); builder.append("\"$permissions\""); }; builder.toString() }
    val ref = reference("permission_ref", Roles.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val value = customEnumeration( "permission_val", "ENUM(${allPermissions()})", { value -> PermissionType.valueOf(value as String) }, { it.name } )
}
object Users : Table() {
    val uuid = uuid("user_uuid")
    val name = varchar( "user_name", 16 ).uniqueIndex()
    val kingdom = reference( "user_kingdom", Kingdoms.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE )
    val role = reference( "user_role", Roles.id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE ).nullable()
    override val primaryKey = PrimaryKey(uuid)
}
enum class PermissionType {
    ADMIN,
    MANAGE,
    PICK,
    CONTAINER,
    INTERACT,
    BUILD,
    KILL,
    TALK
}

// Defaults
val defaultRoles = mapOf(
    "Owner" to UUID.fromString("66e00734-bde4-43c0-a426-46b79075cbb1"),
    "Member" to UUID.fromString("66e00734-bde4-43c0-a426-46b79075cbb2"),
    "Visitor" to UUID.fromString("66e00734-bde4-43c0-a426-46b79075cbb3"),
)
val defaultPermissions : Map<UUID, List<PermissionType>> = mapOf(
    UUID.fromString("66e00734-bde4-43c0-a426-46b79075cbb1") to listOf(PermissionType.ADMIN),
    UUID.fromString("66e00734-bde4-43c0-a426-46b79075cbb2") to listOf(PermissionType.BUILD, PermissionType.CONTAINER, PermissionType.INTERACT, PermissionType.KILL, PermissionType.PICK, PermissionType.TALK),
    UUID.fromString("66e00734-bde4-43c0-a426-46b79075cbb3") to listOf(PermissionType.TALK, PermissionType.INTERACT)
)
