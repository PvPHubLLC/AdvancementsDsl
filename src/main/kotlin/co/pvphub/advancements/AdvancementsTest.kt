package co.pvphub.advancements

import co.pvphub.advancements.dsl.*
import com.fren_gor.ultimateAdvancementAPI.AdvancementMain
import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent
import com.mattmx.ktgui.commands.simpleCommand
import com.mattmx.ktgui.dsl.event
import com.mattmx.ktgui.extensions.color
import io.papermc.paper.event.block.BlockBreakBlockEvent
import org.bukkit.Material
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.min

class AdvancementsTest : JavaPlugin() {
    private lateinit var advancementMain: AdvancementMain

    override fun onLoad() {
        advancementMain = AdvancementMain(this)
        advancementMain.load()
    }

    override fun onEnable() {
        instance = this
        advancementMain.enableInMemory()

        val tab = advancementTab(this, "versus_test") {
            val root = rootAdvancement {
                key = "root"
                texture = "textures/block/netherite_block.png"
                display {
                    material = Material.GRASS_BLOCK
                    title = "&cExample Root".color()
                    announceChat = false
                    description += "&fDo something init".color()
                }
            }

            withRoot(root) {
                val mine = baseAdvancement {
                    maxProgression = 2
                    key = "min_2"
                    display {
                        material = Material.DIAMOND_SWORD
                        title = "Mine"
                        x = 2f
                    }
                }

                val second = baseAdvancement {
                    key = "third"
                    parent = mine
                    display {
                        material = Material.NETHERITE_SWORD
                        title = "Third"
                        x = 4f
                    }
                }

                baseAdvancement {
                    key = "fork"
                    parent = mine
                    display {
                        material = Material.NETHER_STAR
                        title = "Fork!"
                        x = 4f
                        y = 1f
                    }
                }

                event<BlockBreakEvent> {
                    mine.incrementProgression(player)
                }
            }
        }

        event<PlayerLoadingCompletedEvent> {
            tab.showTab(player)
        }

        simpleCommand {
            name = "testadvancement"
            executes {
                tab.grantRootAdvancement(it.player(), true)
                for (advancement in tab.advancements) {
                    if (advancement.maxProgression != 0)
                        advancement.setProgression(it.player(), advancement.maxProgression)
                    else {
                        advancement.grant(it.player(), false)
                    }
                }
            }
        }.register(false)
    }

    override fun onDisable() {
        advancementMain.disable()
    }

    companion object {
        private lateinit var instance: AdvancementsTest
        fun get() = instance
    }

}