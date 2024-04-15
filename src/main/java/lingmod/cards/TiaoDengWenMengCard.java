package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.powers.NellaFantasiaPower;

import static lingmod.ModCore.makeID;

public class TiaoDengWenMengCard extends AbstractEasyCard {


    public static final String ID = makeID(TiaoDengWenMengCard.class.getSimpleName());
    public TiaoDengWenMengCard(){
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,
                new NellaFantasiaPower(abstractPlayer, 1)
                )
        );
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
