package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractWineCard;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

public class YunLiaoWineCard extends AbstractWineCard {

    public static final String ID = makeID(YunLiaoWineCard.class.getSimpleName());

    public YunLiaoWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = 3;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,
                new WinePower(abstractPlayer, magicNumber)));
    }
}
