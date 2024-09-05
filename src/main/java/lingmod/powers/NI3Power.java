package lingmod.powers;

import com.evacipated.cardcrawl.modthespire.lib.*;
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

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = String.format(description, amount);
    }

    @SpirePatch2(clz = AbstractMonster.class, method = "damage")
    public static class QSWS_Patch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"damageAmount",})
        public static void Insert(AbstractMonster __instance, @ByRef int[] damageAmount) {
            ModCore.logger.info("sdjajdask");
            if (Wiz.adp() == null) return;
            AbstractPower qsws = Wiz.adp().getPower(NI3Power.ID);
            if (qsws != null) {
                int amount = qsws.amount;
                damageAmount[0] = damageAmount[0] / amount * amount + (damageAmount[0] % amount != 0 ? amount : 0);
                __instance.lastDamageTaken = Math.min(damageAmount[0], __instance.currentHealth);
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
