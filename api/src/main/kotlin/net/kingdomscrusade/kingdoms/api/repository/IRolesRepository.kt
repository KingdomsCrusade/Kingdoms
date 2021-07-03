package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.RolesModel
import java.util.*

interface IRolesRepository {

    fun create (_obj : RolesModel)

    fun readById (_id : UUID) : List<RolesModel>
    fun readByName (_name : String, _kingdom: UUID?) : List<RolesModel>

    fun replaceById(_targetId: UUID, _obj: RolesModel)
    fun replaceByName(_targetName: String, _targetRoleKingdom: UUID?, _obj: RolesModel)

    fun deleteById (_id : UUID)
    fun deleteByName (_name : String, _kingdom: UUID?)

}