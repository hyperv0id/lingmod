package lingmod.patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import lingmod.ui.PoetryOrb;

@SuppressWarnings("unused")
public class PoetryOrbPatch {

    /**
     * 渲染角色时，同时渲染诗词
     */
    @SpirePatch(clz = AbstractPlayer.class, method = "render")
    public static class RenderPoetryOrbPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            PoetryOrb orb = PlayerFieldsPatch.poetryOrb.get(__instance);
            if (orb == null) return;
            // 只在战斗中渲染诗词orb
            if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !__instance.isDead) {
                orb.render(sb);
            }
        }
    }

    /**
     * 角色更新时，同时更新诗词orb位置
     */
    @SpirePatch(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class UpdatePoetryOrbPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            // 更新诗词orb
            PoetryOrb orb = PlayerFieldsPatch.poetryOrb.get(__instance);
            if (orb != null) {
                orb.update();
            }
        }
    }
}
