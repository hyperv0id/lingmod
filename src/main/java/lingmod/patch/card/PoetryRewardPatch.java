package lingmod.patch.card;

import static lingmod.ModCore.logger;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import javassist.CtBehavior;
import lingmod.cards.AbstractPoetryCard;
import lingmod.util.PoetryReward;
import lingmod.util.card.PoetryCardLib;

public class PoetryRewardPatch {

    @SpirePatch(clz = AbstractRoom.class, method = "update")
    public static class AddPoetryWithPotion {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractRoom __instance) {
            AbstractPoetryCard pc = (AbstractPoetryCard) PoetryCardLib.getCard(AbstractDungeon.cardRng).makeCopy();
            logger.info("添加诗词奖励：" + pc.name);
            __instance.rewards.add(new PoetryReward(pc));
        }

        public static class Locator extends SpireInsertLocator {
            public Locator() {
            }

            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractRoom.class, "addPotionToRewards");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

}
