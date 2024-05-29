package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;

/**
 * 黄粱一梦：获得 70 金币
 */
public class HuangLiang extends AbstractEasyCard {

    public static final String ID = makeID(HuangLiang.class.getSimpleName());
    public HuangLiang() {
        super(ID, 1, CardType.SKILL, AbstractCard.CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 15; // 金币数量
        CardModifierManager.addModifier(this, new ExhaustMod());
        CardModifierManager.addModifier(this, new NellaFantasiaMod());
    }

    @Override
    public void upp() {
        upgradeMagicNumber(5);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.effectList
                .add(new GainPennyEffect(current_x, current_y));
        AbstractDungeon.player.gainGold(magicNumber);
    }
}
