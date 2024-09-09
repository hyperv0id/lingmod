package lingmod.powers;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import javassist.CtBehavior;
import lingmod.ModCore;
import lingmod.util.Wiz;

public class NI3Power extends AbstractEasyPower {

    public static final String ID = ModCore.makeID(NI3Power.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public NI3Power(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, owner, 3);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
    }

    @SpirePatch2(clz = AbstractMonster.class, method = "damage")
    public static class QSWS_Patch {
        @SpireInsertPatch(locator = Locator.class, localvars = { "damageAmount", })
        public static void Insert(AbstractMonster __instance, @ByRef int[] damageAmount) {
            if (Wiz.adp() == null)
                return;
            AbstractPower qsws = Wiz.adp().getPower(NI3Power.ID);
            if (qsws != null) {
                int small = damageAmount[0] / 10 * 10 + 3;
                int big = small + 10;
                int val = big;
                if (Math.abs(damageAmount[0] - small) < Math.abs(damageAmount[0] - big)) {
                    val = small;
                }
                __instance.lastDamageTaken = Math.min(val, __instance.currentHealth);
            }

        }

        public static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher m = new Matcher.MethodCallMatcher(Math.class, "min");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
