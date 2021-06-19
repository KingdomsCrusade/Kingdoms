package net.kingdomscrusade.kingdoms.api.service

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.repository.IKingdomsRepository
import java.util.*
import javax.inject.Inject

class KingdomsService : IKingdomsService {

    companion object : Provider<IKingdomsService> {
        @Provides
        override fun provide() : IKingdomsService = KingdomsService()
    }

    @Inject
    private lateinit var repository: IKingdomsRepository

    override fun create(_id: UUID, _name: String) =
        // Name duplicate checking is executed server-side
        repository.create(_id, _name)

    override fun readById(_id: UUID) =
        repository.readById(_id)

    override fun readByName(_name: String) =
        repository.readByName(_name)

    override fun updateById(_targetId: UUID, _name: String?) =
        // Name duplicate checking is executed server-side
        repository.updateById(_targetId, _name)

    override fun updateByName(_targetName: String, _name: String?) =
        // Name duplicate checking is executed server-side
        repository.updateByName(_targetName, _name)

    override fun deleteById(_id: UUID) =
        repository.deleteById(_id)

    override fun deleteByName(_name: String) =
        repository.deleteByName(_name)

}