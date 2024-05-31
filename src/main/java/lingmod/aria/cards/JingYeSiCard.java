package lingmod.aria.cards;

import static lingmod.ModCore.makeID;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import lingmod.powers.PoeticMoodPower;

/**
 * 普通五言：床前明月光，疑是地上霜，举头望明月，低头思故乡
 * "给予 !M! 虚弱",
 * "给予 !M! 易伤",
 * "NL 造成 !D! 点伤害",
 * "获得 !B! 格挡"
 */
public class JingYeSiCard extends AbstractAriaCard {
    public static final String ID = makeID(JingYeSiCard.class.getSimpleName());

    protected static LinkedList<JingYeSiCard> cards = new LinkedList<>();

    protected static ArrayList<AbstractAriaCard> choices = new ArrayList<>();

    private int optionIndex = -1;

    public JingYeSiCard() {
        this(-1);
    }

    public JingYeSiCard(int index) {
        super(ID, -2, CardType.ATTACK, CardRarity.BASIC, CardTarget.SELF_AND_ENEMY);
        costs = new int[]{5, 5, 5, 5};
        if (index >= 0) {
            this.optionIndex = index;
            isOptionCard = true;
            this.poeticCost = costs[index];
            this.exhaust = true;
        } else {
            // 注册子句
            choices.clear();
            for (int i = 0; i < cardStrings.EXTENDED_DESCRIPTION.length; i++) {
                choices.add(new JingYeSiCard(i));
            }
        }

        this.baseDamage = 5;
        this.baseMagicNumber = 1;
        this.baseBlock = 5;
        this.initializeDescription();
    }

    @Override
    public void initializeDescription() {
        if (!isOptionCard) super.initializeDescription();
        else if (this.cardStrings != null) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[optionIndex];
            super.initializeDescription();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
        if (!isOptionCard) {
            for (AbstractCard c : choices) {
                c.upgrade();
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m))
            return false;
        int amount;
        if (p.getPower(PoeticMoodPower.ID) != null) {
            amount = p.getPower(PoeticMoodPower.ID).amount;
        } else {
            amount = 0;
        }
        if(isOptionCard) return amount>=this.poeticCost;
        // 只要有一个子句满足就行
        return choices.stream().filter(c->!used).anyMatch(c -> c.poeticCost <= amount);
    }

    /**
     * 在打出主牌后，哪些子句能够打出
     *
     * @return 能打出的子句
     */
    protected List<AbstractAriaCard> getChoicesOnUse() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.getPower(PoeticMoodPower.ID) != null) {
            int amount = p.getPower(PoeticMoodPower.ID).amount;
            return choices.stream().filter(c -> c.poeticCost <= amount).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractAriaCard c = new JingYeSiCard(optionIndex);
        if (this.upgraded) c.upgrade();
        return c;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 不是子句，创建子句
        if (!isOptionCard) {
            if (!choices.isEmpty()) {
                // 主卡牌，还有子句未被打出
                ArrayList<AbstractCard> cs = new ArrayList<>(getChoicesOnUse()); // 获得子句
                if (!cs.isEmpty()) {
                    AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(cs));
                }
            } else {
                addToBot(new TalkAction(true, this.rawDescription, 2F, 2F));
                addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
            }
        } else {
            // 子句
            switch (optionIndex) {
                case 0:
                    // 给予 !M! 层虚弱
                    addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
                    break;
                case 1:
                    // 给予 !M! 层易伤
                    addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
                    break;
                case 2:
                    // !D! 点伤害
                    dmg(m, null);
                    break;
                case 3:
                    // !B! 点格挡
                    blck();
                    break;
                default:
                    break;
            }
            choices.remove(this);
        }
        addToBot(new ReducePowerAction(p, p, PoeticMoodPower.ID, poeticCost));
    }
}