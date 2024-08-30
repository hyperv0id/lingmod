package lingmod.powers;

import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import lingmod.util.Wiz;

public class Whoami_NianPower extends AbstractEasyPower {
    public static final String POWER_NAME = Whoami_NianPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public Whoami_NianPower(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, 0);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.ATTACK) {
            AbstractPlayer p = Wiz.adp();
            addToBot(new DamageAction(p, new DamageInfo(p, 1, DamageType.HP_LOSS)));
        }
        super.onAfterUseCard(card, action);
    }
}
