package me.kooper.skghost.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.skghost.SkGhost
import org.bukkit.Location
import org.bukkit.event.Event


@Suppress("UnstableApiUsage")
class CondLocationInView : Condition() {

    companion object {
        init {
            Skript.registerCondition(
                CondLocationInView::class.java,
                "%location% (1¦is|2¦is(n't| not)) inside [of] %string% [of|in] %string%"
            )
        }
    }

    private lateinit var location: Expression<Location>
    private lateinit var view: Expression<String>
    private lateinit var stage: Expression<String>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        location = expressions[0] as Expression<Location>
        view = expressions[1] as Expression<String>
        stage = expressions[2] as Expression<String>
        isNegated = parser!!.mark == 1
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Block inside view: ${location.toString(event, debug)} ${
            view.toString(
                event,
                debug
            )
        } ${stage.toString(event, debug)}"
    }

    override fun check(event: Event?): Boolean {
        if (stage.getSingle(event) == null || location.getSingle(event) == null || view.getSingle(event) == null || SkGhost.instance.ghostCore.stageManager.getStage(
                stage.getSingle(event)!!
            ) == null || SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.views[view.getSingle(
                event
            )!!]
            == null
        ) return isNegated
        return if (SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.views[view.getSingle(event)!!]!!.blocks[Position.block(location.getSingle(event)!!)] != null) isNegated else !isNegated
    }

}