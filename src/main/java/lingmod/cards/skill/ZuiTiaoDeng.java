package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 增强版哨位：获得5/8格挡。被消耗时获得 3E
 */
@CardConfig(block = 5)
public class ZuiTiaoDeng extends AbstractEasyCard {
    public final static String ID = makeID(ZuiTiaoDeng.class.getSimpleName());

    public ZuiTiaoDeng() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(Wiz.adp(), block));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        addToBot(new GainEnergyAction(3));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        addToBot(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}
// "lingmod:ZuiTiaoDeng": {
// "NAME": "ZuiTiaoDeng",
// "DESCRIPTION": ""
// }