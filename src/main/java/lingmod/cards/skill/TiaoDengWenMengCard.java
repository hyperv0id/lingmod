package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.util.TODO;

/**
 * 挑灯问梦：下回合进入幻梦
 * 幻梦：所有手牌无消耗，但你的回合结束后怪物回复其刚刚**损失的一半**的生命值
 */
public class TiaoDengWenMengCard extends AbstractEasyCard {


    public static final String ID = makeID(TiaoDengWenMengCard.class.getSimpleName());
    public TiaoDengWenMengCard(){
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        TODO.info("卡牌效果需修改：随机获得一张梦");
    }

    @Override
    public void upp() {
        exhaust = false;
    }
}