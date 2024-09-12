package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;

import static lingmod.ModCore.makeID;

/**
 * 列刀子：翻倍你的酒
 */
@CardConfig(wineAmount = 1)
@Credit(username = "枯荷倚梅cc", platform = "lofter", link = "https://anluochen955.lofter.com/post/1f2a08fc_2baf8eeb3")
public class LieDaoZiWineCard extends AbstractEasyCard {

    public static final String NAME = LieDaoZiWineCard.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public LieDaoZiWineCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        // CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractPower vp = abstractPlayer.getPower(WinePower.POWER_ID);
        int wineAmt = vp == null ? 0 : vp.amount;
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new WinePower(abstractPlayer, wineAmt)));
        if (upgraded)
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new WinePower(abstractPlayer, wineAmt)));
    }
}
