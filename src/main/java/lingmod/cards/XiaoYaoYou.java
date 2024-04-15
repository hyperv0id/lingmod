package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.powers.PoeticMoodPower;
import lingmod.stances.XiaoYaoStance;

import static lingmod.ModCore.makeID;

/**
 * 进入逍遥形态：回合结束后抽牌数+1,也就是说随回合数增加，抽牌数不断增加
 */
public class XiaoYaoYou extends AbstractPoetCard {

    public static final String ID = makeID(XiaoYaoYou.class.getSimpleName());
    public XiaoYaoYou(){
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        super.use(abstractPlayer, abstractMonster);
        this.addToBot(new ChangeStanceAction(new XiaoYaoStance()));
    }

    @Override
    public void upp() {
        updateCost(-1);
    }
}
