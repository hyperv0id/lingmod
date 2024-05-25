package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.powers.NoDebuffFromOther;

/**
 * 我是谁？
 * 你不会被敌人DEBUFF到，被自己DEBUFF时失去此能力
 */
public class Whoami_Ling extends AbstractEasyCard {
    public static final String NAME = Whoami_Ling.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Ling() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
    }


    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new NoDebuffFromOther(p)));
    }
}
