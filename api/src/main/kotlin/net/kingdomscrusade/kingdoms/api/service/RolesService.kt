package net.kingdomscrusade.kingdoms.api.service

import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*

class RolesService : IRolesService{
    override fun create(_id: UUID, _name: String, _permissions: Set<PermissionType>, _kingdom: UUID?) {
        TODO("Not yet implemented")
    }

    override fun readById(_id: UUID): List<Role> {
        TODO("Not yet implemented")
    }

    override fun readByName(_name: String, _kingdom: UUID?): List<Role> {
        TODO("Not yet implemented")
    }

    override fun updateById(_targetId: UUID, _name: String?, _permissions: Set<PermissionType>?, _kingdom: UUID?) {
        TODO("Not yet implemented")
    }

    override fun updateByName(
        _targetName: String,
        _targetRoleKingdom: UUID?,
        _name: String?,
        _permissions: Set<PermissionType>?,
        _kingdom: UUID?
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteById(_id: UUID) {
        TODO("Not yet implemented")
    }

    override fun deleteByName(_name: String, _kingdom: UUID?) {
        TODO("Not yet implemented")
    }
}