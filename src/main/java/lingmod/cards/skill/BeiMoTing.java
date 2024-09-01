package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;
import lingmod.stance.NellaFantasiaStance;

import static lingmod.ModCore.makeID;

/**
 * 杯莫停：如果是攻击，梦，否则 酒
 */
@CardConfig(magic = 2)
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
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
            addToBot(new MyApplyPower_Action(p, p, new WinePower(p, magicNumber)));
        }
    }
}