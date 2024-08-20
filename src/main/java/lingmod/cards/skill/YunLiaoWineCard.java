package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

@CardConfig(wineAmount = 2, magic = 4)
public class YunLiaoWineCard extends AbstractEasyCard {

    public static final String ID = makeID(YunLiaoWineCard.class.getSimpleName());

    public YunLiaoWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new WinePower(abstractPlayer, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
    }
}
