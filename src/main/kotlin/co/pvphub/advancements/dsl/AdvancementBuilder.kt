package co.pvphub.advancements.dsl

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType
import com.fren_gor.ultimateAdvancementAPI.advancement.display.FancyAdvancementDisplay
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

open class AdvancementBuilder  {
    var material = Material.STONE
    lateinit var itemStack: ItemStack
    var title = ""
    var description = arrayListOf<String>()
    var type = AdvancementFrameType.TASK
    var showToast = false
    var announceChat = false
    var x: Float = 0f
    var y: Float = 0f

    fun build(): FancyAdvancementDisplay {
        val item = if (this::itemStack.isInitialized) itemStack else ItemStack(material)
        return FancyAdvancementDisplay(
                item,
                title,
                type,
                showToast,
                announceChat,
                x,
                y,
                description
            )
    }
}