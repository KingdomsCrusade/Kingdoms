package net.kingdomscrusade.kingdoms.api.repository

import java.util.*

interface IUsersRepository {

    fun create (uuid : UUID, name : String, kingdom : UUID, role : UUID?)

    fun readById (uuid: UUID)
    fun readByName (name: String, kingdom: UUID)

    fun updateById (targetUUID : UUID, name: String?, kingdom: UUID?, role: UUID?)
    fun updateByName (targetName : String, targetUserKingdom : UUID, name: String?, kingdom: UUID?, role: UUID?)

    fun deleteById (uuid: UUID)
    fun deleteByName (name: String, kingdom: UUID)

}