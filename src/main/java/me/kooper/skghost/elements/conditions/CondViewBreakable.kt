package me.kooper.skghost.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.skghost.SkGhost
import org.bukkit.event.Event

class CondViewBreakable : Condition() {

    companion object {
        init {
            Skript.registerCondition(
                CondLocationInView::class.java,
                "%string% of %string% (1¦is|2¦is(n't| not)) breakable"
            )
        }
    }

    private lateinit var view: Expression<String>
    private lateinit var stage: Expression<String>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        view = expressions[0] as Expression<String>
        stage = expressions[1] as Expression<String>
        isNegated = parser!!.mark == 1
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "View is breakable: ${
            view.toString(
                event,
                debug
            )
        } ${stage.toString(event, debug)}"
    }

    override fun check(event: Event?): Boolean {
        if (stage.getSingle(event) == null || view.getSingle(event) == null || SkGhost.instance.ghostCore.stageManager.getStage(
                stage.getSingle(event)!!
            ) == null || SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.views[view.getSingle(
                event
            )!!]
            == null
        ) return isNegated
        return if (SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.views[view.getSingle(event)!!]!!.isBreakable) isNegated else !isNegated
    }


}