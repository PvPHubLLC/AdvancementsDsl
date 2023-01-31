package co.pvphub.advancements.dsl

import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI
import org.bukkit.plugin.java.JavaPlugin

object UltimateAdvancementAPIManager {
    private val instances = hashMapOf<JavaPlugin, UltimateAdvancementAPI>()

    fun add(plugin: JavaPlugin, api: UltimateAdvancementAPI) =
        instances.putIfAbsent(plugin, api)

    fun remove(plugin: JavaPlugin) =
        instances.remove(plugin)

    operator fun get(plugin: JavaPlugin) = instances[plugin]
}

fun advancementsApi(plugin: JavaPlugin) =
    UltimateAdvancementAPIManager[plugin]
        ?: UltimateAdvancementAPI.getInstance(plugin).apply {
            UltimateAdvancementAPIManager.add(plugin, this)
        }