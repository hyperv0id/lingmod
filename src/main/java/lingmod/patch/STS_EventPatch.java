package lingmod.patch;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Addict;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;

import basemod.ReflectionHacks;
import lingmod.ModCore;
import lingmod.character.Ling;
import lingmod.util.Wiz;

public class STS_EventPatch {

    /**
     * 个性化 开局对话
     */
    @SpirePatch(clz = NeowEvent.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {boolean.class})
    public static class NeowEventForLing {

        @SpirePrefixPatch
        public static SpireReturn<Void> constructorPatch(NeowEvent __inst) {
            if (Wiz.isPlayerLing()) {
                CharacterStrings neowCS = CardCrawlGame.languagePack
                        .getCharacterString(makeID("Neow Event"));
                logger.info(neowCS);
                String[] TEXT = neowCS.TEXT;
                // for (int i = 0; i < NeowEvent.TEXT.length; i++) {
                ReflectionHacks.setPrivateStaticFinal(NeowEvent.class, "TEXT", TEXT);
                // NeowEvent.TEXT[i] = TEXT[i];
                // }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Addict.class, method = SpirePatch.CONSTRUCTOR)
    public static class PleadingVagrantEventPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> init(Addict __inst) {
            if (AbstractDungeon.player instanceof Ling) {
                EventStrings es = CardCrawlGame.languagePack.getEventString(ModCore.makeID("Addict"));
                ReflectionHacks.setPrivateStaticFinal(Addict.class, "eventStrings", es);
                ReflectionHacks.setPrivateStaticFinal(Addict.class, "NAME", es.NAME);
                ReflectionHacks.setPrivateStaticFinal(Addict.class, "DESCRIPTIONS", es.DESCRIPTIONS);
                ReflectionHacks.setPrivateStaticFinal(Addict.class, "OPTIONS", es.OPTIONS);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = TheLibrary.class, method = SpirePatch.CONSTRUCTOR)
    public static class TheLibraryPatch {
        @SpirePostfixPatch
        public static void init(TheLibrary __inst) {
            if (AbstractDungeon.player instanceof Ling) {
                String imgURL = "TheLibrary.png";
                imgURL = ModCore.makeImagePath("events/" + imgURL);
                __inst.imageEventText.loadImage(imgURL);
            }
        }
    }
}
