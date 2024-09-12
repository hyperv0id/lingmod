package lingmod.cards.skill;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.WineMod;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;

import static lingmod.ModCore.makeID;

@CardConfig(wineAmount = 4, magic = 2)
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
public class YunLiaoWineCard extends AbstractEasyCard {

    public static final String ID = makeID(YunLiaoWineCard.class.getSimpleName());

    public YunLiaoWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        CardModifierManager.getModifiers(this, WineMod.ID).stream().filter(mod -> mod instanceof WineMod).findFirst().ifPresent(wm -> ((WineMod) wm).amount += magicNumber);
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new WinePower(abstractPlayer, magicNumber)));
//        addToBot(new ApplyPowerAction(p, p, new WinePower(p, magicNumber)));
    }

}
