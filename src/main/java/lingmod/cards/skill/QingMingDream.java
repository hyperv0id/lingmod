package lingmod.cards.skill;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import lingmod.cards.AbstractEasyCard;
import lingmod.interfaces.CardConfig;
import lingmod.interfaces.Credit;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.CustomTags;
import lingmod.util.Wiz;

/**
 * 清明梦：只能在梦中打出，敌人减少的力量变为永久
 */
@Credit(platform = Credit.PIXIV, username = "DaylightAllure", link = "https://www.pixiv.net/artworks/96229195")
@CardConfig(isDream = true, magic = 1)
public class QingMingDream extends AbstractEasyCard {
    public static final String NAME = QingMingDream.class.getSimpleName();

    public static final String ID = makeID(NAME);

    public QingMingDream() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(CustomTags.DREAM);
        // CardModifierManager.addModifier(this, new NellaFantasiaMod());
        CardModifierManager.addModifier(this, new ExhaustMod());
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void upp() {
        updateCost(-1);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.stance != null && p.stance.ID.equals(NellaFantasiaStance.STANCE_ID);
    }

    @Override
    public void applyPowers() {
        if (Wiz.adp() != null && Wiz.adp().stance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            baseMagicNumber = NellaFantasiaStance.dmgModi;
            if (upgraded)
                baseMagicNumber *= 2;
        }
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        if (Wiz.adp() != null && Wiz.adp().stance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            baseMagicNumber = NellaFantasiaStance.dmgModi;
            if (upgraded)
                baseMagicNumber *= 2;
        }
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[1], 2F, 2F));
        if (magicNumber > 0) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -magicNumber)));
            }
        }
    }
}
