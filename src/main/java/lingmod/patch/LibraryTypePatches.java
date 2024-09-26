package lingmod.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import javassist.CtBehavior;

import static lingmod.ModCore.makeID;

public class LibraryTypePatches {
    public LibraryTypePatches() {
    }

    @SpirePatch(
            cls = "basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Render",
            method = "Insert"
    )
    public static class StatusCardLibraryPatch {
        private static final UIStrings uiStrings;

        public StatusCardLibraryPatch() {
        }

        @SpireInsertPatch(
                locator = TabNameLocator.class,
                localvars = {"tabName"}
        )
        public static void InsertFix(ColorTabBar _instance, SpriteBatch sb, float y, ColorTabBar.CurrentTab curTab, @ByRef String[] tabName) {
            if (tabName[0].equalsIgnoreCase("Ling_poetry")) {
                tabName[0] = uiStrings.TEXT[0];
            }

        }

        static {
            uiStrings = CardCrawlGame.languagePack.getUIString(makeID("LingPoetryColor"));
        }

        private static class TabNameLocator extends SpireInsertLocator {
            private TabNameLocator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }
}
