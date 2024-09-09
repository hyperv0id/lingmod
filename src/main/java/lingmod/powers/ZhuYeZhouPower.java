package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import lingmod.actions.ExhaustAllAction;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.Wiz;

public class ZhuYeZhouPower extends AbstractEasyPower {

    public static final String POWER_NAME = ZhuYeZhouPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public FlameBarrierPower reference;

    public ZhuYeZhouPower(AbstractPlayer owner) {
        super(ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, 0);
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (oldStance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            addToBotAbstract(() -> {
                for (AbstractCard card : Wiz.adp().hand.group) {
                    addToTop(new MakeTempCardInHandAction(card.makeCopy()));
                }
                addToTop(new ExhaustAllAction());
            });
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            return;
        }
        super.onChangeStance(oldStance, newStance);
    }
}
