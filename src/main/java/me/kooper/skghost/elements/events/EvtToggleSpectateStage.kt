package me.kooper.skghost.elements.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import me.kooper.ghostcore.events.SpectateStageEvent
import me.kooper.ghostcore.models.Stage
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EvtToggleSpectateStage : SkriptEvent() {

    companion object
    {
        init {
            Skript.registerEvent("stage toggle spectate", EvtToggleSpectateStage::class.java, SpectateStageEvent::class.java, "stage spectate")
            EventValues.registerEventValue(
                SpectateStageEvent::class.java,
                Player::class.java, object : Getter<Player?, SpectateStageEvent?>() {
                    override operator fun get(e: SpectateStageEvent?): Player? {
                        if (e == null) return null
                        return e.spectator
                    }
                }, 0
            )
            EventValues.registerEventValue(
                SpectateStageEvent::class.java,
                Stage::class.java, object : Getter<Stage?, SpectateStageEvent?>() {
                    override operator fun get(e: SpectateStageEvent?): Stage? {
                        if (e == null) return null
                        return e.stage
                    }
                }, 0
            )
            EventValues.registerEventValue(
                SpectateStageEvent::class.java,
                String::class.java, object : Getter<String?, SpectateStageEvent?>() {
                    override operator fun get(e: SpectateStageEvent?): String? {
                        if (e == null) return null
                        return if (e.isSpectating) "spectating" else "exiting"
                    }
                }, 0
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Ghost stage spectate event"
    }

    override fun init(args: Array<out Literal<*>>?, matchedPattern: Int, parseResult: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun check(event: Event?): Boolean {
        return true
    }

}