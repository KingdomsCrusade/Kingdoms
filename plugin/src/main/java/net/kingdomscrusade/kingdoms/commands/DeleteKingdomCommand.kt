package net.kingdomscrusade.kingdoms.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import net.kingdomscrusade.kingdoms.KingdomsMain
import net.kingdomscrusade.kingdoms.actions.DeleteKingdom
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.stream.Collectors

class DeleteKingdomCommand {

    val deleteKingdom:CommandAPICommand = CommandAPICommand("deletekingdom")
        .withAliases("kingdomdelete")
        .withPermission("kingdoms.ManageKingdoms")
        .withArguments(listOf(
            StringArgument("kingdom")
                .overrideSuggestions(
                    KingdomsMain.getKingdomsCollection().find()
                        .toList()
                        .stream()
                        .map { k -> k.getName() }
                        .collect(Collectors.toList())
                )
        ))
        .executesPlayer(
            PlayerCommandExecutor{ sender, args ->
                run {

                    val kingdomName = args[0] as String

                    if (DeleteKingdom.accept(kingdomName))
                        Bukkit.broadcast(
                            Component.text(
                                KingdomsMain.getMessage().load(
                                    "DeleteKingdom",
                                    kingdomName,
                                    sender.name
                                )!!
                            ),
                            ""
                        )

            }
        }
    )
}