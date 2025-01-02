package lingmod.patch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import lingmod.ModCore;
import lingmod.util.Wiz;

/**
 * Neow开局对话修改，使用部分诗词代替原有的对话内容
 */
@SpirePatch(clz = NeowEvent.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {boolean.class})
public class NeowEventPatch {
    @SpireInsertPatch(rloc = 109, localvars = {"isDone"})
    public static void Insert(NeowEvent __inst, boolean isDone) {
        if (!Wiz.isPlayerLing()) return;
        // 仅支持中文
        if (!(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT)) return;
        String cid = ModCore.makeID("NeowEventNewBegin");
        CharacterStrings cs = CardCrawlGame.languagePack.getCharacterString(cid);
        String talkText = cs.TEXT[MathUtils.random(0, cs.TEXT.length - 1)];
        ReflectionHacks.RMethod m;
        m = ReflectionHacks.privateMethod(NeowEvent.class, "dismissBubble");
        m.invoke(__inst);
        m = ReflectionHacks.privateMethod(NeowEvent.class, "talk", String.class);
        m.invoke(__inst, talkText);
        //int bossCount = ReflectionHacks.getPrivate(__inst, NeowEvent.class, "bossCount");
        //if (bossCount == 0 && !Settings.isTestingNeow) {
        //    m = ReflectionHacks.privateMethod(NeowEvent.class, "miniBlessing");
        //    m.invoke(__inst);
        //} else {
        //    m = ReflectionHacks.privateMethod(NeowEvent.class, "blessing");
        //    m.invoke(__inst);
        //}
        //return SpireReturn.Return(null);
        //return SpireReturn.Continue();
    }

    //public static class Locator extends SpireInsertLocator {
    //    public Locator() {
    //    }
    //
    //    public int[] Locate(CtBehavior ctBehavior) throws Exception {
    //        Matcher m = new Matcher.MethodCallMatcher(NeowEvent.class, "talk");
    //        String s = (Arrays.toString(LineFinder.findAllInOrder(ctBehavior, m)));
    //        return LineFinder.findInOrder(ctBehavior, m);
    //    }
    //}
}
