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

class EffResizeView : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffResizeView::class.java,
                "reset view (for|in) %stage% (with name|named) %string% (with|from) %locations% with pattern %string% that (1¦is|2¦is(n't| not)) breakable"
            )
        }
    }

    private lateinit var stage: Expression<ChunkedStage>
    private lateinit var name: Expression<String>
    private var breakable: Boolean = false
    private lateinit var locations: Expression<Location>
    private lateinit var pattern: Expression<String>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Create view with stage expression: ${stage.toString(event, debug)}, name expression: ${
            name.toString(
                event,
                debug
            )
        }, locations ${
            locations.toString(
                event,
                debug
            )
        }, pattern expression ${pattern.toString(event, debug)}, and breakable $breakable"
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
        locations = expressions[2] as Expression<Location>
        pattern = expressions[3] as Expression<String>
        breakable = parser!!.mark == 1
        return true
    }

    @Suppress("UnstableApiUsage")
    override fun execute(event: Event?) {
        val stage = stage.getSingle(event)
        val blocks = locations.getAll(event)
        val name = name.getSingle(event)!!
        Bukkit.getScheduler().runTaskAsynchronously(SkGhost.instance, Runnable {
            run {
                if (stage == null || blocks == null || pattern.getSingle(event) == null) return@Runnable
                val existingView = stage.views[name] ?: return@Runnable
                if (existingView !is ChunkedView) return@Runnable

                val locs = blocks.map { p ->
                    SimplePosition.from(
                        p.blockX,
                        p.blockY,
                        p.blockZ
                    )
                }
                val minLoc: SimplePosition
                val maxLoc: SimplePosition

                if (locs.size > 1) {
                    minLoc = SimplePosition.from(
                        locs.minOf { p -> p.x },
                        locs.minOf { p -> p.y },
                        locs.minOf { p -> p.z }
                    )
                    maxLoc = SimplePosition.from(
                        locs.maxOf { p -> p.x },
                        locs.maxOf { p -> p.y },
                        locs.maxOf { p -> p.z }
                    )
                } else {
                    minLoc = locs[0]
                    maxLoc = locs[0]
                }

                existingView.bound = SimpleBound(minLoc, maxLoc)
                existingView.patternData = PatternData(Utils.parseMaterialValues(pattern.getSingle(event)!!))
                existingView.setBreakable(breakable)
                stage.setAirBlocks(name, existingView.getAllBlocksInBound().keys)
                stage.resetBlocks(name)
            }
        })
    }

}