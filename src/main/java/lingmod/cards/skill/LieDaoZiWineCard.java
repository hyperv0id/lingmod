package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import lingmod.cards.AbstractWineCard;
import lingmod.powers.WinePower;

/**
 * 列刀子：翻倍你的酒
 */
public class LieDaoZiWineCard extends AbstractWineCard {

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
        AbstractPower wp = abstractPlayer.getPower(WinePower.POWER_ID);
        int wineAmount = wp == null ? 0 : wp.amount;
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new WinePower(abstractPlayer, wineAmount)));
    }
}
