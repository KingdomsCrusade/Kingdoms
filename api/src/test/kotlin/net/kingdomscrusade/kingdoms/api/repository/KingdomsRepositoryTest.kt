@file:Suppress("SqlResolve")

package net.kingdomscrusade.kingdoms.api.repository

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.util.TestUtil.api
import net.kingdomscrusade.kingdoms.api.util.TestUtil.injectInit
import net.kingdomscrusade.kingdoms.api.util.toStringWithoutDashes
import net.kingdomscrusade.kingdoms.api.util.toUUID
import java.util.*

class KingdomsRepositoryTest : FunSpec({

    injectInit(this)

    test("create") {

        // Given a kingdom's credentials
        val info = Kingdom(name = "Kingdom of Windmire")
        println("Generated object: $info")

        // When function `create` is called
        KingdomsRepository().create(info.id!!, info.name!!)

        // Then function should add a new role with the corresponding credentials given
        val list = mutableListOf<Kingdom>()
        api.getConnectionObject().createStatement().run {
            executeQuery("SELECT kingdom_id, kingdom_name FROM Kingdoms").run {
                while (next())
                    list += Kingdom(
                        id = getBytes("kingdom_id").toUUID(),
                        name = getString("kingdom_name")
                    )
            }
        }
        println("Queried object: $list")

        list.contains(info) shouldBe true

    }

    context("readById"){

        test("with existing object id as parameter") {

            // Given that a kingdom object has been inserted to table
            val obj = Kingdom( name = "Pickleland" )
            println("Generated object: $obj")
            api.getConnectionObject().createStatement().run {
                executeUpdate(
                    """
                    INSERT INTO Kingdoms VALUES (
                        UNHEX('${obj.id.toStringWithoutDashes()}'),
                        '${obj.name}'    
                    )
                    """.trimIndent()
                )
            }

            // When someone is querying with a UUID that exists, for example the object we just created
            val result = KingdomsRepository().readById(obj.id!!)
            println("Queried object: $result")

            // Then the result (it's a list of object) of it should contain the kingdom object
            result.shouldContain(obj)

        }

         test("with object id that does not exists as parameter") {

             // Given a random, non-exist kingdom object, Let's say... Kingdom of Stardrop?
             val obj = Kingdom(name = "Kingdom of Stardrop")
             println("Generated object: $obj")

             // When someone is querying with a UUID that does not exist, like the object we just created
             val result = KingdomsRepository().readById(obj.id!!)
             println("Queried object: $result")

             // Then the result of it should not contain the object
             result.shouldNotContain(obj)

         }

    }

    context("readByName") {

        test("existing object id as parameter") {

            // Given that a kingdom object has been inserted to table
            val obj = Kingdom(name = "Midgard")
            println("Generated object: $obj")
            api.getConnectionObject().createStatement().run {
                executeUpdate(
                    """
                        INSERT INTO Kingdoms VALUES (
                            UNHEX('${obj.id.toStringWithoutDashes()}'),
                            '${obj.name}'
                        )
                    """.trimIndent()
                )
            }

            // When user is querying with an object name that exists, for example, the object that we just created
            val result = KingdomsRepository().readByName(obj.name!!)
            println("Queried object: $result")

            // Then the result should contain the object we just created
            result.shouldContain(obj)

        }

        test("with non-existing object name as parameter") {

            // Given a random, non-existing kingdom object
            val obj = Kingdom(name = "SanDisk")
            println("Generated object: $obj")

            // When someone is querying with this object's name
            val result = KingdomsRepository().readByName(obj.name!!)
            println("Queried object: $result")

            // Then it should not contain the object we just generated
            result.shouldNotContain(obj)

        }

    }

    context("updateById") {

        test("updating existing object") {
            // Given a kingdom object has been inserted to table
            val obj = Kingdom(name = "Redhat")
            println("Generated object: $obj")
            api.getConnectionObject().createStatement().run {
                executeUpdate(
                    """
                    INSERT INTO Kingdoms VALUES (
                        UNHEX('${obj.id.toStringWithoutDashes()}'), 
                        '${obj.name}'
                    )
                """.trimIndent()
                )
            }

            // When user is updating a kingdom object's name to Peneng targeting an existing object, like the one we just inserted
            val newName = "Peneng"
            KingdomsRepository().updateById(obj.id!!, newName)

            // Then the object in table should have the new name
            val expected = Kingdom(obj.id!!, newName)
            val queried = api.getConnectionObject().createStatement().run {
                executeQuery(
                    """
                        SELECT * FROM Kingdoms
                    """.trimIndent()
                ).run {
                    mutableListOf<Kingdom>().apply {
                        while (next())
                            this += Kingdom(
                                id = getBytes("kingdom_id").toUUID(),
                                name = getString("kingdom_name")
                            )
                    }
                }
            }
            println("Queried objects: $queried")
            queried.shouldContain(expected)
        }

        xtest("updating non-existing object") {
            // TODO Element not found error
            shouldThrow<NoSuchElementException> {
                KingdomsRepository().updateById(UUID.randomUUID(), "TheAlienKingdom")
            }
        }
    }

    context("updateByName") {
        test("update existing object") {
            // Given a kingdom object has been inserted to table
            val obj = Kingdom(name = "Redhat")
            println("Generated object: $obj")
            api.getConnectionObject().createStatement().run {
                executeUpdate(
                    """
                    INSERT INTO Kingdoms VALUES (
                        UNHEX('${obj.id.toStringWithoutDashes()}'), 
                        '${obj.name}'
                    )
                """.trimIndent()
                )
            }

            // When user is updating a kingdom object's name to Peneng targeting an existing object, like the one we just inserted
            val newName = "Peneng"
            KingdomsRepository().updateByName(obj.name!!, newName)

            // Then the object in table should have the new name
            val expected = Kingdom(obj.id!!, newName)
            val queried = api.getConnectionObject().createStatement().run {
                executeQuery(
                    """
                        SELECT * FROM Kingdoms
                    """.trimIndent()
                ).run {
                    mutableListOf<Kingdom>().apply {
                        while (next())
                            this += Kingdom(
                                id = getBytes("kingdom_id").toUUID(),
                                name = getString("kingdom_name")
                            )
                    }
                }
            }
            println("Queried objects: $queried")
            queried.shouldContain(expected)
        }
        xtest("update non-existing object") {
            // TODO Element not found error
            shouldThrow<NoSuchElementException> {
                KingdomsRepository().updateByName("Logitech", "TheAlienKingdom")
            }
        }
    }

    context("deleteById") {
        test("delete existing object") {
            // Given a kingdom object has been inserted to table
            val obj = Kingdom(name = "Redhat")
            println("Generated object: $obj")
            api.getConnectionObject().createStatement().run {
                executeUpdate(
                    """
                    INSERT INTO Kingdoms VALUES (
                        UNHEX('${obj.id.toStringWithoutDashes()}'), 
                        '${obj.name}'
                    )
                """.trimIndent()
                )
            }

            // When user is updating a kingdom object's name to Peneng targeting an existing object, like the one we just inserted
            KingdomsRepository().deleteById(obj.id!!)

            // Then the object in table should have the new name
            val queried = api.getConnectionObject().createStatement().run {
                executeQuery(
                    """
                        SELECT * FROM Kingdoms
                    """.trimIndent()
                ).run {
                    mutableListOf<Kingdom>().apply {
                        while (next())
                            this += Kingdom(
                                id = getBytes("kingdom_id").toUUID(),
                                name = getString("kingdom_name")
                            )
                    }
                }
            }
            println("Queried objects: $queried")
            queried.shouldNotContain(obj)
        }
        xtest("update non-existing object") {
            // TODO Element not found error
            shouldThrow<NoSuchElementException> {
                KingdomsRepository().deleteById(UUID.randomUUID())
            }
        }
    }

    context("deleteByName") {
        test("delete existing object") {
            // Given a kingdom object has been inserted to table
            val obj = Kingdom(name = "Redhat")
            println("Generated object: $obj")
            api.getConnectionObject().createStatement().run {
                executeUpdate(
                    """
                    INSERT INTO Kingdoms VALUES (
                        UNHEX('${obj.id.toStringWithoutDashes()}'), 
                        '${obj.name}'
                    )
                """.trimIndent()
                )
            }

            // When user is updating a kingdom object's name to Peneng targeting an existing object, like the one we just inserted
            KingdomsRepository().deleteByName(obj.name!!)

            // Then the object in table should have the new name
            val queried = api.getConnectionObject().createStatement().run {
                executeQuery(
                    """
                        SELECT * FROM Kingdoms
                    """.trimIndent()
                ).run {
                    mutableListOf<Kingdom>().apply {
                        while (next())
                            this += Kingdom(
                                id = getBytes("kingdom_id").toUUID(),
                                name = getString("kingdom_name")
                            )
                    }
                }
            }
            println("Queried objects: $queried")
            queried.shouldNotContain(obj)
        }
        xtest("update non-existing object") {
            // TODO Element not found error
            shouldThrow<NoSuchElementException> {
                KingdomsRepository().deleteByName("Vivobook")
            }
        }
    }

})
