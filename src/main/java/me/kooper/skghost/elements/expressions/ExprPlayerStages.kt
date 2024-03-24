package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.Stage
import me.kooper.skghost.SkGhost
import org.bukkit.entity.Player
import org.bukkit.event.Event

class ExprPlayerStages : SimpleExpression<Stage>() {

    private lateinit var player: Expression<Player>

    companion object {
        init {
            Skript.registerExpression(
                ExprPlayerStages::class.java,
                Stage::class.java,
                ExpressionType.SIMPLE,
                "[the|all] stage(s) of %player%",
                "all of %player%('s) stage(s)",
                "%player%('s) stage(s)"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Stages of player expression with expression player: ${player.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        player = expressions!![0] as Expression<Player>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Stage> {
        return Stage::class.java
    }

    override fun get(event: Event?): Array<Stage?> {
        if (player.getSingle(event) == null) return arrayOf(null)
        return SkGhost.instance.ghostCore.stageManager.getStages(player.getSingle(event)!!).toTypedArray()
    }

}