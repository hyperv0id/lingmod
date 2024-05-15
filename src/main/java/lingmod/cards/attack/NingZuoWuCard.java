package lingmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import lingmod.cards.AbstractPoetCard;

import java.util.ArrayList;
import java.util.List;

import static lingmod.ModCore.makeID;

/**
 * 宁作吾：移除所有能力，每种造成 7 点伤害
 * <p>
 * 升级：可以消耗手牌中的状态、诅咒
 */
public class NingZuoWuCard extends AbstractPoetCard {

    public static final String ID = makeID(NingZuoWuCard.class.getSimpleName());


    public NingZuoWuCard() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;
        baseDamage = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        List<AbstractCard> cards = new ArrayList<>();
        int times = p.powers.size();

        if (upgraded) { // 可以消耗
            // cards = p.hand.group.stream().filter(c -> c.type == CardType.CURSE || c.type == CardType.STATUS).collect(Collectors.toList());
            cards = p.hand.group;
            times += cards.size();
        }
        while (times-- != 0) {
            addToBot(new DamageAction(m, new DamageInfo(p, baseDamage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

        addToTop(new RemoveAllPowersAction(p, false));
        if (!upgraded) return;
        for (AbstractCard c : cards) {
            if (Settings.FAST_MODE)
                this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            else
                this.addToTop(new ExhaustAction(1, true, true));
        }
    }

    @Override
    public void upp() {
    }
}
