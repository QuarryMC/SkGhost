package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.ghostcore.models.ChunkedView
import me.kooper.ghostcore.utils.PatternData
import me.kooper.ghostcore.utils.SimpleBound
import me.kooper.ghostcore.utils.types.SimplePosition
import me.kooper.skghost.SkGhost
import me.kooper.skghost.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.Event

class EffSetViewBound : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffSetViewBound::class.java,
                "set bound of view (for|in) %stage% (with name|named) %string% to [minloc] %location% and [maxloc] %location%"
            )
        }
    }

    private lateinit var stage: Expression<ChunkedStage>
    private lateinit var name: Expression<String>
    private lateinit var minLoc: Expression<Location>
    private lateinit var maxLoc: Expression<Location>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Set bound of view with stage expression: ${stage.toString(event, debug)}, name expression: ${
            name.toString(
                event,
                debug
            )
        }, min location ${
            minLoc.toString(
                event,
                debug
            )
        }, and max location ${maxLoc.toString(event, debug)}"
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
        minLoc = expressions[2] as Expression<Location>
        maxLoc = expressions[3] as Expression<Location>
        return true
    }

    @Suppress("UnstableApiUsage")
    override fun execute(event: Event?) {
        val stage = stage.getSingle(event)
        val name = name.getSingle(event)
        val minLoc = minLoc.getSingle(event)
        val maxLoc = maxLoc.getSingle(event)

        if (stage == null || name == null || minLoc == null || maxLoc == null) return

        Bukkit.getScheduler().runTaskAsynchronously(SkGhost.instance, Runnable {
            run {
                val view: ChunkedView = (stage.views as HashMap<String, ChunkedView>)[name] ?: return@run
                println("View before: ${view.bound}")
                view.bound = SimpleBound(
                    SimplePosition.from(minLoc.blockX, minLoc.blockY, minLoc.blockZ),
                    SimplePosition.from(maxLoc.blockX, maxLoc.blockY, maxLoc.blockZ)
                )
                println("View after: ${view.bound}")
            }
        })
    }

}