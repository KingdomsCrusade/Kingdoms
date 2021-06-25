package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.KingdomModel
import java.util.*

interface IKingdomsRepository {

    fun create (_obj : KingdomModel)

    fun readById (_id: UUID) : List<KingdomModel>
    fun readByName (_name : String) : List<KingdomModel>

    fun replaceById(_targetId: UUID, _obj: KingdomModel)
    fun replaceByName(_targetName: String, _obj: KingdomModel)

    fun deleteById (_id: UUID)
    fun deleteByName (_name: String)

}