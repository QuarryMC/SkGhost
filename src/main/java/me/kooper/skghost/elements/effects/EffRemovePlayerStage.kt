package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.Stage
import me.kooper.skghost.SkGhost
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffRemovePlayerStage : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffAddPlayerStage::class.java,
                "remove %player% from stage %stage%"
            )
        }
    }

    private lateinit var player: Expression<Player>
    private lateinit var stage: Expression<Stage>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Remove player from stage with expression player: ${
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
        Bukkit.getScheduler().runTaskAsynchronously(SkGhost.instance, Runnable {
            run {
                if (player.getSingle(event) == null || stage.getSingle(event) == null) return@Runnable
                stage.getSingle(event)!!.removePlayer(player.getSingle(event)!!)
            }
        })
    }

}