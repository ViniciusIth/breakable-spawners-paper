package cloud.viniciusith.breakablespawner

import cloud.viniciusith.breakablespawner.listeners.SpawnerListener
import org.bukkit.plugin.java.JavaPlugin

class BreakableSpawner : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()

        server.pluginManager.registerEvents(SpawnerListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        const val PLUGIN_NAME = "bspawn"
    }
}
