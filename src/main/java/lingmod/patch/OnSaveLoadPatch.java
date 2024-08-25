package lingmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * sl时记录si次数
 */
public class OnSaveLoadPatch {
    public static int saveTimes = 0;
    public static int loadTimes = 0;
    public static int saveThisRoom = 0;
    public static int loadThisRoom = 0;

    @SpirePatch2(clz = ConfirmPopup.class, method = "yesButtonEffect")
    public static class OnSaveTrigger {
        @SpireInsertPatch(locator = OnSaveTriggerLocator.class)
        public static void Trigger() {
            // logger.info("==============================player saved" + saveTimes);
            ++saveTimes;
        }

        public static class OnSaveTriggerLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior behavior)
                    throws PatchingException, CannotCompileException {
                Matcher.MethodCallMatcher matcher =
                        new Matcher.MethodCallMatcher(MusicMaster.class, "fadeAll");
                return LineFinder.findInOrder(behavior, matcher);
            }
        }
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = "loadPlayerSave")
    public static class OnLoadTrigger {
        @SpirePrefixPatch
        public static void Trigger() {
            // logger.info("====================================player loaded" + loadTimes);
            ++loadTimes;
        }
    }
}
