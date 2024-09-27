package lingmod.patch.card;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;
import lingmod.cards.AbstractPoetryCard;
import lingmod.util.PoetryReward;
import lingmod.util.card.PoetryCardLib;

import static lingmod.ModCore.logger;

public class PoetryRewardPatch {

    @SpirePatch(clz = AbstractRoom.class, method = "update")
    public static class AddPoetryWithPotion {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractRoom __instance) {
            if (AbstractDungeon.cardRng.random() <= 0.2) {
                AbstractPoetryCard pc = (AbstractPoetryCard) PoetryCardLib.getCard(AbstractDungeon.cardRng).makeCopy();
                logger.info("Add Poetry Reward: " + pc.name);
                __instance.rewards.add(new PoetryReward(pc));
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
