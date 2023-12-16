package me.kooper.skghost.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.kooper.ghostcore.models.Stage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event

class ExprStageAudience : SimpleExpression<Player>() {

    private lateinit var stage: Expression<Stage>

    companion object {
        init {
            Skript.registerExpression(
                ExprStageAudience::class.java,
                Player::class.java, ExpressionType.SIMPLE, "[the] audience of %stage%", "%stage%('s) audience"
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Audience of stage expression with expression view: ${stage.toString(event, debug)}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(expressions: Array<out Expression<*>>?, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        stage = expressions!![0] as Expression<Stage>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Player> {
        return Player::class.java
    }

    override fun get(event: Event?): Array<Player?> {
        if (stage.getSingle(event) == null) return arrayOf(null)
        return stage.getSingle(event)!!.audience.map { player -> Bukkit.getPlayer(player) }.toTypedArray()

    }


}