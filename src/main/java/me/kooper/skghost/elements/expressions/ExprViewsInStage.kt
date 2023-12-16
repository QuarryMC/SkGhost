package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.kooper.ghostcore.data.ViewData
import me.kooper.ghostcore.models.Stage
import org.bukkit.event.Event

class ExprViewsInStage : SimpleExpression<ViewData>() {

    private lateinit var stage: Expression<Stage>

    companion object {
        init {
            Skript.registerExpression(
                ExprViewsInStage::class.java,
                ViewData::class.java, ExpressionType.SIMPLE, "[the] view(s) of %stage%", "%stage%('s) view(s)"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Views in stage expression with expression view: ${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(expressions: Array<out Expression<*>>?, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        stage = expressions!![0] as Expression<Stage>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out ViewData> {
        return ViewData::class.java
    }

    override fun get(event: Event?): Array<ViewData?> {
        if (stage.getSingle(event) == null) return arrayOf(null)
        return stage.getSingle(event)!!.views.values.toTypedArray()

    }

}