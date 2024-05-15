package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import lingmod.cards.AbstractWineCard;
import lingmod.util.CustomTags;;

public class ZhuoJiuChengXin extends AbstractWineCard {
    public static final String ID = makeID(ZhuoJiuChengXin.class.getSimpleName());

    public ZhuoJiuChengXin() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = 1; // 获得 1层人工制品
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        super.use(abstractPlayer, abstractMonster);
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
