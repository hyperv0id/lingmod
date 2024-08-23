package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

@CardConfig(wineAmount = 2, magic = 4)
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
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
