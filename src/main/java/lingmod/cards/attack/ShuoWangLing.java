package lingmod.cards.attack;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

import static lingmod.ModCore.makeID;

/**
 * 朔望：卡牌唯一/满：打50
 */
@CardConfig(damage = 1, damage2 = 19, block = 3, block2 = 3, magic = 12, magic2 = 2)
public class ShuoWangLing extends AbstractEasyCard {
    public final static String ID = makeID(ShuoWangLing.class.getSimpleName());

    public ShuoWangLing() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPlayer p = AbstractDungeon.player;
        int siz = p.hand.size();
        if (siz == 1 || siz == BaseMod.MAX_HAND_SIZE || siz == 10) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR;
        } else if (upgraded && siz == 3) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.damage <= 1) this.damage = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        if (p.hand.size() == 1) {
            for (int i = 0; i < magicNumber; i++) {
                dmg(m, null);
            }
        } else if (p.hand.size() == 10 || p.hand.size() == BaseMod.MAX_HAND_SIZE) {
            for (int i = 0; i < 2; i++) {
                altDmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            }
        } else if (upgraded && p.hand.size() == 3) {
            for (int i = 0; i < 3; i++) {
                blck();
            }
        }
    }

    @Override
    public void upp() {
        String ling = cardStrings.EXTENDED_DESCRIPTION[0];
        this.name = cardStrings.NAME + ling;
        this.initializeTitle();
    }
}
// "lingmod:ShuoWangLing": {
// "NAME": "ShuoWangLing",
// "DESCRIPTION": ""
// }
