package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*

interface IRolesRepository {

    fun create (_id : UUID, _name : String, _permissions : Set<PermissionType>, _kingdom : UUID?)

    fun readById (_id : UUID) : List<Role>
    fun readByName (_name : String, _kingdom: UUID?) : List<Role>

    fun updateById (
        _targetId: UUID,
        _name : String?,
        _permissions : Set<PermissionType>?,
        _kingdom : UUID?
    )
    fun updateByName (
        _targetName : String,
        _targetRoleKingdom : UUID?,
        _name : String?,
        _permissions : Set<PermissionType>?,
        _kingdom : UUID?
    )

    fun deleteById (_id : UUID)
    fun deleteByName (_name : String, _kingdom: UUID?)

}