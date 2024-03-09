package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.ghostcore.utils.PositionUtils
import org.bukkit.Location
import org.bukkit.event.Event


@Suppress("UnstableApiUsage")
class ExprBlocksRadius : SimpleExpression<Location>() {

    private lateinit var center: Expression<Location>
    private lateinit var radius: Expression<Int>

    companion object {
        init {
            Skript.registerExpression(
                ExprBlocksRadius::class.java,
                Location::class.java, ExpressionType.COMBINED, "[the] ghost locations in radius %integer% around %location%"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Locations in radius expression with expression center: ${center.toString(event, debug)} and radius: ${radius.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(expressions: Array<out Expression<*>>?, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        radius = expressions!![0] as Expression<Int>
        center = expressions[1] as Expression<Location>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Location> {
        return Location::class.java
    }

    override fun get(event: Event?): Array<Location?> {
        if (center.getSingle(event) == null || radius.getSingle(event) == null) return arrayOf(null)
        return PositionUtils.getLocationsInRadius(Position.block(center.getSingle(event)!!), radius.getSingle(event)!!).map { it.toLocation(center.getSingle(event)!!.world) }.toTypedArray()
    }

}