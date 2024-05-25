package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractNellaFantasiaCard;

/**
 * 黄粱一梦：获得 70 金币
 */
public class HuangLiang extends AbstractNellaFantasiaCard {

    public static final String ID = makeID(HuangLiang.class.getSimpleName());
    public HuangLiang() {
        super(ID, 1, CardType.SKILL, AbstractCard.CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 70; // 获得 70 金币
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {

    }

    @Override
    public void use_n(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.effectList
                .add(new GainPennyEffect(current_x, current_y));
        AbstractDungeon.player.gainGold(magicNumber);
    }
}
