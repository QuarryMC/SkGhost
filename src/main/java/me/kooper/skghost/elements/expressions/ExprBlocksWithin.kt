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
class ExprBlocksWithin : SimpleExpression<Location>() {

    private lateinit var location1: Expression<Location>
    private lateinit var location2: Expression<Location>

    companion object {
        init {
            Skript.registerExpression(
                ExprBlocksWithin::class.java,
                Location::class.java, ExpressionType.COMBINED, "[the] ghost locations (within|between) %location% and %location%"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Locations within loc1 and loc2 expression with expression location1: ${location1.toString(event, debug)} and location2: ${location2.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(expressions: Array<out Expression<*>>?, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        location1 = expressions!![0] as Expression<Location>
        location2 = expressions[1] as Expression<Location>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Location> {
        return Location::class.java
    }

    override fun get(event: Event?): Array<Location?> {
        if (location1.getSingle(event) == null || location2.getSingle(event) == null) return arrayOf(null)
        return PositionUtils.getLocationsWithin(Position.block(location1.getSingle(event)!!), Position.block(location2.getSingle(event)!!)).map { it.toLocation(location1.getSingle(event)!!.world) }.toTypedArray()
    }

}