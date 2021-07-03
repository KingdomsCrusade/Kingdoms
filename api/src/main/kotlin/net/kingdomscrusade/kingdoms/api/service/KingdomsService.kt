package net.kingdomscrusade.kingdoms.api.service

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.KingdomsModel
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

    override fun create(_obj: KingdomsModel) =
        repository.create(_obj)

    override fun readById(_id: UUID) =
        repository.readById(_id)

    override fun readByName(_name: String) =
        repository.readByName(_name)

    override fun replaceById(_targetId: UUID, _obj: KingdomsModel) =
        repository.replaceById(_targetId, _obj)

    override fun replaceByName(_targetName: String, _obj: KingdomsModel) =
        repository.replaceByName(_targetName, _obj)

    override fun deleteById(_id: UUID) =
        repository.deleteById(_id)

    override fun deleteByName(_name: String) =
        repository.deleteByName(_name)

}