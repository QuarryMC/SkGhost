package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.ghostcore.models.ChunkedView
import org.bukkit.Location
import org.bukkit.event.Event

@Suppress("UnstableApiUsage")
class ExprSolidBlocksInView : SimpleExpression<Location>() {

    private lateinit var view: Expression<ChunkedView>
    private lateinit var stage: Expression<ChunkedStage>

    companion object {
        init {
            Skript.registerExpression(
                ExprSolidBlocksInView::class.java,
                Location::class.java,
                ExpressionType.COMBINED,
                "[the] solid blocks in %view% (of|in) %stage%",
                "all solid blocks in %view% (of|in) %stage%"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Solid blocks in view expression with expression view: ${
            view.toString(
                event,
                debug
            )
        }, expression stage: ${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        view = expressions!![0] as Expression<ChunkedView>
        stage = expressions[1] as Expression<ChunkedStage>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Location> {
        return Location::class.java
    }

    override fun get(event: Event?): Array<Location?> {
        if (view.getSingle(event) == null || stage.getSingle(event) == null) return arrayOf(null)
        val stage: ChunkedStage = stage.getSingle(event)!!
        val view: ChunkedView = view.getSingle(event)!!
        return stage.getSolidBlocks(view.name).keys.map { p -> p.toLocation(stage.world) }.toTypedArray()
    }

}