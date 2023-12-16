package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.data.PatternData
import me.kooper.skghost.SkGhost
import me.kooper.skghost.utils.Utils
import org.bukkit.event.Event

class EffSetPattern : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffAddBlocks::class.java,
                "set pattern of %string% (of|in) %string% to %string%"
            )
        }
    }

    private lateinit var pattern: Expression<String>
    private lateinit var view: Expression<String>
    private lateinit var stage: Expression<String>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Set blocks to pattern: ${
            pattern.toString(
                event,
                debug
            )
        } and string expression view: ${
            view.toString(
                event,
                debug
            )
        }, and string expression stage ${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        view = expressions!![0] as Expression<String>
        stage = expressions[1] as Expression<String>
        pattern = expressions[2] as Expression<String>
        return true
    }

    override fun execute(event: Event?) {
        if (view.getSingle(event) == null || stage.getSingle(event) == null || pattern.getSingle(event) == null
            || SkGhost.instance.ghostCore.stageManager.getStage(
                stage.getSingle(event)!!
            ) == null || SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.views[view.getSingle(
                event
            )!!] == null
        ) return
        SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.changePattern(view.getSingle(event)!!, PatternData(
            Utils.parseMaterialValues(pattern.getSingle(event)!!)))
    }

}