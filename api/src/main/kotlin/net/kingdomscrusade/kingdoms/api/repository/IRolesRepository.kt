package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.RoleModel
import java.util.*

interface IRolesRepository {

    fun create (_obj : RoleModel)

    fun readById (_id : UUID) : List<RoleModel>
    fun readByName (_name : String, _kingdom: UUID?) : List<RoleModel>

    fun replaceById(_targetId: UUID, _obj: RoleModel)
    fun replaceByName(_targetName: String, _targetRoleKingdom: UUID?, _obj: RoleModel)

    fun deleteById (_id : UUID)
    fun deleteByName (_name : String, _kingdom: UUID?)

}