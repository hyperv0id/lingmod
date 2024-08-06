package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;

import lingmod.events.CampfireEventManager;
import lingmod.util.EventMaster;

public class CampfirePatch {
    @SpirePatch(clz = CampfireSleepEffect.class, method = "update")
    public static class CampfireSleepPatch {
        public CampfireSleepPatch() {
        }

        public static void Postfix(CampfireSleepEffect __inst) {
            if (__inst.isDone) {
                EventMaster.triggerEvent(CampfireEventManager.rollEvent());
            }
        }
    }
}
