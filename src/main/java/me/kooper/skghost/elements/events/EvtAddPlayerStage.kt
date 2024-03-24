package me.kooper.skghost.elements.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import me.kooper.ghostcore.events.JoinStageEvent
import me.kooper.ghostcore.models.ChunkedStage
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EvtAddPlayerStage : SkriptEvent() {

    companion object {
        init {
            Skript.registerEvent(
                "Ghost Add Player Stage",
                EvtAddPlayerStage::class.java,
                JoinStageEvent::class.java,
                "player join stage"
            )
            EventValues.registerEventValue(
                JoinStageEvent::class.java,
                Player::class.java, object : Getter<Player?, JoinStageEvent?>() {
                    override operator fun get(e: JoinStageEvent?): Player? {
                        if (e == null) return null
                        return e.player
                    }
                }, 0
            )
            EventValues.registerEventValue(
                JoinStageEvent::class.java,
                ChunkedStage::class.java, object : Getter<ChunkedStage?, JoinStageEvent?>() {
                    override operator fun get(e: JoinStageEvent?): ChunkedStage? {
                        if (e == null) return null
                        return e.stage as ChunkedStage
                    }
                }, 0
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Ghost add player stage event"
    }

    override fun init(
        args: Array<out Literal<*>>?,
        matchedPattern: Int,
        parseResult: SkriptParser.ParseResult?
    ): Boolean {
        return true
    }

    override fun check(event: Event?): Boolean {
        return true
    }

}