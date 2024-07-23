package lingmod.cards.skill;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import lingmod.cards.AbstractEasyCard;
import lingmod.cards.mod.NellaFantasiaMod;
import lingmod.stance.NellaFantasiaStance;
import lingmod.util.CustomTags;

import static lingmod.ModCore.makeID;

/**
 * 清明梦：只能在梦中打出，敌人减少的力量变为永久
 */
public class QingMingDream extends AbstractEasyCard {
    public static final String NAME = QingMingDream.class.getSimpleName();

    public static final String ID = makeID(NAME);

    public QingMingDream() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(CustomTags.DREAM);
        CardModifierManager.addModifier(this, new NellaFantasiaMod());
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
    public void use(AbstractPlayer p, AbstractMonster m) {
        // this.addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F,
        // 1.0F), true)));
        // this.addToBot(new SkipEnemiesTurnAction());
        addToBot(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[1], 2F, 2F));
        int amount = 0;
        if (p.stance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            amount = NellaFantasiaStance.dmgModi;
        }
        if (amount > 0) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -amount)));
            }
        }
        addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }
}
