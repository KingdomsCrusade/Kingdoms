package net.kingdomscrusade.kingdoms.api.service

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.RolesModel
import net.kingdomscrusade.kingdoms.api.repository.IRolesRepository
import java.util.*
import javax.inject.Inject

class RolesService : IRolesService{

    companion object : Provider<IRolesService> {
        @Provides
        override fun provide() : IRolesService = RolesService()
    }

    @Inject
    private lateinit var repository: IRolesRepository

    override fun create(_obj: RolesModel) =
        repository.create(_obj)

    override fun readById(_id: UUID): List<RolesModel> =
        repository.readById(_id)

    override fun readByName(_name: String, _kingdom: UUID?): List<RolesModel> =
        repository.readByName(_name, _kingdom)

    override fun replaceById(_targetId: UUID, _obj: RolesModel) =
        repository.replaceById(_targetId, _obj)

    override fun replaceByName(_targetName: String, _targetRoleKingdom: UUID?, _obj: RolesModel) =
        repository.replaceByName(_targetName, _targetRoleKingdom, _obj)

    override fun deleteById(_id: UUID) =
        repository.deleteById(_id)

    override fun deleteByName(_name: String, _kingdom: UUID?) =
        repository.deleteByName(_name, _kingdom)
}