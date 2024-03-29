package cn.rainbow.oxygen.module.modules.combat

import cn.rainbow.oxygen.module.setting.NumberValue
import cn.rainbow.oxygen.Oxygen
import cn.rainbow.oxygen.module.Category
import cn.rainbow.oxygen.module.Module
import cn.rainbow.oxygen.module.ModuleInfo

@ModuleInfo(name = "Reach", category = Category.Combat)
object Reach : Module() {

    var numberValue = NumberValue("Reach", 3.0, 3.0, 6.0, 0.1)

    @JvmStatic
    fun getReach(): Float {
        return if (Oxygen.INSTANCE.moduleManager.getModule(this.javaClass)!!.enabled) numberValue.currentValue.toFloat() else 3.0f
    }
}