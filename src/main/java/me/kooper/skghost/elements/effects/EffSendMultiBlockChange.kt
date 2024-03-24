package me.kooper.skghost.elements.effects
import ch.njol.skript.Skript
import ch.njol.skript.aliases.ItemType
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.ghostcore.models.ChunkedView
import me.kooper.ghostcore.utils.types.SimplePosition
import me.kooper.skghost.SkGhost
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import org.bukkit.event.Event

@Suppress("UnstableApiUsage")
class EffSendMultiBlockChange : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffSendMultiBlockChange::class.java,
                "send multi block change at %locations% to %itemtype/blockdata% for %player%"
            )
        }
    }

    private lateinit var location: Expression<Location>
    private lateinit var player: Expression<Player>
    private lateinit var item: Expression<Any>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Send multi block change with expression location: ${
            location.toString(
                event,
                debug
            )
        } and string expression player: ${
            player.toString(
                event,
                debug
            )
        } and string expression item: ${
            item.toString(
                event,
                debug
            )
        }"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        location = expressions!![0] as Expression<Location>
        item = expressions[1] as Expression<Any>
        player = expressions[2] as Expression<Player>
        return true
    }

    override fun execute(event: Event?) {
        val locations = location.getArray(event)
        val item = item.getSingle(event)
        val player = player.getSingle(event)
        if (locations == null || item == null || player == null) return

        val blockData = when (item) {
            is ItemType -> item.material.createBlockData()
            is BlockData -> item
            else -> return
        }

        player.sendMultiBlockChange(
            locations.associateWith { blockData }
        )
    }

}