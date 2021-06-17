package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*

interface IRolesRepository {

    fun create (id : UUID, name : String, permissions : Set<PermissionType>, kingdom : UUID?)

    fun readById (id : UUID)
    fun readByName (name : String, kingdom: UUID?)

    fun updateById (targetId: UUID, name : String?, permissions : Set<PermissionType>?, kingdom : UUID?)
    fun updateByName (targetName : String, targetRoleKingdom : UUID?, name : String?, permissions : Set<PermissionType>?, kingdom : UUID?)

    fun deleteById (id : UUID)
    fun deleteByName (name : String, kingdom: UUID?)

}