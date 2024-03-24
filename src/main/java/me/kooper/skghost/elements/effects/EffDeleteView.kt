package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.ghostcore.models.ChunkedView
import me.kooper.ghostcore.utils.PatternData
import me.kooper.ghostcore.utils.types.SimplePosition
import me.kooper.skghost.SkGhost
import me.kooper.skghost.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.Event

class EffDeleteView : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffDeleteView::class.java,
                "delete view (for|in) %stage% (with name|named) %string%"
            )
        }
    }

    private lateinit var stage: Expression<ChunkedStage>
    private lateinit var name: Expression<String>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Delete view with stage expression: ${stage.toString(event, debug)}, name expression: ${
            name.toString(
                event,
                debug
            )
        }"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult?
    ): Boolean {
        stage = expressions!![0] as Expression<ChunkedStage>
        name = expressions[1] as Expression<String>
        return true
    }

    @Suppress("UnstableApiUsage")
    override fun execute(event: Event?) {
        val stage = stage.getSingle(event)
        val name = name.getSingle(event)

        if (stage == null || name == null) return

        val view = stage.views[name] ?: return
        stage.deleteView(name)
    }

}