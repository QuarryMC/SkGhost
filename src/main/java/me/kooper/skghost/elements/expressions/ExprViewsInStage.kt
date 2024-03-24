package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.ghostcore.models.ChunkedView
import org.bukkit.event.Event

class ExprViewsInStage : SimpleExpression<ChunkedView>() {

    private lateinit var stage: Expression<ChunkedStage>

    companion object {
        init {
            Skript.registerExpression(
                ExprViewsInStage::class.java,
                ChunkedView::class.java,
                ExpressionType.SIMPLE,
                "[the] view(s) of stage %stage%",
                "[stage] %stage%('s) view(s)"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Views in stage expression with expression view: ${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        stage = expressions!![0] as Expression<ChunkedStage>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out ChunkedView> {
        return ChunkedView::class.java
    }

    override fun get(event: Event?): Array<ChunkedView?> {
        if (stage.getSingle(event) == null) return arrayOf(null)
        return (stage.getSingle(event)!!.views.mapValues {
            it.value as ChunkedView
        }).values.toTypedArray()

    }

}