package net.kingdomscrusade.kingdoms.api.service

import net.kingdomscrusade.kingdoms.api.model.Kingdom
import java.util.*

class KingdomsService : IKingdomsService{
    override fun create(_id: UUID, _name: String) {
        TODO("Not yet implemented")
    }

    override fun readById(_id: UUID): List<Kingdom> {
        TODO("Not yet implemented")
    }

    override fun readByName(_name: String): List<Kingdom> {
        TODO("Not yet implemented")
    }

    override fun updateById(_targetId: UUID, _name: String?) {
        TODO("Not yet implemented")
    }

    override fun updateByName(_targetName: String, _name: String?) {
        TODO("Not yet implemented")
    }

    override fun deleteById(_id: UUID) {
        TODO("Not yet implemented")
    }

    override fun deleteByName(_name: String) {
        TODO("Not yet implemented")
    }
}