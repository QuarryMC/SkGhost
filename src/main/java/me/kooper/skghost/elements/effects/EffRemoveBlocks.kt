package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.skghost.SkGhost
import org.bukkit.block.Block
import org.bukkit.event.Event

@Suppress("UnstableApiUsage")
class EffRemoveBlocks : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffRemoveBlocks::class.java,
                "remove %block% from %string% (of|in) %string%"
            )
        }
    }

    private lateinit var block: Expression<Block>
    private lateinit var view: Expression<String>
    private lateinit var stage: Expression<String>

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
        view = expressions[1] as Expression<String>
        stage = expressions[2] as Expression<String>
        return true
    }

    override fun execute(event: Event?) {
        if (view.getSingle(event) == null || stage.getSingle(event) == null || block.getSingle(event) == null || SkGhost.instance.ghostCore.stageManager.getStage(
                stage.getSingle(event)!!
            ) == null || SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.views[view.getSingle(
                event
            )!!] == null
        ) return
        SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!.removeBlocks(
            view.getSingle(
                event
            )!!, block.getAll(event)!!.map { b -> Position.block(b.location) }.toSet()
        )
    }

}