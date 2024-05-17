package lingmod.patch;

import static lingmod.ModCore.makeID;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.localization.CharacterStrings;

import basemod.ReflectionHacks;

/**
 * 添加角色对话，替代/新增 尼奥对话上
 */
@SpirePatch(clz = NeowNarrationScreen.class, method = "open")
public class CreditScreenPatch {

    public static final CharacterStrings cs = CardCrawlGame.languagePack
            .getCharacterString(makeID("LingHeartKill"));

    @SpirePrefixPatch
    public static void Patch(final NeowNarrationScreen __instance) {
        ReflectionHacks.setPrivateStaticFinal(NeowNarrationScreen.class, "charStrings", cs);
    }
    // logger.info("-------------Hacking Neow");
    // if (!(boolean) ReflectionHacks.getPrivate(__instance, CreditsScreen.class,
    // "showNeowAfter"))
    // return;
    // logger.info("-------------Neow Say");
    // // if (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass
    // == PLAYER_LING) {
    // CardCrawlGame.music.justFadeOutTempBGM();
    // CardCrawlGame.mainMenuScreen.screen =
    // com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.NEOW_SCREEN;
    // LingNarrationScreen screen = new LingNarrationScreen();
    // logger.info("-------------Ling Say");
    // screen.open();
    // // }
    // }
}
