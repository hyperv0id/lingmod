package lingmod.patch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import lingmod.ui.SkinSelectScreen;

import static lingmod.character.Ling.Enums.PLAYER_LING;

public class SkinSelectPatch {
    public static boolean isMizukiSelected() {
        return CardCrawlGame.chosenCharacter == PLAYER_LING && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected");
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class RenderButtonPatch {
        public RenderButtonPatch() {
        }

        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (SkinSelectPatch.isMizukiSelected()) {
                SkinSelectScreen.getInstance().render(sb);
            }

        }

        @SpirePatch(
                clz = CharacterSelectScreen.class,
                method = "update"
        )
        public static class UpdateButtonPatch {
            public UpdateButtonPatch() {
            }

            public static void Prefix(CharacterSelectScreen _inst) {
                if (SkinSelectPatch.isMizukiSelected()) {
                    SkinSelectScreen.getInstance().update();
                }

            }
        }
    }

}
