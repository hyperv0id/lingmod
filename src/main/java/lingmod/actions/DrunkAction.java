package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WingBoots;

/**
 * 如果斩杀，那么无视路线
 */
public class DrunkAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public DrunkAction(AbstractPlayer p, AbstractMonster m, DamageInfo info) {
        this.setValues(m, p);
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.duration == DURATION && this.target != null) {
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead
                    && !this.target.hasPower("Minion")) {
                if (AbstractDungeon.player.relics.stream().anyMatch(r -> r.relicId.equals(WingBoots.ID))) {
                    // 有羽翼之靴
                    AbstractRelic relic =
                            AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(WingBoots.ID)).iterator().next();
                    if (relic.grayscale) {
                        relic.grayscale = false;
                        relic.counter = 0;
                    }
                    relic.flash();
                    relic.counter++;
                } else {
                    WingBoots fly = new WingBoots();
                    fly.counter = 1;
                    AbstractDungeon.getCurrRoom().addRelicToRewards(fly);
                    // addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, fly));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
