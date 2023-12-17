package me.kooper.skghost.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.Stage
import org.bukkit.entity.Player
import org.bukkit.event.Event

class CondPlayerInStage : Condition() {

    companion object {
        init {
            Skript.registerCondition(
                CondPlayerInStage::class.java,
                "%player% (1¦is|2¦is(n't| not)) in stage %stage%"
            )
        }
    }

    private lateinit var player: Expression<Player>
    private lateinit var stage: Expression<Stage>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        player = expressions[0] as Expression<Player>
        stage = expressions[1] as Expression<Stage>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Player in stage: ${player.toString(event, debug)} ${
            stage.toString(
                event,
                debug
            )
        }"
    }

    override fun check(event: Event?): Boolean {
        if (stage.getSingle(event) == null || player.getSingle(event) == null) return false
        return stage.getSingle(event)!!.audience.contains(
            player.getSingle(
                event
            )!!.uniqueId
        )
    }

}