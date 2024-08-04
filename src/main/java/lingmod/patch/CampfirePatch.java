package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import lingmod.events.CampfireEventManager;
import lingmod.util.EventMaster;

import static lingmod.ModCore.logger;

public class CampfirePatch {
    @SpirePatch(clz = CampfireSleepEffect.class, method = "update")
    public static class CampfireSleepPatch {
        public CampfireSleepPatch() {
        }

        public static void Postfix(CampfireSleepEffect __inst) {
            if (__inst.isDone) {
                logger.info("睡觉事件触发");
                // TODO: 没有保存功能
                EventMaster.triggerEvent(CampfireEventManager.rollEvent());
            }
        }
    }
}
