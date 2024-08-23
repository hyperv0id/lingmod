package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.CustomTags;

/**
 * 获得人工制品，升级后获得手牌中 酒牌 数量的人工制品
 */
@Credit(username = "阿尼鸭👀Any-a", platform = Credit.LOFTER, link = "https://anyaaaaa.lofter.com/post/1d814764_2b82a4328")
@CardConfig(wineAmount = 2, magic = 1)
public class ZhuoJiuChengXin extends AbstractEasyCard {
    public static final String ID = makeID(ZhuoJiuChengXin.class.getSimpleName());

    public ZhuoJiuChengXin() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
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
                if (c.hasTag(CustomTags.WINE)) {
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractMonster,
                            new ArtifactPower(abstractPlayer, magicNumber)));
                }
            }
        }
    }
}
