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
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.powers.WinePower;

/**
 * img from: pixiv_106560931_0
 */
@CardConfig(damage = 14, magic = 3)
@Credit(platform = "pixiv", link = "https://www.pixiv.net/artworks/106560931", username = "Maiz")
public class ZhiZhan extends AbstractEasyCard {

    public static final String ID = makeID(ZhiZhan.class.getSimpleName());

    public ZhiZhan() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    public void applyPowers() {
        AbstractPower wine = AbstractDungeon.player.getPower(WinePower.POWER_ID);
        if (wine == null) {
            super.applyPowers();
            return;
        }
        int cache = wine.amount;
        wine.amount *= baseMagicNumber;
        super.applyPowers();
        wine.amount = cache;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        
        AbstractPower wine = AbstractDungeon.player.getPower(WinePower.POWER_ID);
        if (wine == null) {
            super.calculateCardDamage(mo);;
            return;
        }
        int cache = wine.amount;
        wine.amount *= baseMagicNumber;
        super.calculateCardDamage(mo);
        wine.amount = cache;
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
