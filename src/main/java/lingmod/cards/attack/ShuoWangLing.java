package lingmod.cards.attack;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 朔望：卡牌唯一/满：打50
 */
@Credit(link = "https://chunqiudameng229.lofter.com/post/1fad26eb_2bb4cf926", username = "-有点小桂-", platform = "lofter")
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
        if (siz == 1) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else if (checkWang()) {
            this.glowColor = GREEN_BORDER_GLOW_COLOR.cpy();
        } else if (upgraded && siz == 3) {
            this.glowColor = Color.BLUE.cpy();
        } else {
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    boolean checkWang() {
        return Wiz.adp().hand.size() >= BaseMod.MAX_HAND_SIZE || (Wiz.adp().discardPile.isEmpty() && Wiz.adp().drawPile.isEmpty());
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
        } else if (checkWang()) {
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
