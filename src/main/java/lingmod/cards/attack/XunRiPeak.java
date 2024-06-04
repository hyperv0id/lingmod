package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.powers.PoeticMoodPower;
import lingmod.stance.NellaFantasiaStance;

import static lingmod.ModCore.makeID;

/**
 * 寻日峰：群体4伤害x次，x为能量。如果在梦中：x为能量+诗兴
 */
public class XunRiPeak extends AbstractEasyCard {
    public static final String NAME = XunRiPeak.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private boolean removePoem = false;

    public XunRiPeak() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 4;
        isMultiDamage = true;
        this.magicNumber = 0;
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.magicNumber = energyOnUse;
        AbstractPlayer player = AbstractDungeon.player;
        removePoem = player.stance.ID.equals(NellaFantasiaStance.STANCE_ID);
        if (removePoem) {
            // 计算诗兴
            AbstractPower power = player.getPower(PoeticMoodPower.ID);
            if (power != null)
                magicNumber += power.amount;
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        calculateCardDamage(monster);
        if (removePoem) {
            // 移除诗兴
            this.addToBot(new RemoveSpecificPowerAction(player, player, PoeticMoodPower.ID));
        }
        this.addToBot(new WhirlwindAction(player, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce,
                magicNumber));
    }
}