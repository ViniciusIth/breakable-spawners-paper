package cloud.viniciusith.breakablespawner.listeners

import cloud.viniciusith.breakablespawner.BreakableSpawner
import cloud.viniciusith.breakablespawner.utils.BSUtils
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
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

        if (event.isCancelled || event.block.type.name !== Material.SPAWNER.name || !BSUtils.isBreakingToolValid(player.inventory.itemInMainHand)) return

        val entityName = BSUtils.getSpawnerEntityName(block, this.plugin)

        if (entityName.isNullOrEmpty()) return

        this.plugin.logger.fine("block stored entity = ${BSUtils.nameToMobId(entityName)}")

        val spawnerItemStack: ItemStack = BSUtils.newSpawnerItem(entityName)

        player.world.dropItemNaturally(block.location, spawnerItemStack)
    }

    @EventHandler
    fun onSpawnerPlace(event: BlockPlaceEvent) {
        val block: Block = event.blockPlaced
        val player: Player = event.player
        val item: ItemStack = BSUtils.getSpawnerFromHand(player.inventory)

        if (event.isCancelled || block.type.name !== Material.SPAWNER.name) return

        val entityId = BSUtils.getStoredEntity(item)

        if (entityId.isNullOrEmpty()) return

        this.plugin.logger.fine("Setting spawner entity to $entityId")

        BSUtils.setSpawnerEntity(block, entityId)
    }
}