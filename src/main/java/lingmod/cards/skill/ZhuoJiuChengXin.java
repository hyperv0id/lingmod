package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import lingmod.cards.AbstractWineCard;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

;

/**
 * 获得人工制品，升级后获得手牌中 酒牌 数量的人工制品
 */
public class ZhuoJiuChengXin extends AbstractWineCard {
    public static final String ID = makeID(ZhuoJiuChengXin.class.getSimpleName());

    public ZhuoJiuChengXin() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = 1; // 获得 1层人工制品
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (!upgraded)
            addToBot(new ApplyPowerAction(abstractPlayer, abstractMonster,
                    new ArtifactPower(abstractPlayer, magicNumber)));
        else {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (AbstractWineCard.class.isAssignableFrom(c.getClass()) ||
                        c.hasTag(CustomTags.WINE)) {
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractMonster,
                            new ArtifactPower(abstractPlayer, magicNumber)));
                }
            }
        }
    }
}
