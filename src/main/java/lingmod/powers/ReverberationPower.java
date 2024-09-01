package lingmod.powers;

import basemod.AutoAdd.Ignore;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static lingmod.ModCore.makeID;

/**
 * 回合结束，打出之前打出的最后一张牌
 * TODO: 没有联动
 */
@Ignore
public class ReverberationPower extends AbstractEasyPower {
    public static final String POWER_NAME = ReverberationPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public AbstractCard lastCard;
    public AbstractMonster lastMonster;

    public ReverberationPower(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, 0);
    }

    public ReverberationPower(AbstractCreature owner, AbstractCard c, AbstractMonster m) {
        super(ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, 0);
        this.lastMonster = m;
        this.lastCard = c;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        lastCard = card;
        lastMonster = m;
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (lastCard == null) return;
        if (lastMonster.isDeadOrEscaped()) {
            lastMonster = AbstractDungeon.getMonsters().monsters.stream().filter(mo -> !mo.isDeadOrEscaped()).findFirst().orElse(null);
        }
        addToBot(new UseCardAction(lastCard, lastMonster));
    }
}
