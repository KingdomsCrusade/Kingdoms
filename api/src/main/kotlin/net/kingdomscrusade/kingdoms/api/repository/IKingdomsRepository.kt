package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.KingdomsModel
import java.util.*

interface IKingdomsRepository {

    fun create (_obj : KingdomsModel)

    fun readById (_id: UUID) : List<KingdomsModel>
    fun readByName (_name : String) : List<KingdomsModel>

    fun replaceById(_targetId: UUID, _obj: KingdomsModel)
    fun replaceByName(_targetName: String, _obj: KingdomsModel)

    fun deleteById (_id: UUID)
    fun deleteByName (_name: String)

}