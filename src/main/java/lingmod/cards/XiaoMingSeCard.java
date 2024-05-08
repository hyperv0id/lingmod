package lingmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.powers.PoeticMoodPower;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static lingmod.ModCore.makeID;

/**
 * 笑鸣瑟：每有一种一个敌人或状态牌，获得2点"诗意"。
 */
public class XiaoMingSeCard extends AbstractPoetCard {

    public static final String ID = makeID(XiaoMingSeCard.class.getSimpleName());

    public XiaoMingSeCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = 0;
        this.baseMagicNumber = 0;
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();

        this.isMagicNumberModified = true;
        this.magicNumber = countPoet(AbstractDungeon.player);
        this.upgradedMagicNumber = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        super.use(player, monster); // 诗类牌：额外获得诗意
        int times = countPoet(player);
        addToBot(new ApplyPowerAction(player, player, new PoeticMoodPower((AbstractCreature) player,
                times)));
    }


    // TODO: 作为 卡牌变量使用，而不是函数
    protected int countPoet(AbstractPlayer player){
        long times = 0;
        // 统计异常状态
        times += player.powers.stream().filter(p -> p.type == AbstractPower.PowerType.DEBUFF).count();
        // 统计怪物个数
        times += AbstractDungeon.getMonsters().monsters.size();

        // 统计状态 or 诅咒牌
        if (upgraded) {
            times += player.hand.group.stream().filter(c -> c.type == CardType.CURSE || c.type == CardType.STATUS).count();
        }
        return (int) times * 2;
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}
