package cloud.viniciusith.breakablespawner.listeners

import cloud.viniciusith.breakablespawner.BreakableSpawner
import cloud.viniciusith.breakablespawner.utils.BSUtils
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack


class SpawnerListener(plugin: BreakableSpawner) : Listener {

    private val plugin: BreakableSpawner

    init {
        this.plugin = plugin
    }

    @EventHandler
    fun onSpawnerBreak(event: BlockBreakEvent) {
        val block: Block = event.block
        val player: Player = event.player

        if (event.isCancelled || event.block.type.name !== Material.SPAWNER.name) return

        val entityName = BSUtils.getSpawnerEntityName(block, this.plugin)

        if (entityName.isNullOrEmpty()) return

        this.plugin.logger.fine("block stored entity = ${BSUtils.nameToMobId(entityName)}")

//        TODO: Know whether player used silk or not
//        TODO: Set exp to 0 to prevent xp farm if player has already mined (item has mined metadata)

        val spawnerItemStack: ItemStack = BSUtils.newSpawnerItem(entityName)

        player.world.dropItemNaturally(block.location, spawnerItemStack)
    }
}