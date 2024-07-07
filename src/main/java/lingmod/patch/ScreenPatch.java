package lingmod.patch;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;

import basemod.ReflectionHacks;
import lingmod.character.Ling;

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
        @SuppressWarnings("rawtypes")
        @SpireInsertPatch(rloc = 4, localvars = { "list" })
        public static SpireReturn Insert(DeathScreen __inst, ArrayList<String> list) {
            if (AbstractDungeon.player.chosenClass == Ling.Enums.PLAYER_LING) {
                list.clear();
                list.add("酣眠");
                list.add("坛子空了");
                list.add("生皆梦幻，如露似电，无踪泡影");
                list.add("国蚀器锈 如梦似电 无踪泡影");
                logger.info("added death text" + list.toString());
            }
            return SpireReturn.Continue();
        }
    }
}
