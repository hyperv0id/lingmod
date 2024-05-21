package lingmod.cards.power;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractPoetCard;
import lingmod.powers.XiaoYaoPower;

/**
 * 逍遥游：进入逍遥形态：回合结束后抽牌数+1,也就是说随回合数增加，抽牌数不断增加
 */
public class XiaoYaoYou extends AbstractPoetCard {

    public static final String ID = makeID(XiaoYaoYou.class.getSimpleName());

    public XiaoYaoYou() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        super.use(abstractPlayer, abstractMonster);
        this.addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new XiaoYaoPower(abstractPlayer, 0)));
        this.addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 2F, 2F));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
