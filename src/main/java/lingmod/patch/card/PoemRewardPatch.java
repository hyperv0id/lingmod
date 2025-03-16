package lingmod.patch.card;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import lingmod.cards.AbstractPoetryCard;
import lingmod.util.card.PoemReward;
import lingmod.util.card.PoetryCardLib;

import static lingmod.ModCore.logger;

public class PoemRewardPatch {

    @SpirePatch(clz = AbstractRoom.class, method = "update")
    public static class AddPoetryWithPotion {

        public static float defaultProbability = 0.2f;
        public static float probability = 0.25f;

        /**
         * 添加诗歌奖励，概率随着游戏进行而增加
         *
         * @param __instance 房间实例
         */
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractRoom __instance) {
            if (AbstractDungeon.cardRng.random() <= probability) {
                AbstractPoetryCard pc = (AbstractPoetryCard) PoetryCardLib.getCard(AbstractDungeon.cardRng).makeCopy();
                logger.info("Add Poetry Reward: " + pc.name);
                __instance.rewards.add(new PoemReward(pc));
                probability = defaultProbability;
            } else {
                logger.info("No Poetry Reward");
                probability += 0.1f;
            }
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
