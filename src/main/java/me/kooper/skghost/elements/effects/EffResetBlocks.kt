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

class EffResetBlocks : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffResetBlocks::class.java,
                "reset blocks of view %view% (of|in) stage %stage%"
            )
        }
    }

    private lateinit var view: Expression<ViewData>
    private lateinit var stage: Expression<Stage>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Reset blocks with string expression view: ${
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
        return true
    }

    override fun execute(event: Event?) {
        if (view.getSingle(event) == null || stage.getSingle(event) == null) return
        stage.getSingle(event)!!.resetBlocks(view.getSingle(event)!!.name)
    }

}