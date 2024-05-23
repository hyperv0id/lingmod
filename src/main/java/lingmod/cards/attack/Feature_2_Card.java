package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractEasyCard;
import lingmod.powers.Feature_2_Power;

/**
 * 随付笺咏醉屠苏: 召唤物被击倒/吸收/回收时令额外获得4(+1)点技力、攻击力+3%（攻击力加成最多叠加5层）
 */
public class Feature_2_Card extends AbstractEasyCard {
    public static final String ID = makeID(Feature_2_Card.class.getSimpleName());

    public Feature_2_Card() {
        super(ID, 5, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        selfRetain = true;
        baseDamage = 4;
        baseMagicNumber = 5;
        baseSecondMagic = 3;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.powers.stream().anyMatch(c -> c.ID == Feature_2_Power.POWER_ID))
            addToBot(new ApplyPowerAction(p, p, new Feature_2_Power(p)));
    }

    @Override
    public boolean canUpgrade() {
        return  timesUpgraded < magicNumber;
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
}
