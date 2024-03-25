package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.skghost.SkGhost
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffRemovePlayerStage : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffRemovePlayerStage::class.java,
                "remove %player% from stage %stage%"
            )
        }
    }

    private lateinit var player: Expression<Player>
    private lateinit var stage: Expression<ChunkedStage>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Remove player to stage with expression player: ${
            player.toString(
                event,
                debug
            )
        } and string expression stage ${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        player = expressions!![0] as Expression<Player>
        stage = expressions[1] as Expression<ChunkedStage>
        return true
    }

    override fun execute(event: Event?) {
        val stage = stage.getSingle(event)
        val player = player.getSingle(event)
        if (player == null || stage == null) return
        stage.removePlayer(player)
    }

}