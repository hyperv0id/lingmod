package lingmod.cards.verse;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lingmod.cards.AbstractVerseCard;
import lingmod.interfaces.CardConfig;
import lingmod.powers.PoeticMoodPower;

import static lingmod.ModCore.makeID;

/**
 * 飞花令：如果打出的卡牌和上次不同类，那么造成 诗兴总量AOE，失去一半
 */
@AutoAdd.Ignore
@CardConfig(poemAmount = 1)
public class FeiHuaLingCard extends AbstractVerseCard {
    public static final String ID = makeID(FeiHuaLingCard.class.getSimpleName());
    int upgrade = 0;
    public AbstractCard lastCard;

    public FeiHuaLingCard() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        AbstractPower poem = AbstractDungeon.player.getPower(PoeticMoodPower.ID);
        if (poem != null) {
            int amt = poem.amount;
            damage = amt;
            if (lastCard != null && c.type != lastCard.type) {
                allDmg(AbstractGameAction.AttackEffect.SLASH_HEAVY);
            }
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, PoeticMoodPower.ID, amt / 2));
        }
    }

    @Override
    public void upp() {
        upgrade++;
    }
}
