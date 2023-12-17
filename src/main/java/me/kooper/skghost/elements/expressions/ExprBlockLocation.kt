package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.ghostcore.data.ViewData
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.event.Event


class ExprBlockLocation : SimpleExpression<Block>() {

    private lateinit var view: Expression<ViewData>
    private lateinit var location: Expression<Location>

    companion object {
        init {
            Skript.registerExpression(
                ExprBlockLocation::class.java,
                Block::class.java, ExpressionType.COMBINED, "[the] block at %location% in view %view%"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Block location of view expression with expression location: ${location.toString(event, debug)} and expression view: ${view.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(expressions: Array<out Expression<*>>?, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        location = expressions!![0] as Expression<Location>
        view = expressions[1] as Expression<ViewData>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Block> {
        return Block::class.java
    }

    override fun get(event: Event?): Array<Block?> {
        if (location.getSingle(event) == null || view.getSingle(event) == null) return arrayOf(null)
        return arrayOf(view.getSingle(event)!!.blocks[Position.block(location.getSingle(event)!!)]!!.createBlockState().block)
    }

}