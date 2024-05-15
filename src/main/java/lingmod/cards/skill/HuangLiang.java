package lingmod.cards.skill;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.DreamMod;

import static lingmod.ModCore.makeID;

/**
 * 黄粱一梦：获得 70 金币
 */
public class HuangLiang extends AbstractEasyCard {

    public static final String ID = makeID(HuangLiang.class.getSimpleName());
    public HuangLiang() {
        super(ID, 1, CardType.SKILL, AbstractCard.CardRarity.SPECIAL, CardTarget.SELF);
        purgeOnUse = true;
        this.magicNumber = 70; // 获得 70 金币
        CardModifierManager.addModifier(this, new DreamMod(1));
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
        AbstractDungeon.effectList
                .add(new GainPennyEffect(current_x, current_y));
        AbstractDungeon.player.gainGold(magicNumber);
    }
}
