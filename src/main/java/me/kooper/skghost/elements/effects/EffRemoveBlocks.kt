package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.ghostcore.data.ViewData
import me.kooper.ghostcore.models.Stage
import org.bukkit.block.Block
import org.bukkit.event.Event

@Suppress("UnstableApiUsage")
class EffRemoveBlocks : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffRemoveBlocks::class.java,
                "remove %blocks% from view %view% (of|in) stage %stage%"
            )
        }
    }

    private lateinit var block: Expression<Block>
    private lateinit var view: Expression<ViewData>
    private lateinit var stage: Expression<Stage>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Remove blocks to view with expression block: ${
            block.toString(
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
        block = expressions!![0] as Expression<Block>
        view = expressions[1] as Expression<ViewData>
        stage = expressions[2] as Expression<Stage>
        return true
    }

    override fun execute(event: Event?) {
        if (view.getSingle(event) == null || stage.getSingle(event) == null || block.getAll(event) == null) return
        stage.getSingle(event)!!.removeBlocks(
            view.getSingle(
                event
            )!!.name, block.getAll(event)!!.map { b -> Position.block(b.location) }.toSet()
        )
    }

}