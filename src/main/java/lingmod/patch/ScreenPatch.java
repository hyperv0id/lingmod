package lingmod.patch;

import static lingmod.ModCore.makeID;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.localization.CharacterStrings;

import basemod.ReflectionHacks;

public class ScreenPatch {
    @SpirePatch(clz= NeowNarrationScreen.class, method = SpirePatch.CONSTRUCTOR)
    public static class NeowNarrationScreenPatch {
        @SuppressWarnings("rawtypes")
        @SpirePrefixPatch
        public static SpireReturn constructorPatch(NeowNarrationScreen __inst) {
            // 替换碎心后尼奥对话
            CharacterStrings cs = CardCrawlGame.languagePack
                    .getCharacterString(makeID("LingHeartKill"));
            ReflectionHacks.setPrivateStaticFinal(NeowNarrationScreen.class, "charStrings", cs);
            return SpireReturn.Continue();
        }
    }
}
