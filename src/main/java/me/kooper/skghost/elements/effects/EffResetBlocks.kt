package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.ghostcore.models.ChunkedView
import me.kooper.skghost.SkGhost
import org.bukkit.Bukkit
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

    private lateinit var view: Expression<ChunkedView>
    private lateinit var stage: Expression<ChunkedStage>

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
        view = expressions!![0] as Expression<ChunkedView>
        stage = expressions[1] as Expression<ChunkedStage>
        return true
    }

    override fun execute(event: Event?) {
        val stage = stage.getSingle(event)
        val view = view.getSingle(event)
        Bukkit.getScheduler().runTaskAsynchronously(SkGhost.instance, Runnable {
            run {
                if (view == null || stage == null) return@Runnable
                stage.resetBlocks(view.name)
            }
        })
    }

}