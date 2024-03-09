package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.ghostcore.data.PatternData
import me.kooper.ghostcore.models.Stage
import me.kooper.skghost.SkGhost
import me.kooper.skghost.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.Event

class EffCreateView : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffCreateView::class.java,
                "create view (for|in) %stage% (with name|named) %string% (with|from) %locations% with pattern %string% that (1¦is|2¦is(n't| not)) breakable"
            )
        }
    }

    private lateinit var stage: Expression<Stage>
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
        stage = expressions!![0] as Expression<Stage>
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
                stage.createView(
                    name,
                    HashSet(blocks.map { Position.block(it) }),
                    PatternData(Utils.parseMaterialValues(pattern.getSingle(event)!!)),
                    breakable
                )
            }
        })
    }

}