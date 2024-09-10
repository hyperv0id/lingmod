package lingmod.cards.poetry;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.AutoAdd;
import lingmod.cards.AbstractPoetryCard;
import lingmod.powers.PoeticMoodPower;

/**
 * 飞花令：如果打出的卡牌和上次不同类，那么造成 诗兴总量AOE，失去一半
 */
@AutoAdd.Ignore
public class FeiHuaLingCard extends AbstractPoetryCard {
    public static final String ID = makeID(FeiHuaLingCard.class.getSimpleName());
    public AbstractCard lastCard;

    public FeiHuaLingCard() {
        super(ID, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
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
}