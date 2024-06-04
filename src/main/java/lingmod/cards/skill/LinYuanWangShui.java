package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.WangShuiPower;

import static lingmod.ModCore.makeID;

/**
 * 临渊忘水：抽3/4张，回合结束消耗所有
 */
public class LinYuanWangShui extends AbstractEasyCard {
    public static final String ID = makeID(LinYuanWangShui.class.getSimpleName());

    public LinYuanWangShui() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer arg0, AbstractMonster arg1) {
        addToBot(new DrawCardAction(magicNumber));
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new WangShuiPower(p)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
    
}
