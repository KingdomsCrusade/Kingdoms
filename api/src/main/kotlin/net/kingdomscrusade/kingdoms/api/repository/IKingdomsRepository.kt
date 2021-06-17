package net.kingdomscrusade.kingdoms.api.repository

import java.util.*

interface IKingdomsRepository {

    fun create (id : UUID, name : String)

    fun readById (id: UUID)
    fun readByName (name : String)

    fun updateById (targetId : UUID, name: String?)
    fun updateByName (targetName : String, name: String?)

    fun deleteById (id: UUID)
    fun deleteByName (name: String)

}