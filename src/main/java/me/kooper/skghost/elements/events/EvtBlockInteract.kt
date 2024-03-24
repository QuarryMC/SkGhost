package me.kooper.skghost.elements.events

import ch.njol.skript.Skript
import ch.njol.skript.aliases.ItemType
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import me.kooper.ghostcore.events.GhostInteractEvent
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.ghostcore.models.ChunkedView
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event

@Suppress("UnstableApiUsage")
class EvtBlockInteract : SimpleEvent() {

    companion object {
        init {
            Skript.registerEvent(
                "Ghost Block Interact",
                EvtBlockInteract::class.java,
                GhostInteractEvent::class.java,
                "ghost block interact"
            )
            EventValues.registerEventValue(
                GhostInteractEvent::class.java,
                Player::class.java, object : Getter<Player?, GhostInteractEvent?>() {
                    override operator fun get(e: GhostInteractEvent?): Player? {
                        if (e == null) return null
                        return e.player
                    }
                }, 0
            )
            EventValues.registerEventValue(
                GhostInteractEvent::class.java,
                ItemType::class.java, object : Getter<ItemType?, GhostInteractEvent?>() {
                    override operator fun get(e: GhostInteractEvent?): ItemType? {
                        if (e == null) return null
                        return ItemType(e.blockData.material)
                    }
                }, 0
            )
            EventValues.registerEventValue(
                GhostInteractEvent::class.java,
                ChunkedStage::class.java, object : Getter<ChunkedStage?, GhostInteractEvent?>() {
                    override operator fun get(e: GhostInteractEvent?): ChunkedStage? {
                        if (e == null) return null
                        return e.stage as ChunkedStage
                    }
                }, 0
            )
            EventValues.registerEventValue(
                GhostInteractEvent::class.java,
                ChunkedView::class.java, object : Getter<ChunkedView?, GhostInteractEvent?>() {
                    override operator fun get(e: GhostInteractEvent?): ChunkedView? {
                        if (e == null) return null
                        return e.view as ChunkedView
                    }
                }, 0
            )
            EventValues.registerEventValue(
                GhostInteractEvent::class.java,
                Location::class.java, object : Getter<Location?, GhostInteractEvent?>() {
                    override operator fun get(e: GhostInteractEvent?): Location? {
                        if (e == null) return null
                        return e.position.toLocation(e.stage.world)
                    }
                }, 0
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Ghost block interact event"
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