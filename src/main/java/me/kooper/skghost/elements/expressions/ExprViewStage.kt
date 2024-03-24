package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedView
import me.kooper.ghostcore.models.Stage
import org.bukkit.event.Event

class ExprViewStage : SimpleExpression<ChunkedView>() {

    private lateinit var stage: Expression<Stage>
    private lateinit var view: Expression<String>

    companion object {
        init {
            Skript.registerExpression(
                ExprViewStage::class.java,
                ChunkedView::class.java, ExpressionType.COMBINED, "%string% view of [stage] %stage%"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "View in stage with expression view: ${
            view.toString(
                event,
                debug
            )
        } and expression stage:${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        view = expressions!![0] as Expression<String>
        stage = expressions[1] as Expression<Stage>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out ChunkedView> {
        return ChunkedView::class.java
    }

    override fun get(event: Event?): Array<ChunkedView?> {
        if (stage.getSingle(event) == null || view.getSingle(event) == null || stage.getSingle(event)!!.views[view.getSingle(
                event
            )!!] == null
        ) return arrayOf(null)
        return arrayOf(stage.getSingle(event)!!.views[view.getSingle(event)!!]!! as ChunkedView)

    }

}