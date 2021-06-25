package net.kingdomscrusade.kingdoms.api.util

import io.kotest.core.Tuple2
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestType
import net.kingdomscrusade.kingdoms.api.Config
import net.kingdomscrusade.kingdoms.api.KingdomsApi
import java.lang.StringBuilder
import java.sql.DriverManager
import java.sql.SQLSyntaxErrorException
import kotlin.reflect.KClass

object TestUtil {

    private var lock: Pair<Boolean, String?> = Pair(false, null)

    private fun hasNotInitialized() = !lock.first
    private fun hasInitialized() = lock.first
    private fun whichClass() = lock.second

    fun lock(clazz: KClass<Spec>) {
        lock = Pair(true, clazz.simpleName)
        println("[${this::class.simpleName}] Init has been locked.")
    }
    fun unlock() {
        lock = Pair(false, null)
        println("[${this::class.simpleName}] Init has been unlocked.")
    }

    lateinit var api : KingdomsApi
    private val url = Config.url; private val usr = Config.usr; private val pwd = Config.pwd
    val injectInit : (Spec) -> Unit = {
        it.apply {
            beforeSpec(initializeAndLock)
            beforeTest(testOutputSeparate)
            afterTest(softReinitialize)
        }
    }
    val injectClearance : (Spec) -> Unit = {
        it.apply {
            @Suppress("SqlResolve")
            beforeSpec(dropTablesAndUnlock)
            beforeTest(testOutputSeparate)
            afterTest(softReinitialize)
        }
    }
    private val initializeAndLock : suspend (Spec) -> Unit = {
        println("[${this::class.simpleName}] Present working class: ${it::class.simpleName}")
        if (hasNotInitialized()) {
            // Clearing all known table to create a new environment
            println("[${this::class.simpleName}] Init is not locked. Initializing environment for test cases.")
            @Suppress("SqlResolve")
            try {
                DriverManager.getConnection(url, usr, pwd).createStatement().run {
                    execute("SET foreign_key_checks = 0")
                    execute("DROP TABLE flyway_schema_history, Kingdoms, Permissions, Roles, Users")
                    execute("SET foreign_key_checks = 1")
                }
            } catch (e : SQLSyntaxErrorException) {
                println("[${this::class.simpleName}] Skipped dropping table due to exception thrown.")
                e.printStackTrace()
            }
            // Initializing the api, because the things later wont work without it
            api = KingdomsApi.connect(url, usr, pwd)

            // Locking and broadcasting
            lock(it.javaClass.kotlin)
        } else
            // Broadcasting
            println("[${this::class.simpleName}] Init has already been locked by ${whichClass()}.")
    }
    private val testOutputSeparate : suspend (TestCase) -> Unit = {
        // Getting all the names of its parents
        var cwo: TestCase = it
        val parentList: MutableList<String> = mutableListOf()
        while (cwo.parent != null) {
            cwo = cwo.parent!!
            parentList.add(0, cwo.displayName)
        }

        // Constructing the display string
        println(
            StringBuilder().run {
                append("\n>>>>> ")
                parentList.forEach { s -> append("$s - ") }
                append(it.displayName)
                append(" <<<<<")
                toString()
            }
        )
    }
    private val softReinitialize : suspend (Tuple2<TestCase, TestResult>) -> Unit = {

        if (it.a.type != TestType.Container) {
            @Suppress("SqlResolve", "SqlWithoutWhere")
            DriverManager.getConnection(url, usr, pwd).createStatement().run {
                // Delete all rows from all tables
                execute("DELETE FROM Kingdoms")
                execute("DELETE FROM Permissions")
                execute("DELETE FROM Roles")
                execute("DELETE FROM Users")

                // And reinitialize 'em all :)
                executeUpdate(
                    """
                        INSERT INTO Roles (role_id, role_kingdom, role_name) VALUES 
                        (UNHEX('66e00734bde443c0a42646b79075cbb1'), NULL, 'Owner'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb2'), NULL, 'Member'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb3'), NULL, 'Visitor'); 
                    """.trimIndent()
                )
                executeUpdate(
                    """
                        INSERT INTO Permissions (permission_ref, permission_val) VALUES 
                        (UNHEX('66e00734bde443c0a42646b79075cbb1'), 'ADMIN'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'BUILD'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'CONTAINER'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'INTERACT'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'KILL'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'PICK'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb2'), 'TALK'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb3'), 'TALK'), 
                        (UNHEX('66e00734bde443c0a42646b79075cbb3'), 'INTERACT');
                    """.trimIndent()
                )
            }
            println("\n[${this::class.simpleName}] Soft reinitialized test environment for next test case.")
        }
    }

    private val dropTablesAndUnlock : suspend (Spec) -> Unit = {
        println("[${this::class.simpleName}] Present working class: ${it::class.simpleName}")

        println("[${this::class.simpleName}] Dropping all tables and initializing api object instance without migration.")
        // Drop all tables
        try {
            @Suppress("SqlResolve")
            DriverManager.getConnection(url, usr, pwd).createStatement().run {
                execute("SET foreign_key_checks = 0")
                execute("DROP TABLE flyway_schema_history, Kingdoms, Permissions, Roles, Users")
                execute("SET foreign_key_checks = 1")
            }
        } catch (e : SQLSyntaxErrorException) {
            println("[${this::class.simpleName}] Skipped dropping table due to exception thrown.")
            e.printStackTrace()
        }
        // Initializing api value without migrating to avoid exception
        api = KingdomsApi.connectWithoutMigrate(url, usr, pwd)

        // If is locked, unlock. If not, tell user that is is not locked but system will attempt a lock.
        if (hasInitialized()) {
            unlock()
        } else {
            println("[${this::class.simpleName}] Init is not locked, but System will still set lock to false state to prevent any potential errors.")
            unlock()
        }
    }

}