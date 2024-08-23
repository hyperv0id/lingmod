package lingmod.cards.poetry;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractPoetryCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

@CardConfig(isDream = true, magic = 2)
public class QingPingYueCard extends AbstractPoetryCard {
    public static final String ID = makeID(QingPingYueCard.class.getSimpleName());

    public QingPingYueCard() {
        super(ID, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void use_p(AbstractPlayer p, AbstractMonster m) {
        addToBotAbstract(() -> {
            int diff = 0;
            for (AbstractCard card : p.hand.group) {
                if (card.cost > 0) {
                    int newCost = AbstractDungeon.cardRandomRng.random(1, 3);
                    diff += Math.abs(newCost - card.cost);
                    if (card.cost != newCost) {
                        card.cost = newCost;
                        card.costForTurn = card.cost;
                        card.isCostModified = true;
                    }
                    card.freeToPlayOnce = false;
                }
            }
            addToTop(new AddTemporaryHPAction(p, p, diff * magicNumber));
        });
    }
}
