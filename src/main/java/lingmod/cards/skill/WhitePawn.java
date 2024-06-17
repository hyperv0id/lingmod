package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.DerivativeMod;
import lingmod.util.Wiz;

/**
 * 白子：防0,在梦中时，所有化物增加2格挡
 */
public class WhitePawn extends AbstractEasyCard {
    public final static String ID = makeID(WhitePawn.class.getSimpleName());

    public WhitePawn() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 4;
        baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (!Wiz.isStanceNell())
            return;
        // 检查所有化物技能牌
        Wiz.allCardsInBattle(false).stream()
                .filter(card -> card.baseBlock != -1)
                .filter(card -> card.type == CardType.SKILL)
                .filter(c -> CardModifierManager.hasModifier(c, DerivativeMod.ID))
                .forEach(c -> {
                    c.baseBlock += magicNumber;
                    c.isBlockModified = true;
                });
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