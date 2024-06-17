package lingmod.cards.attack;

import static lingmod.ModCore.makeID;
import static lingmod.util.Wiz.allCardsInBattle;
import static lingmod.util.Wiz.isStanceNell;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.DerivativeMod;

/**
 * 黑子：打6,在梦中时，所有化物增加2伤害
 */
public class BlackPawn extends AbstractEasyCard {
    public final static String ID = makeID(BlackPawn.class.getSimpleName());

    public BlackPawn() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (!isStanceNell())
            return;
        allCardsInBattle(false).stream()
                .filter(card -> card.type == CardType.ATTACK)
                .filter(card -> CardModifierManager.hasModifier(card, DerivativeMod.ID))
                .filter(card -> card.baseDamage > 0)
                .forEach(card -> {
                    card.baseDamage += magicNumber;
                    card.isDamageModified = true;
                });
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}
// "${ModID}:BlackPawn": {
// "NAME": "BlackPawn",
// "DESCRIPTION": ""
// }