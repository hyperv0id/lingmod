package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

@CardConfig(magic = 33, magic2 = 2)
@Credit(platform = Credit.PIXIV, username = "JANGO", link = "https://www.pixiv.net/artworks/105995954")
public class AGiftCard extends AbstractEasyCard {

    public static final String ID = makeID(AGiftCard.class.getSimpleName());

    public AGiftCard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        tags.add(CustomTags.DREAM);
        CardModifierManager.addModifier(this, new NellaFantasiaMod());
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(p,
                new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE),
                0.5F));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            // this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false),
                    // this.magicNumber, true, AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber,
                    true, AttackEffect.NONE));
        }
        // addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, secondMagic, false)));
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, secondMagic, false)));
    }

    @Override
    public void upp() {
        upgradeSecondMagic(-1);
    }
}