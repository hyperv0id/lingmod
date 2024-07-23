package lingmod.cards.attack;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 黑子：打6，弃牌堆放入一张黑子，你牌库中每有一张黑子，伤害增加2
 */
@CardConfig(damage = 6, magic = 2)
public class BlackPawn extends AbstractEasyCard {
    public final static String ID = makeID(BlackPawn.class.getSimpleName());

    public BlackPawn() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.magicNumber = baseMagicNumber;
        int totalCnt = Wiz.allCardsInBattle(false).stream()
                .mapToInt(c -> c.cardID.equals(ID) ? 1 : 0).sum();

        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * totalCnt;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.FIREBRICK, p.hb.cX, p.hb.cY), 0.0F));
        this.addToBot(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));
    }

    @Override
    public void upp() {
        upgradeDamage(magicNumber);
    }
}
// "${ModID}:BlackPawn": {
// "NAME": "BlackPawn",
// "DESCRIPTION": ""
// }