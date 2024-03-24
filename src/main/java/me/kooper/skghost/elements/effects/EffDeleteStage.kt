package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.skghost.SkGhost
import org.bukkit.event.Event


class EffDeleteStage : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffDeleteStage::class.java,
                "delete stage %stage%"
            )
        }
    }

    private lateinit var stage: Expression<ChunkedStage>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Delete stage with expression stage: ${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        stage = expressions!![0] as Expression<ChunkedStage>
        return true
    }

    override fun execute(event: Event?) {
        if (stage.getSingle(event) == null) return
        SkGhost.instance.ghostCore.stageManager.deleteStage(stage.getSingle(event)!!)
    }

}