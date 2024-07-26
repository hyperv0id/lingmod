package lingmod.patch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import lingmod.character.Ling;

import static lingmod.ModCore.makeID;

public class ScreenPatch {
    @SpirePatch(clz = NeowNarrationScreen.class, method = SpirePatch.CONSTRUCTOR)
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

    @SpirePatch(clz = DeathScreen.class, method = "getDeathText")
    public static class DeathScreen_DeathText {

        public static String[] deathTexts = {"酣眠", "坛子空了", "生皆梦幻，如露似电，无踪泡影", "国蚀器锈 如梦似电 无踪泡影"};

        @SpirePostfixPatch
        public static String Insert(String __result, DeathScreen __inst) {
            if (AbstractDungeon.player.chosenClass == Ling.Enums.PLAYER_LING) {
                if (Math.random() > 0) { //todo: change this after test
                    return deathTexts[MathUtils.random(0, deathTexts.length - 1)];
                }
            }
            return __result;
        }
    }
}
