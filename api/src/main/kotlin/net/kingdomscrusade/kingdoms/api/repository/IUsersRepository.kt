package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.UsersModel
import java.util.*

interface IUsersRepository {

    fun create(_obj : UsersModel)

    fun readById(_id: UUID, _kingdom: UUID): List<UsersModel>
    fun readByName(_name: String, _kingdom: UUID): List<UsersModel>

    fun replaceById(_targetId: UUID, _targetUserKingdom: UUID, _obj: UsersModel)
    fun replaceByName(_targetName: String, _targetUserKingdom: UUID, _obj: UsersModel)

    fun deleteById (_id: UUID, _kingdom: UUID)
    fun deleteByName (_name: String, _kingdom: UUID)

}