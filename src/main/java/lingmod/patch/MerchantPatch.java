package lingmod.patch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.shop.ShopScreen;
import lingmod.ModCore;
import lingmod.util.Wiz;

public class MerchantPatch {

    /**
     * 替换商人的手图为令的
     */
    @SpirePatch(clz = ShopScreen.class, method = "init")
    public static class MerchantHandPatch {
        @SpirePostfixPatch
        public static void Postfix(ShopScreen screen) {
            if (Wiz.isPlayerLing()) {
                String imgPath = ModCore.makeCharacterPath("ling/hand.png");
                Texture handImg = ImageMaster.loadImage(imgPath);
                ReflectionHacks.setPrivateStatic(ShopScreen.class, "handImg", handImg);
                ReflectionHacks.setPrivateStatic(ShopScreen.class, "HAND_W", handImg.getWidth());
                ReflectionHacks.setPrivateStatic(ShopScreen.class, "HAND_H", handImg.getHeight());
            }
        }
    }
}
