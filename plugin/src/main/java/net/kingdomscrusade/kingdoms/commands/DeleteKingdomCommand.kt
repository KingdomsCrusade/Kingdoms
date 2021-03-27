package net.kingdomscrusade.kingdoms.commands

import com.mongodb.client.model.Filters
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.CommandExecutor
import net.kingdomscrusade.kingdoms.Main
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class DeleteKingdomCommand {

    val deleteKingdom:CommandAPICommand = CommandAPICommand("deletekingdom")
        .withAliases("kingdomdelete")
        .withPermission("Kingdoms.ManageKingdoms")
        .withArguments(argument())
        .executes(
            CommandExecutor{
                sender, args -> run {

                val kingdomName = args[0] as String
                val collection = Main.getKingdomsCollection()

                // Checking if the kingdom list contains the name player given
                var contain = false
                for (kingdom in collection.find()) {
                    if (kingdom.getName() == kingdomName) {
                        contain = true
                        break
                    }
                }
                if (!contain) CommandAPI.fail("Kingdom name given isn't in list!")
                else {
                    collection.deleteOne(Filters.eq("name", kingdomName))
                    Bukkit.broadcast(
                        Component.text(
                            Main.getMessage().load("DeleteKingdom", kingdomName, sender.name)!!
                        ),
                        ""
                    )
                }


                }
            }
        )


    private fun argument(): ArrayList<Argument> {
        val argument = ArrayList<Argument>()
        val kingdomList = ArrayList<String>()
        for (kingdom in Main.getKingdomsCollection().find()){
            kingdomList.add(kingdom.getName())
        }
        argument.add(StringArgument("kingdom").overrideSuggestions(kingdomList))
        return argument
    }

}