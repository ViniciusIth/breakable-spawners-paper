package cloud.viniciusith.breakablespawner.commands

import cloud.viniciusith.breakablespawner.BreakableSpawner
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ReloadCommand(
    plugin: BreakableSpawner,
    name: String,
    description: String,
    usageMessage: String,
    aliases: MutableList<String>
) : Command(name, description, usageMessage, aliases) {
    private val plugin: BreakableSpawner

    init {
        this.name = name
        this.label = name
        this.description = description
        this.usageMessage = usageMessage
        this.aliases = aliases
        this.plugin = plugin
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
        if (!sender.isOp) {
            sender.sendMessage(
                Component.text("You don't have permission to run this").color(TextColor.color(NamedTextColor.RED))
            )
            return false
        }

        sender.sendMessage(
            Component.text("Reloading BreakableSpawners configs...").color(TextColor.color(NamedTextColor.YELLOW))
        )
        this.plugin.reloadConfig()

        sender.sendMessage(
            Component.text("BreakableSpawners configs reloaded").color(TextColor.color(NamedTextColor.GREEN))
        )
        return true
    }
}