package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;

import static lingmod.ModCore.makeID;

/**
 * 我是谁？
 * TODO: 敌人失去50%生命，生成一个怪物 墨魉
 */
public class Whoami_Dusk extends AbstractEasyCard {
    public static final String NAME = Whoami_Dusk.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Dusk() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new ApplyPowerAction(p, p, new NoDebuffFromOther(p)));
    }
}