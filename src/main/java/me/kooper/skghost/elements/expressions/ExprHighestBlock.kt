package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.kooper.ghostcore.data.ViewData
import me.kooper.ghostcore.models.Stage
import org.bukkit.Location
import org.bukkit.event.Event


@Suppress("UnstableApiUsage")
class ExprHighestBlock : SimpleExpression<Location>() {

    private lateinit var view: Expression<ViewData>
    private lateinit var stage: Expression<Stage>
    private lateinit var location: Expression<Location>

    companion object {
        init {
            Skript.registerExpression(
                ExprHighestBlock::class.java,
                Location::class.java,
                ExpressionType.COMBINED,
                "[the] highest ghost block at %location% (in|of) view %view% (in|of) stage %stage%"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Highest block location of view expression with expression location: ${
            location.toString(
                event,
                debug
            )
        }, expression stage ${stage.getSingle(event)!!}, and expression view: ${view.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        location = expressions!![0] as Expression<Location>
        view = expressions[1] as Expression<ViewData>
        stage = expressions[2] as Expression<Stage>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Location> {
        return Location::class.java
    }

    override fun get(event: Event?): Array<Location?> {
        if (location.getSingle(event) == null || view.getSingle(event) == null || stage.getSingle(event) == null) return arrayOf(
            null
        )
        return arrayOf(
            (stage.getSingle(event)!!.getHighestPosition(
                view.getSingle(event)!!.name,
                location.getSingle(event)!!.blockX,
                location.getSingle(event)!!.blockZ
            )
                ?: return arrayOf(null)).toLocation(stage.getSingle(event)!!.world)
        )
    }

}