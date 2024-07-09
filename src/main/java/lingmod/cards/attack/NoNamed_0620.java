package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.AutoAdd.Ignore;
import lingmod.cards.AbstractEasyCard;
@Ignore
public class NoNamed_0620 extends AbstractEasyCard {
    public final static String ID = makeID(NoNamed_0620.class.getSimpleName());

    public NoNamed_0620() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 11;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(null);
        int cnt = (int) p.hand.group.stream().filter(c -> c.costForTurn >= this.costForTurn).count();
        addToBot(new GainEnergyAction(cnt));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}
