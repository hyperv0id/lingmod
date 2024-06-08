package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.DrinkAlonePower;

import static lingmod.ModCore.makeID;

/**
 * 独酌：下一张卡牌消耗全部能量，打出多次
 */
public class DrinkAlone extends AbstractEasyCard {
    public final static String ID = makeID(DrinkAlone.class.getSimpleName());

    public DrinkAlone() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DrinkAlonePower(p)));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
