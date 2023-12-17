package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.data.PatternData
import me.kooper.ghostcore.data.ViewData
import me.kooper.ghostcore.models.Stage
import me.kooper.skghost.utils.Utils
import org.bukkit.event.Event

class EffSetPattern : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffSetPattern::class.java,
                "set pattern of view %view% (of|in) stage %stage% to pattern %string%"
            )
        }
    }

    private lateinit var pattern: Expression<String>
    private lateinit var view: Expression<ViewData>
    private lateinit var stage: Expression<Stage>

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
        view = expressions!![0] as Expression<ViewData>
        stage = expressions[1] as Expression<Stage>
        pattern = expressions[2] as Expression<String>
        return true
    }

    override fun execute(event: Event?) {
        if (view.getSingle(event) == null || stage.getSingle(event) == null || pattern.getSingle(event) == null) return
        stage.getSingle(event)!!.changePattern(view.getSingle(event)!!.name, PatternData(Utils.parseMaterialValues(pattern.getSingle(event)!!)))
    }

}