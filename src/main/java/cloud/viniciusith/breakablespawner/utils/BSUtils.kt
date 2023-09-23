package cloud.viniciusith.breakablespawner.utils

import cloud.viniciusith.breakablespawner.BreakableSpawner
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.CreatureSpawner
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object BSUtils {
    fun newSpawnerItem(entityName: String): ItemStack {
        val item = ItemStack(Material.SPAWNER, 1)
        val meta: ItemMeta = item.itemMeta

        meta.persistentDataContainer.set(
            NamespacedKey(BreakableSpawner.PLUGIN_NAME, "entity_id"),
            PersistentDataType.STRING,
            nameToMobId(entityName)
        )

        meta.persistentDataContainer.set(
            NamespacedKey(BreakableSpawner.PLUGIN_NAME, "mined"),
            PersistentDataType.BOOLEAN,
            true
        )

        meta.lore(
            listOf(
                Component.text("Stored Entity: ")
                    .append(Component.text(entityName, NamedTextColor.YELLOW))
            )
        )

        item.setItemMeta(meta)

        return item
    }

    fun getSpawnerEntityName(block: Block, plugin: BreakableSpawner): String? {
        val blockState = block.state
        if (blockState !is CreatureSpawner) {
            plugin.logger.warning("getSpawnerEntityName called on non-spawner block: $block")
            return null
        }

        if (blockState.spawnedType != null) {
            return blockState.spawnedType?.toString()?.lowercase()
        }

        return null
    }

    fun setSpawnerEntity(block: Block, entityId: String) {
        val blockState = block.state
        if (blockState !is CreatureSpawner) return

        var entity: EntityType? = null

        EntityType.entries.toTypedArray().forEach {
            if (it.name == mobIdToName(entityId).uppercase()) entity = it
        }

//        TODO: Check if entity is null

        blockState.spawnedType = entity
        blockState.update(true)
    }

    fun getStoredEntity(itemStack: ItemStack): String? {
        val entityKey = NamespacedKey(BreakableSpawner.PLUGIN_NAME, "entity_id")
        val data = itemStack.itemMeta.persistentDataContainer

        if (data.has(entityKey, PersistentDataType.STRING)) {
            println(data.get(entityKey, PersistentDataType.STRING))
            return data.get(entityKey, PersistentDataType.STRING)
        }

        return null
    }

    fun mobIdToName(entityId: String): String {
        return entityId.substringAfter(':')
    }

    fun nameToMobId(entityName: String): String {
        return "minecraft:${entityName}"
    }

    fun getSpawnerFromHand(inventory: PlayerInventory): ItemStack {
        if (inventory.itemInMainHand.type.name === Material.SPAWNER.name) return inventory.itemInMainHand
        return inventory.itemInOffHand
    }
}
