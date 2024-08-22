package lingmod.cards.poetry;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractPoetryCard;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * TODO: 没有获得事件
 */
public class ShiShuoXinYuCard extends AbstractPoetryCard {
    public static final String ID = makeID(ShiShuoXinYuCard.class.getSimpleName());

    public ShiShuoXinYuCard() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        for (int i = p.powers.size() - 1; i >= 0; i--) {
            AbstractPower power = p.powers.get(i);
            if (Wiz.isVisibleDebuff(power)) {
                addToBot(new GainEnergyAction(1));
                addToBot(new RemoveSpecificPowerAction(p, p, power));
            }
        }
    }
}
