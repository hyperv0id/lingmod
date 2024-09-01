package lingmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.actions.MyApplyPower_Action;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.Credit;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

/**
 * 笑鸣瑟：每有一种一个敌人或状态牌，获得2点"诗兴"。
 */
@Credit(username = "明日方舟", platform = "鹰角网络", link = "https://prts.wiki/w/令")
public class XiaoMingSeCard extends AbstractEasyCard {

    public static final String ID = makeID(XiaoMingSeCard.class.getSimpleName());

    public XiaoMingSeCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();

        this.isMagicNumberModified = true;
        this.magicNumber = countPoet(AbstractDungeon.player);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int times = countPoet(player);
        addToBot(new MyApplyPower_Action(player, player, new PoeticMoodPower(player,
                times)));
    }

    // TODO: 作为 卡牌变量使用，而不是函数
    protected int countPoet(AbstractPlayer player) {
        long times = 0;
        // 统计异常状态
        times += player.powers.stream().filter(p -> p.type == AbstractPower.PowerType.DEBUFF).count();
        // 统计存活的怪物个数
        times += AbstractDungeon.getMonsters().monsters.stream().filter(mo -> !mo.isDead).count();

        // 统计状态 or 诅咒牌
        if (upgraded) {
            times += player.hand.group.stream().filter(c -> c.type == CardType.CURSE || c.type == CardType.STATUS)
                    .count();
        }
        return (int) times * baseMagicNumber;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
