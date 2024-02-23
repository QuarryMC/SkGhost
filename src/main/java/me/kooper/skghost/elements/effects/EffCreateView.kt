package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import io.papermc.paper.math.Position
import me.kooper.ghostcore.data.PatternData
import me.kooper.ghostcore.models.Stage
import me.kooper.skghost.utils.Utils
import org.bukkit.block.Block
import org.bukkit.event.Event

class EffCreateView : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffCreateView::class.java,
                "create view (for|in) %stage% (with name|named) %string% (with|from) %blocks% with pattern %string% that (1¦is|2¦is(n't| not)) breakable"
            )
        }
    }

    private lateinit var stage: Expression<Stage>
    private lateinit var name: Expression<String>
    private var breakable: Boolean = false
    private lateinit var blocks: Expression<Block>
    private lateinit var pattern: Expression<String>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Create view with stage expression: ${stage.toString(event, debug)}, name expression: ${
            name.toString(
                event,
                debug
            )
        }, blocks ${
            blocks.toString(
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
        blocks = expressions[2] as Expression<Block>
        pattern = expressions[3] as Expression<String>
        breakable = parser!!.mark == 1
        return true
    }

    @Suppress("UnstableApiUsage")
    override fun execute(event: Event?) {
        if (stage.getSingle(event) == null || name.getSingle(event) == null || blocks.getAll(event) == null || pattern.getSingle(event) == null) return
        stage.getSingle(event)!!.createView(
            name.getSingle(event)!!,
            HashSet(blocks.getAll(event)!!.map { block -> Position.block(block.location) }),
            PatternData(Utils.parseMaterialValues(pattern.getSingle(event)!!)),
            breakable
        )
    }

}