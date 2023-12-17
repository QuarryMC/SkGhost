package me.kooper.skghost.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.Stage
import org.bukkit.event.Event

class CondStageHasView : Condition() {

    companion object {
        init {
            Skript.registerCondition(
                CondStageHasView::class.java,
                "%stage% (has|contains) [view] %string%"
            )
        }
    }

    private lateinit var stage: Expression<Stage>
    private lateinit var view: Expression<String>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        stage = expressions[0] as Expression<Stage>
        view = expressions[1] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Stage has view: ${stage.toString(event, debug)} ${view.toString(event, debug)}"
    }

    override fun check(event: Event?): Boolean {
        if (stage.getSingle(event) == null || view.getSingle(event) == null) return false
        return stage.getSingle(event)!!.views[view.getSingle(event)!!] != null
    }

}