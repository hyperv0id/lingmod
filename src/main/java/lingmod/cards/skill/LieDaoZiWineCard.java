package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 列刀子：翻倍你的酒
 */
@CardConfig(wineAmount = 0, magic = 1)
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
public class LieDaoZiWineCard extends AbstractEasyCard {

    public static final String NAME = LieDaoZiWineCard.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public LieDaoZiWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        // CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void applyPowers() {
        int realMagic = baseMagicNumber;
        AbstractPower vp = Wiz.adp().getPower(WinePower.POWER_ID);
        baseMagicNumber = vp == null ? 0 : vp.amount;
        baseMagicNumber *= realMagic;
        super.applyPowers();
        baseMagicNumber = realMagic;
    }


    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realMagic = baseMagicNumber;
        AbstractPower vp = Wiz.adp().getPower(WinePower.POWER_ID);
        baseMagicNumber = vp == null ? 0 : vp.amount;
        baseMagicNumber *= realMagic;
        super.calculateCardDamage(mo);
        baseMagicNumber = realMagic;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new WinePower(abstractPlayer, magicNumber)));
    }
}
