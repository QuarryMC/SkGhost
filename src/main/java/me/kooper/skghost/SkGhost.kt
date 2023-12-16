package me.kooper.skghost

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import me.kooper.ghostcore.GhostCore
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException


class SkGhost : JavaPlugin() {

    lateinit var addon: SkriptAddon
    lateinit var ghostCore: GhostCore

    companion object {
        lateinit var instance: SkGhost
    }

    override fun onEnable() {
        instance = this
        ghostCore = server.pluginManager.getPlugin("GhostCore") as GhostCore
        if (!ghostCore.isEnabled) {

        }
        addon = Skript.registerAddon(this)
        addon.setLanguageFileDirectory("lang")
        try {
            addon.loadClasses("me.kooper.skghost", "elements")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Bukkit.getLogger().info("[GhostSK] has been enabled!")
    }

}