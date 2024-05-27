package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.actions.ExhaustAllAction;
import lingmod.cards.AbstractPoemCard;

/**
 * 宁作吾：消耗所有手牌，每张打5，并抽等量牌，然后失去所有能力
 * 能力数量参与计数
 */
public class NingZuoWuCard extends AbstractPoemCard {

    public static final String ID = makeID(NingZuoWuCard.class.getSimpleName());


    public NingZuoWuCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 5;
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cnt = AbstractDungeon.player.hand.size();
        if (upgraded) cnt += AbstractDungeon.player.powers.size();
        for (int i = 0; i < cnt; i++) {
            dmg(m, null);
        }
        addToBot(new ExhaustAllAction());
        addToBot(new DrawCardAction(cnt));
        addToBot(new RemoveAllPowersAction(AbstractDungeon.player, false));
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}