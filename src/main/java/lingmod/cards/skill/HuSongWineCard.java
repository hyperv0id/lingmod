package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import lingmod.cards.AbstractWineCard;

import static lingmod.ModCore.makeID;

/**
 * 4多层护甲
 */
public class HuSongWineCard extends AbstractWineCard {
    public static final String ID = makeID(HuSongWineCard.class.getSimpleName());

    public HuSongWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        baseMagicNumber = 4;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,
                new PlatedArmorPower(abstractPlayer, magicNumber)));
        upgradeMagicNumber(-1);
    }
}
