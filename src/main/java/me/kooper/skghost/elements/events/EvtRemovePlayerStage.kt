package me.kooper.skghost.elements.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import me.kooper.ghostcore.events.LeaveStageEvent
import me.kooper.ghostcore.models.Stage
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EvtRemovePlayerStage : SimpleEvent() {

    companion object
    {
        init {
            Skript.registerEvent("Ghost Leave Stage", EvtRemovePlayerStage::class.java, LeaveStageEvent::class.java, "player leave stage")
            EventValues.registerEventValue(
                LeaveStageEvent::class.java,
                Stage::class.java, object : Getter<Stage?, LeaveStageEvent?>() {
                    override operator fun get(e: LeaveStageEvent?): Stage? {
                        if (e == null) return null
                        return e.stage
                    }
                }, 0
            )
            EventValues.registerEventValue(
                LeaveStageEvent::class.java,
                Player::class.java, object : Getter<Player?, LeaveStageEvent?>() {
                    override operator fun get(e: LeaveStageEvent?): Player? {
                        if (e == null) return null
                        return e.player
                    }
                }, 0
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Ghost player leave stage event"
    }

    override fun init(args: Array<out Literal<*>>?, matchedPattern: Int, parseResult: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun check(event: Event?): Boolean {
        return true
    }

}