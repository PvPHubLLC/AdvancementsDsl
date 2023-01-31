package co.pvphub.advancements.dsl

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay
import org.bukkit.entity.Player

class BaseAdvancementBuilder {
    var key: String = "key"
    lateinit var parent: Advancement
    var maxProgression = 1
    lateinit var advancementDisplay: AdvancementDisplay

    private var rewards: ((Player) -> Unit)? = null

    fun rewards(callback: Player.() -> Unit) {
        rewards = callback
    }

    inline fun display(builder: AdvancementBuilder.() -> Unit) {
        advancementDisplay = AdvancementBuilder().apply(builder).build()
    }

    fun defaultParent(parent: Advancement) {
        if (!this::parent.isInitialized)
            this.parent = parent
    }

    fun buildBase(): BaseAdvancement {
        return DummyBaseAdvancement(key, parent, advancementDisplay, rewards, maxProgression)
    }
}

class DummyBaseAdvancement(
    key: String,
    parent: Advancement,
    display: AdvancementDisplay,
    val rewards: ((Player) -> Unit)?,
    maxProgression: Int = 0
) : BaseAdvancement(key, display, parent, maxProgression) {
    override fun giveReward(player: Player) {
        rewards?.invoke(player)
    }
}