package net.kingdomscrusade.kingdoms.api.service

import net.kingdomscrusade.kingdoms.api.model.User
import java.util.*

class UsersService : IUsersService{
    override fun create(_id: UUID, _name: String, _kingdom: UUID, _role: UUID?) {
        TODO("Not yet implemented")
    }

    override fun readById(_id: UUID): List<User> {
        TODO("Not yet implemented")
    }

    override fun readByName(_name: String, _kingdom: UUID): List<User> {
        TODO("Not yet implemented")
    }

    override fun updateById(_targetId: UUID, _name: String?, _kingdom: UUID?, _role: UUID?) {
        TODO("Not yet implemented")
    }

    override fun updateByName(
        _targetName: String,
        _targetUserKingdom: UUID,
        _name: String?,
        _kingdom: UUID?,
        _role: UUID?
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteById(_id: UUID) {
        TODO("Not yet implemented")
    }

    override fun deleteByName(_name: String, _kingdom: UUID) {
        TODO("Not yet implemented")
    }
}