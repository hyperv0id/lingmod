package lingmod.cards.skill;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.Wiz;

import static lingmod.ModCore.makeID;

/**
 * 失去6生命，获得 1 无实体
 */
@CardConfig(magic = 5)
@Credit(username = "柞木不朽", link = "https://www.bilibili.com/video/BV1xa4y1C7vV", platform = "bilibili")
public class Whoami_Wang extends AbstractEasyCard {
    public static final String NAME = Whoami_Wang.class.getSimpleName();
    public static final String ID = makeID(NAME);

    public Whoami_Wang() {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.1F));
        } else {
            this.addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        }

        AbstractDungeon.effectList.add(new FlashAtkImgEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY, AbstractGameAction.AttackEffect.NONE));
        for (int i = 0; i < baseMagicNumber; i++) {
            p.damage(new DamageInfo(p, 1, DamageInfo.DamageType.HP_LOSS));
        }
        this.addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
    }
}
