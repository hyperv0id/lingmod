package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

/**
 * 笑鸣瑟：每有一种一个敌人或状态牌，获得2点"诗兴"。
 */
@CardConfig(magic = 2, magic2 = 0)
@Credit(username = "明日方舟", platform = "鹰角网络", link = "https://prts.wiki/w/令")
public class XiaoMingSeCard extends AbstractEasyCard {

    public static final String ID = makeID(XiaoMingSeCard.class.getSimpleName());

    public XiaoMingSeCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void applyPowers() {
        this.baseSecondMagic = countPoet(AbstractDungeon.player) * baseMagicNumber;
        this.isMagicNumberModified = true;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseSecondMagic = countPoet(AbstractDungeon.player) * baseMagicNumber;
        this.isMagicNumberModified = true;
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int times = countPoet(player);
        addToBot(new ApplyPowerAction(player, player, new PoeticMoodPower(player,
                times)));
    }

    protected int countPoet(AbstractPlayer player) {
        long times = 0;
        // 统计异常状态
        times += player.powers.stream().filter(p -> p.type == AbstractPower.PowerType.DEBUFF).count();
        // 统计存活的怪物个数
        times += AbstractDungeon.getMonsters().monsters.stream().filter(mo -> !mo.isDead).count();

        // 统计 状态/诅咒牌
        if (upgraded) {
            times += player.hand.group.stream().filter(c -> c.type == CardType.CURSE || c.type == CardType.STATUS)
                    .count();
        }
        return (int) times;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
