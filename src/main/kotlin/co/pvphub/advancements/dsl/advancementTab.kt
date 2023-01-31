package co.pvphub.advancements.dsl

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay
import org.bukkit.plugin.java.JavaPlugin

fun createAdvancementTab(plugin: JavaPlugin, namespace: String) : AdvancementTabBuilder {
    UltimateAdvancementAPI.getInstance(plugin)
        .apply {
            UltimateAdvancementAPIManager.add(plugin, this)
            return AdvancementTabBuilder(createAdvancementTab(namespace))
        }
}

val JavaPlugin.advancementApi: UltimateAdvancementAPI
    get() = advancementsApi(this)

inline fun advancementTab(plugin: JavaPlugin, namespace: String, builder: AdvancementTabBuilder.() -> Unit) =
    createAdvancementTab(plugin, namespace).apply(builder).apply {
        for (entry in advancements) {
            tab.registerAdvancements(entry.key, *entry.value.toTypedArray())
        }
    }.tab