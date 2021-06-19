package net.kingdomscrusade.kingdoms.api.service

import net.kingdomscrusade.kingdoms.api.model.Kingdom
import java.util.*

interface IKingdomsService {
    fun create (_id : UUID, _name : String)

    fun readById (_id: UUID) : List<Kingdom>
    fun readByName (_name : String) : List<Kingdom>

    fun updateById (_targetId : UUID, _name: String?)
    fun updateByName (_targetName : String, _name: String?)

    fun deleteById (_id: UUID)
    fun deleteByName (_name: String)
}