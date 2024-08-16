package lingmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.actions.TransformDrawPileAction;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 黑子：打6 X 次，次数为黑子数量。
 * 在抽牌堆放入一张黑子
 */
@CardConfig(damage = 6, magic = 1)
public class BlackPawn extends AbstractEasyCard {
    public final static String ID = makeID(BlackPawn.class.getSimpleName());

    public BlackPawn() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = (int) Wiz.allCardsInBattle(false).stream().filter(c -> c.cardID.equals(BlackPawn.ID)).count();
        super.calculateCardDamage(mo);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, null);
        }
        if (Wiz.isStanceNell())
            addToBot(new TransformDrawPileAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}
// "${ModID}:BlackPawn": {
// "NAME": "BlackPawn",
// "DESCRIPTION": ""
// }