package lingmod.cards.attack;

import static lingmod.ModCore.makeID;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantTextEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;

@CardConfig(damage = 10)
public class SurprisingThunder extends AbstractEasyCard {
    public static final String ID = makeID(SurprisingThunder.class.getSimpleName());

    public SurprisingThunder() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            damage *= 2;
            dmg(m, null);
            damage /= 2;
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY, Color.GOLD.cpy())));
            this.addToBot(new WaitAction(0.8F));
            this.addToBot(new VFXAction(new GiantTextEffect(m.hb.cX, m.hb.cY)));
        }
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (mo != m) {
                addToBot(new DamageAction(mo, new DamageInfo(p, damage)));
            }
        }
    }
}
