package me.kooper.skghost.elements.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import me.kooper.ghostcore.events.StageCreateEvent
import me.kooper.ghostcore.models.ChunkedStage
import org.bukkit.event.Event

class EvtCreateStage : SimpleEvent() {

    companion object {
        init {
            Skript.registerEvent(
                "Ghost Create Stage",
                EvtCreateStage::class.java,
                StageCreateEvent::class.java,
                "stage create",
                "stage creation",
                "ghost stage create"
            )
            EventValues.registerEventValue(
                StageCreateEvent::class.java,
                ChunkedStage::class.java, object : Getter<ChunkedStage?, StageCreateEvent?>() {
                    override operator fun get(e: StageCreateEvent?): ChunkedStage? {
                        if (e == null) return null
                        return e.stage as ChunkedStage
                    }
                }, 0
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Ghost stage create event"
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