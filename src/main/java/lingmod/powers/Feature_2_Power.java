package lingmod.powers;

import static lingmod.ModCore.logger;
import static lingmod.ModCore.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import lingmod.cards.attack.Feature_2_Card;

public class Feature_2_Power extends AbstractEasyPower{
    public static final String POWER_ID = makeID(Feature_2_Power.class.getSimpleName());
    public static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = ps.NAME;
    public static final String[] DESC = ps.DESCRIPTIONS;
    public Feature_2_Power(AbstractPlayer p) {
        super(POWER_ID, NAME, PowerType.BUFF, false, p, 0);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        for(AbstractCard c: AbstractDungeon.player.hand.group) {
            if(c.cardID == Feature_2_Card.ID) {
                this.flash();
                c.upgrade();
                logger.info("UPGRADE " + Feature_2_Card.ID);
            }
        }
    }
}
