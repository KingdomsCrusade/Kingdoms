package net.kingdomscrusade.kingdoms.api.service

import net.kingdomscrusade.kingdoms.api.model.User
import java.util.*

interface IUsersService {
    fun create (_id : UUID, _name : String, _kingdom : UUID, _role : UUID?)

    fun readById (_id: UUID) : List<User>
    fun readByName (_name: String, _kingdom: UUID) : List<User>

    fun updateById (
        _targetId : UUID,
        _name: String?,
        _kingdom: UUID?,
        _role: UUID?
    )
    fun updateByName (
        _targetName : String,
        _targetUserKingdom : UUID,
        _name: String?,
        _kingdom: UUID?,
        _role: UUID?
    )

    fun deleteById (_id: UUID)
    fun deleteByName (_name: String, _kingdom: UUID)

}