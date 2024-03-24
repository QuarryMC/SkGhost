package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.skghost.SkGhost
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.Event
import java.util.*


class EffCreateStage : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffCreateStage::class.java,
                "create stage (with|for) %players% (with name|named) %string% in [world] %world%"
            )
        }
    }

    private lateinit var players: Expression<Player>
    private lateinit var name: Expression<String>
    private lateinit var world: Expression<World>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Create stage with expression players: ${
            players.toString(
                event,
                debug
            )
        } and string expression: ${name.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        players = expressions!![0] as Expression<Player>
        name = expressions[1] as Expression<String>
        world = expressions[2] as Expression<World>
        return true
    }

    override fun execute(event: Event?) {
        if (world.getSingle(event) == null || name.getSingle(event) == null || players.getAll(event) == null) return
        val list = players.getAll(event).map {
            it.uniqueId
        } as ArrayList<UUID>
        SkGhost.instance.ghostCore.stageManager.createStage(
            ChunkedStage(
                world.getSingle(event)!!,
                name.getSingle(event)!!,
                list,
                hashMapOf(),
                hashMapOf()
            )
        )
    }

}