package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

/**
 * 羽化：造能力牌，其耗能-1
 */
public class AltMetamorphosis extends AbstractEasyCard{

    public static final String ID = makeID(AltMetamorphosis.class.getSimpleName());

    public AltMetamorphosis() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = 2;
        CardModifierManager.addModifier(this, new ExhaustMod());
    }
    
    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
            if(upgraded) {
                addToBot(new UpgradeSpecificCardAction(c));
            }
            c.cost -= 1;
            c.costForTurn -= 1;
            c.isCostModified = true;
            addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
        }
    }
    
    @Override
    public void upp() {
    }
}
