package me.kooper.skghost.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedView
import org.bukkit.event.Event

class CondViewBreakable : Condition() {

    companion object {
        init {
            Skript.registerCondition(
                CondViewBreakable::class.java,
                "view %view% (1¦is|2¦is(n't| not)) breakable"
            )
        }
    }

    private lateinit var view: Expression<ChunkedView>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        view = expressions[0] as Expression<ChunkedView>
        isNegated = parser!!.mark == 1
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "View is breakable: ${
            view.toString(
                event,
                debug
            )
        }"
    }

    override fun check(event: Event?): Boolean {
        if (view.getSingle(event) == null) return isNegated
        return if (view.getSingle(event)!!.isBreakable()) isNegated else !isNegated
    }


}