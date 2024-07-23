package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.WinePower;
import lingmod.stance.NellaFantasiaStance;

import static lingmod.ModCore.makeID;

/**
 * 杯莫停：如果是攻击，梦，否则 酒
 */
@CardConfig(magic = 4)
public class BeiMoTing extends AbstractEasyCard {
    public final static String ID = makeID(BeiMoTing.class.getSimpleName());

    public BeiMoTing() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.getIntentBaseDmg() >= 0) {
            addToBot(new ChangeStanceAction(new NellaFantasiaStance()));
        } else {
            addToBot(new ApplyPowerAction(p, p, new WinePower(p, magicNumber)));
        }
    }
}