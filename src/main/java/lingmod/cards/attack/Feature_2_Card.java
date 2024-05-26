package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import basemod.BaseMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.Feature_2_Power;

/**
 * 随付笺咏醉屠苏: 召唤物被击倒/吸收/回收时令额外获得4(+1)点技力、攻击力+3%（攻击力加成最多叠加5层）
 */
public class Feature_2_Card extends AbstractEasyCard implements PostExhaustSubscriber {
    public static final String ID = makeID(Feature_2_Card.class.getSimpleName());

    public Feature_2_Card() {
        super(ID, 5, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        CardModifierManager.addModifier(this, new RetainMod());
        baseDamage = 8;
        baseMagicNumber = 5;
        baseSecondMagic = 3;
        BaseMod.subscribe(this);
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded < magicNumber;
    }

    @Override
    public void upp() {
        upgradeDamage(secondMagic);
        updateCost(-1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, null);
    }

    /**
     * @param card 消耗的卡牌
     */
    @Override
    public void receivePostExhaust(AbstractCard card) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null && AbstractDungeon.player.hand.contains(this))
            this.upgrade();
    }
}
