package me.kooper.skghost.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.skghost.SkGhost
import org.bukkit.event.Event

class CondStageExists : Condition() {

    companion object {
        init {
            Skript.registerCondition(
                CondStageExists::class.java,
                "stage with name %string% (1¦does|2¦does(n't| not)) exist"
            )
        }
    }

    private lateinit var stage: Expression<String>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        stage = expressions[0] as Expression<String>
        isNegated = parser!!.mark == 1
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Stage exists: ${stage.toString(event, debug)}"
    }

    override fun check(event: Event?): Boolean {
        if (stage.getSingle(event) == null) return isNegated
        return if (SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!) != null) isNegated else !isNegated
    }

}