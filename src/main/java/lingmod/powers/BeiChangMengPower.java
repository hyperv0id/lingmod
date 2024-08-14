package lingmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import lingmod.stance.NellaFantasiaStance;

import static lingmod.ModCore.makeID;

/**
 * 离开梦境时触发 能力、遗物、手牌 的回合结束效果
 */
public class BeiChangMengPower extends AbstractEasyPower {
    public static final String POWER_NAME = BeiChangMengPower.class.getSimpleName();
    public static final String ID = makeID(POWER_NAME);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public BeiChangMengPower(AbstractCreature owner) {
        super(ID, powerStrings.NAME, PowerType.DEBUFF, false, owner, 0);
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        super.onChangeStance(oldStance, newStance);
        AbstractPlayer p = AbstractDungeon.player;
        if (oldStance.ID.equals(NellaFantasiaStance.STANCE_ID)) {
            // EOT
            p.powers.forEach(po -> po.atEndOfTurnPreEndTurnCards(true));
            p.powers.forEach(po -> po.atEndOfTurn(true));
            p.powers.forEach(AbstractPower::atEndOfRound);
            p.relics.forEach(AbstractRelic::onPlayerEndTurn);
            p.hand.group.forEach(AbstractCard::triggerOnEndOfPlayerTurn); // 卡牌回合结束
            p.hand.group.forEach(AbstractCard::onRetained); // 特判保留
            // SOT
            p.powers.forEach(AbstractPower::atStartOfTurn);
            p.powers.forEach(AbstractPower::atStartOfTurnPostDraw);
            p.relics.forEach(AbstractRelic::atTurnStart);
            p.relics.forEach(AbstractRelic::atTurnStartPostDraw);
            p.hand.group.forEach(AbstractCard::atTurnStart);
        }
    }
}
