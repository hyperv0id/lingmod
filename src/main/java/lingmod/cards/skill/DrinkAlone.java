package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.DrinkAlonePower;
import lingmod.powers.WinePower;

/**
 * 独酌：获得 X*X 酒 NL 下一张卡牌消耗全部能量，打出X次
 */
@CardConfig(magic = 0)
public class DrinkAlone extends AbstractEasyCard {
    public final static String ID = makeID(DrinkAlone.class.getSimpleName());

    public DrinkAlone() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        magicNumber = EnergyPanel.totalCount + baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WinePower(p, magicNumber * magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DrinkAlonePower(p, magicNumber)));
        // addToBot(new GainEnergyAction(EnergyPanel.totalCount));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
