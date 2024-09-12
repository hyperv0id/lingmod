package lingmod.powers;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import lingmod.ModCore;
import lingmod.util.Wiz;

import static lingmod.ModCore.logger;

public class NI3Power extends AbstractEasyPower {

    public static final String ID = ModCore.makeID(NI3Power.class.getSimpleName());
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public NI3Power(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, owner, 3);
        this.priority = 99;
    }

    public float atDamageFinalGive(float damage) {
        float small = damage / 10 * 10 + 3;
        float big = small + 10;
        float val = big;
        if (Math.abs(damage - small) < Math.abs(damage - big)) {
            val = small;
        }
        return val;
    }

    @SpirePatch2(clz = AbstractMonster.class, method = "damage")
    public static class QSWS_Damage_Patch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"damageAmount",})
        public static void Insert(AbstractMonster __instance, @ByRef int[] damageAmount) {
            if (Wiz.adp() == null)
                return;
            NI3Power qsws = (NI3Power) Wiz.adp().getPower(NI3Power.ID);
            if (qsws != null) {
                damageAmount[0] = (int) qsws.atDamageFinalGive(damageAmount[0]);
                __instance.lastDamageTaken = Math.min(damageAmount[0], __instance.currentHealth);
                logger.info("NI3: 伤害变为" + __instance.lastDamageTaken);
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

    @SpirePatch2(clz = AbstractMonster.class, method = "calculateDamage")
    public static class QSWS_Calc_Patch {
        @SpirePostfixPatch
        public static SpireReturn<Integer> Postfix(AbstractMonster __instance, int dmg) {
            if (Wiz.adp() == null)
                return SpireReturn.Continue();
            NI3Power qsws = (NI3Power) Wiz.adp().getPower(NI3Power.ID);
            if (qsws != null) {
                return SpireReturn.Return((int) qsws.atDamageFinalGive(dmg));
            }
            return SpireReturn.Continue();
        }
    }

}
