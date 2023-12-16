package me.kooper.skghost.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.kooper.skghost.SkGhost
import org.bukkit.event.Event


class EffDeleteStage : Effect() {

    companion object {
        init {
            Skript.registerEffect(
                EffDeleteStage::class.java,
                "delete stage (with|with name|named) %string%"
            )
        }
    }

    private lateinit var name: Expression<String>

    override fun toString(event: Event?, debug: Boolean): String {
        return "Delete stage with expression name: ${name.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(expressions: Array<out Expression<*>>?, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        name = expressions!![0] as Expression<String>
        return true
    }

    override fun execute(event: Event?) {
        if (name.getSingle(event) == null) return
        SkGhost.instance.ghostCore.stageManager.deleteStage(name.getSingle(event)!!)
    }

}