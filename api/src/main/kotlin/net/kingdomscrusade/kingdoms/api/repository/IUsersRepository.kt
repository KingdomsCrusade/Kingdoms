package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.UserModel
import java.util.*

interface IUsersRepository {

    fun create(_obj : UserModel)

    fun readById(_id: UUID, _kingdom: UUID): List<UserModel>
    fun readByName(_name: String, _kingdom: UUID): List<UserModel>

    fun replaceById(_targetId: UUID, _targetUserKingdom: UUID, _obj: UserModel)
    fun replaceByName(_targetName: String, _targetUserKingdom: UUID, _obj: UserModel)

    fun deleteById (_id: UUID, _kingdom: UUID)
    fun deleteByName (_name: String, _kingdom: UUID)

}