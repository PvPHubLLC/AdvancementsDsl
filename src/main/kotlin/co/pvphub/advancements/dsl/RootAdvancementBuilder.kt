package co.pvphub.advancements.dsl

import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay

open class RootAdvancementBuilder {
    var key = "root"
    var texture = "textures/block/stone.png"
    lateinit var advancementDisplay: AdvancementDisplay

    inline fun display(builder: AdvancementBuilder.() -> Unit) {
        advancementDisplay = AdvancementBuilder().apply(builder).build()
    }

    fun build(tab: AdvancementTabBuilder): RootAdvancement {
        return RootAdvancement(tab.tab, key, advancementDisplay, texture)
    }
}