package lingmod.cards.aria;

import static lingmod.ModCore.makeID;

import java.util.ArrayList;
import java.util.LinkedList;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.powers.PoeticMoodPower;

/**
 * 普通五言：床前明月光，疑是地上霜，举头望明月，低头思故乡
 */
public class JingYeSiCard extends AbstractAriaCard {
    public static final String ID = makeID(JingYeSiCard.class.getSimpleName());

    protected static LinkedList<JingYeSiCard> cards = new LinkedList<>();

    protected static ArrayList<AbstractCard> choices = new ArrayList<>();

    private int optionIndex = -1;

    public JingYeSiCard() {
        this(-1);
    }

    public JingYeSiCard(int index) {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.SELF_AND_ENEMY);
        if (index >= 0) {
            this.optionIndex = index;
            isOptionCard = true;
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
        if(!isOptionCard) super.initializeDescription();
        else if(this.cardStrings != null) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[optionIndex];
            super.initializeDescription();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
        if(!isOptionCard) {
            for(AbstractCard c: choices) {
                c.upgrade();
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m))
            return false;
        if (p.getPower(PoeticMoodPower.ID) != null) {
            int amount = p.getPower(PoeticMoodPower.ID).amount;
            return amount >= 0; // TODO: change this
        }
        return false;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractAriaCard c = new JingYeSiCard(optionIndex);
        if(this.upgraded) c.upgrade();
        return c;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 不是子句，创建子句
        if (!isOptionCard) {
            if (!used) {
                // 主卡牌，第1次使用
                AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(choices));
                this.used = true;
            } else {
                dmg(m, null);
                dmg(m, null);
                dmg(m, null);
                dmg(m, null);
            }
        } else {
            // 子句
            dmg(m, null);
            choices.remove(this);
        }
    }
}