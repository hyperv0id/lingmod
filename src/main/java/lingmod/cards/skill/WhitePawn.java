package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.WhitePawnAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 白子：防0,在梦中时，所有化物增加2格挡
 */
@CardConfig(block = 6, magic = 1)
public class WhitePawn extends AbstractEasyCard {
    public final static String ID = makeID(WhitePawn.class.getSimpleName());

    public WhitePawn() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 4;
        baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard whitePawn = this;
        for (int i = 0; i < magicNumber; i++) {
            blck();
        }
        if (Wiz.isStanceNell()) {
            if (p.drawPile.isEmpty()) addToBot(new DrawCardAction(1)); // 触发洗牌
            addToBot(new WhitePawnAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
        }
        // 检查所有化物技能牌
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}
// "lingmod:WhitePawn": {
// "NAME": "WhitePawn",
// "DESCRIPTION": ""
// }