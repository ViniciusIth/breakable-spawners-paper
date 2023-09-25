package cloud.viniciusith.breakablespawner

import cloud.viniciusith.breakablespawner.commands.ReloadCommand
import cloud.viniciusith.breakablespawner.listeners.SpawnerListener
import org.bukkit.plugin.java.JavaPlugin

class BreakableSpawner : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()

        server.commandMap.register(
            PLUGIN_NAME, ReloadCommand(
                this, "reload", "Reloads Breakable Spawners configs", "/",
                arrayListOf()
            )
        )

        server.pluginManager.registerEvents(SpawnerListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        const val PLUGIN_NAME = "bspawn"
    }
}
