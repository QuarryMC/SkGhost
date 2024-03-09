package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.Stage
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffAddPlayerStage : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffAddPlayerStage::class.java,
                "add %player% to stage %stage%"
            )
        }
    }

    private lateinit var player: Expression<Player>
    private lateinit var stage: Expression<Stage>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Add player to stage with expression player: ${
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
        stage = expressions[1] as Expression<Stage>
        return true
    }

    override fun execute(event: Event?) {
        val stage = stage.getSingle(event)
        val player = player.getSingle(event)
        if (player == null || stage == null) return
        stage.addPlayer(player)
    }

}