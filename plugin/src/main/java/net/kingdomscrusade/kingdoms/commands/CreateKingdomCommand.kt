package net.kingdomscrusade.kingdoms.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import net.kingdomscrusade.kingdoms.Main
import net.kingdomscrusade.kingdoms.actions.CreateKingdom
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class CreateKingdomCommand {

    val createKingdom: CommandAPICommand = CommandAPICommand("createkingdom")
        .withAliases("kingdomcreate")
        .withArguments(listOf(
            StringArgument("name").setListed(false),
            PlayerArgument("player")
        ))
        .withPermission("kingdoms.ManageKingdoms")
        .executesPlayer(
            PlayerCommandExecutor{sender, args ->
                run {

                    val player = args[1] as Player
                    val name = args[0] as String

                    if (CreateKingdom.accept(sender, name, player))
                        Bukkit.broadcast(
                            Component.text(
                                Main.getMessage().load(
                                    "CreateKingdom",
                                    name,
                                    player.name,
                                    sender.name
                                )!!
                            ),
                            "" // Sends the message to everyone on server
                        )
                }
            }
        )
}