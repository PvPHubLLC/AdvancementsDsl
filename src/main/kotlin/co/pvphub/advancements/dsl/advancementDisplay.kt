package co.pvphub.advancements.dsl

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement

class AdvancementTabBuilder(
    val tab: AdvancementTab
) {
    val advancements = hashMapOf<RootAdvancement, ArrayList<BaseAdvancement>>()

    fun withRoot(key: String = "root", texture: String = "textures/block/stone.png", builder: RootBuilder.() -> Unit) {
        val advancementBuilder = RootAdvancementBuilder().apply {
            this.key = key
            this.texture = texture
        }
        withRoot(advancementBuilder.build(this), builder)
    }

    fun withRoot(rootAdvancement: RootAdvancement, builder: RootBuilder.() -> Unit) {
        advancements.putIfAbsent(rootAdvancement, arrayListOf())
        val root = RootBuilder(rootAdvancement, advancements[rootAdvancement]!!).apply(builder)
        advancements[rootAdvancement]?.addAll(root.advancements)
    }
}

inline fun AdvancementTabBuilder.rootAdvancement(builder: RootAdvancementBuilder.() -> Unit) =
    RootAdvancementBuilder().apply(builder).build(this)

class RootBuilder(val root: RootAdvancement, val advancements: ArrayList<BaseAdvancement>)

inline fun RootBuilder.baseAdvancement(builder: BaseAdvancementBuilder.() -> Unit) : BaseAdvancement =
    BaseAdvancementBuilder().apply(builder).apply {
        defaultParent(root)
    }.buildBase().apply {
        this@baseAdvancement.advancements += this
    }