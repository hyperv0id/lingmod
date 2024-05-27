package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;

/**
 * 辞旧岁
 * TODO: 交换弃牌堆、抽牌堆，洗牌后抽一张牌
 */
public class ChiJiuSui extends AbstractEasyCard {
    public static final String ID = makeID(ChiJiuSui.class.getSimpleName());
    public ChiJiuSui() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF );
    }
    @Override
    public void upp() {
        upgradeBaseCost(-1);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster arg1) {

        this.addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
        addToBot(new DrawCardAction(1));
    }

    
}
