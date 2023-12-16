package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.ghostcore.data.PatternData
import me.kooper.ghostcore.utils.CuboidUtils
import me.kooper.skghost.SkGhost
import me.kooper.skghost.utils.Utils
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffCreateView : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffCreateView::class.java,
                "create view (for|in) %string% (with|for) %players% (with name|named) %string% (between|from) %location% (and|to) %location% with pattern %string% that (1¦is|2¦is(n't| not)) breakable"
            )
        }
    }

    private lateinit var players: Expression<Player>
    private lateinit var stage: Expression<String>
    private lateinit var name: Expression<String>
    private var breakable: Boolean = false
    private lateinit var loc1: Expression<Location>
    private lateinit var loc2: Expression<Location>
    private lateinit var pattern: Expression<String>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Create view with expression players: ${
            players.toString(
                event,
                debug
            )
        }, stage expression: ${stage.toString(event, debug)}, name expression: ${
            name.toString(
                event,
                debug
            )
        }, loc1 expression ${loc1.toString(event, debug)}, loc2 expression ${
            loc2.toString(
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
        stage = expressions!![0] as Expression<String>
        players = expressions[1] as Expression<Player>
        name = expressions[2] as Expression<String>
        loc1 = expressions[3] as Expression<Location>
        loc2 = expressions[4] as Expression<Location>
        pattern = expressions[5] as Expression<String>
        breakable = parser!!.mark == 1
        return true
    }

    @Suppress("UnstableApiUsage")
    override fun execute(event: Event?) {
        if (stage.getSingle(event) == null || players.getSingle(event) == null || name.getSingle(event) == null || loc1.getSingle(
                event
            ) == null || loc2.getSingle(event) == null || pattern.getSingle(event) == null || SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!) == null
        ) return
        val stage = SkGhost.instance.ghostCore.stageManager.getStage(stage.getSingle(event)!!)!!
        val name =  name.getSingle(event)!!
        val loc1 = loc1.getSingle(event)!!
        val loc2 = loc2.getSingle(event)!!
        val pattern = PatternData(Utils.parseMaterialValues(pattern.getSingle(event)!!))
        CuboidUtils.getPositionsBetween(loc1, loc2).thenAccept { positions ->
            SkGhost.instance.server.scheduler.runTask(SkGhost.instance, Runnable {
                run {
                    println(
                        stage.createView(
                            name,
                            HashSet(positions),
                            loc1,
                            loc2,
                            pattern,
                            breakable
                        )
                    )
                }
            })
        }
    }

}