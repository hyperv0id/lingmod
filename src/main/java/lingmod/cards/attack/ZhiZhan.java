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
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

import lingmod.cards.AbstractEasyCard;

public class ZhiZhan extends AbstractEasyCard {

    public static final String ID = makeID(ZhiZhan.class.getSimpleName());

    public ZhiZhan() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 3;
        this.baseDamage = 14;
    }

    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null) {
            strength.amount *= this.magicNumber;
        }
        AbstractPower vigor = AbstractDungeon.player.getPower(VigorPower.POWER_ID);
        if(vigor != null)
            vigor.amount *= this.magicNumber;

        super.applyPowers();
        if (strength != null) {
            strength.amount /= this.magicNumber;
        }
        if(vigor != null)
            vigor.amount /= this.magicNumber;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null) 
            strength.amount *= this.magicNumber;
        AbstractPower vigor = AbstractDungeon.player.getPower(VigorPower.POWER_ID);
        if(vigor != null)
            vigor.amount *= this.magicNumber;

        super.calculateCardDamage(mo);
        if (strength != null) 
            strength.amount /= this.magicNumber;
        if(vigor != null)
            vigor.amount /= this.magicNumber;
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
