package net.kingdomscrusade.kingdoms.api.service

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.UsersModel
import net.kingdomscrusade.kingdoms.api.repository.IUsersRepository
import net.kingdomscrusade.kingdoms.api.table.UsersTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andNot
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.inject.Inject

class UsersService : IUsersService{

    companion object : Provider<IUsersService> {
        @Provides
        override fun provide() : IUsersService = UsersService()
    }

    @Inject
    private lateinit var repository: IUsersRepository

    override fun create(_obj: UsersModel) {
        // User dupe check
        val userDupe = !transaction { UsersTable.select { (UsersTable.kingdom eq _obj.kingdom) and (UsersTable.id eq _obj.id) }.empty() }
        if (userDupe) throw IllegalArgumentException("Same user under the kingdom exists.")

        repository.create(_obj)
    }

    override fun readById(_id: UUID, _kingdom: UUID): List<UsersModel> =
        repository.readById(_id, _kingdom)

    override fun readByName(_name: String, _kingdom: UUID): List<UsersModel> =
        repository.readByName(_name, _kingdom)

    @Suppress("DuplicatedCode")
    override fun replaceById(_targetId: UUID, _targetUserKingdom: UUID, _obj: UsersModel) {
        // User dupe check
        val userDupe = !transaction {
            UsersTable
                .select {
                    // Receiving all rows matching _obj.id and _obj.kingdom
                    (UsersTable.id eq _obj.id) and (UsersTable.kingdom eq _obj.kingdom)
                        // And not the rows that matches the targetId and targetKingdom
                        .andNot{(UsersTable.id eq _targetId) and (UsersTable.kingdom eq _targetUserKingdom)}
                }
                .empty()
        }
        if (userDupe) throw IllegalArgumentException("Same user under the kingdom exists.")

        replaceById(_targetId, _targetUserKingdom, _obj)
    }

    @Suppress("DuplicatedCode")
    override fun replaceByName(_targetName: String, _targetUserKingdom: UUID, _obj: UsersModel) {
        // User dupe check
        val userDupe = !transaction {
            UsersTable
                .select {
                    // Receiving all rows matching _obj.id and _obj.kingdom
                    (UsersTable.id eq _obj.id) and (UsersTable.kingdom eq _obj.kingdom)
                        // And not the rows that matches the targetId and targetKingdom
                        .andNot{(UsersTable.name eq _targetName) and (UsersTable.kingdom eq _targetUserKingdom)}
                }
                .empty()
        }
        if (userDupe) throw IllegalArgumentException("Same user under the kingdom exists.")

        replaceByName(_targetName, _targetUserKingdom, _obj)
    }

    override fun deleteById(_id: UUID, _kingdom: UUID) =
        repository.deleteById(_id, _kingdom)

    override fun deleteByName(_name: String, _kingdom: UUID) =
        repository.deleteByName(_name, _kingdom)
}