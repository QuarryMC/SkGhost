package me.kooper.skghost.elements.events

import ch.njol.skript.Skript
import ch.njol.skript.aliases.ItemType
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import me.kooper.ghostcore.data.ViewData
import me.kooper.ghostcore.events.GhostBreakEvent
import me.kooper.ghostcore.models.Stage
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event


@Suppress("UnstableApiUsage")
class EvtBlockBreak : SkriptEvent() {

    companion object
    {
       init {
           Skript.registerEvent("Ghost Block Break", EvtBlockBreak::class.java, GhostBreakEvent::class.java, "ghost block break")
           EventValues.registerEventValue(
               GhostBreakEvent::class.java,
               Player::class.java, object : Getter<Player?, GhostBreakEvent?>() {
                   override operator fun get(e: GhostBreakEvent?): Player? {
                       if (e == null) return null
                       return e.player
                   }
               }, 0
           )
           EventValues.registerEventValue(
               GhostBreakEvent::class.java,
               ItemType::class.java, object : Getter<ItemType?, GhostBreakEvent?>() {
                   override operator fun get(e: GhostBreakEvent?): ItemType? {
                       if (e == null) return null
                       return ItemType(e.blockData.material)
                   }
               }, 0
           )
           EventValues.registerEventValue(
               GhostBreakEvent::class.java,
               Stage::class.java, object : Getter<Stage?, GhostBreakEvent?>() {
                   override operator fun get(e: GhostBreakEvent?): Stage? {
                       if (e == null) return null
                       return e.stage
                   }
               }, 0
           )
           EventValues.registerEventValue(
               GhostBreakEvent::class.java,
               ViewData::class.java, object : Getter<ViewData?, GhostBreakEvent?>() {
                   override operator fun get(e: GhostBreakEvent?): ViewData? {
                       if (e == null) return null
                       return e.view
                   }
               }, 0
           )
           EventValues.registerEventValue(
               GhostBreakEvent::class.java,
               Location::class.java, object : Getter<Location?, GhostBreakEvent?>() {
                   override operator fun get(e: GhostBreakEvent?): Location? {
                       if (e == null) return null
                       return e.position.toLocation(e.stage.world)
                   }
               }, 0
           )
       }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "Ghost block break event"
    }

    override fun init(args: Array<out Literal<*>>?, matchedPattern: Int, parseResult: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun check(event: Event?): Boolean {
        return true
    }

}