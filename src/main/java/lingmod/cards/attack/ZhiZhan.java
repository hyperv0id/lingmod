package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

import lingmod.cards.AbstractEasyCard;
import lingmod.powers.WinePower;

/**
 * img from: pixiv_106560931_0
 */
public class ZhiZhan extends AbstractEasyCard {

    public static final String ID = makeID(ZhiZhan.class.getSimpleName());

    public ZhiZhan() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 3;
        this.baseDamage = 14;
    }

    public void applyPowers() {
        AbstractPower winePower = AbstractDungeon.player.getPower(WinePower.POWER_ID);
        if (winePower != null)
            winePower.amount *= magicNumber;
        super.applyPowers();
        if (winePower != null)
            winePower.amount /= magicNumber;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower winePower = AbstractDungeon.player.getPower(WinePower.POWER_ID);
        if (winePower != null)
            winePower.amount *= magicNumber;
        super.calculateCardDamage(mo);
        if (winePower != null)
            winePower.amount /= magicNumber;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(
                    new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        }

        addToBot(new TalkAction(true, this.cardStrings.EXTENDED_DESCRIPTION[0], 2F, 2F));

        this.addToBot(
                new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AttackEffect.BLUNT_HEAVY));
    }

}
