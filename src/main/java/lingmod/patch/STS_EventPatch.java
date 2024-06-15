package lingmod.patch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Addict;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import lingmod.ModCore;
import lingmod.character.Ling;

public class STS_EventPatch {
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
