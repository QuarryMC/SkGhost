package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.skghost.SkGhost
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffAddPlayerStage : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffAddPlayerStage::class.java,
                "add %player% to stage %string%"
            )
        }
    }

    private lateinit var player: Expression<Player>
    private lateinit var stage: Expression<String>

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
        stage = expressions[1] as Expression<String>
        return true
    }

    override fun execute(event: Event?) {
        if (player.getSingle(event) == null || stage.getSingle(event) == null || SkGhost.instance.ghostCore.stageManager.getStage(
                stage.getSingle(event)!!
            ) == null
        ) return
        SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.addPlayer(player.getSingle(event)!!)
    }

}