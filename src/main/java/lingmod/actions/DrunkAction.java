package lingmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WingBoots;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.List;
import java.util.stream.Collectors;

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
            boolean alreadyDead = ((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0;
            // 如果已经死了，那么不再计算
            if(!alreadyDead){
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
                    }
                    else {
                        WingBoots fly = new WingBoots();
                        fly.counter = 1;
                        // 获取当前房间的奖励列表
                        List<RewardItem> rewards = AbstractDungeon.getCurrRoom().rewards;
                        rewards = rewards.stream()
                                .filter(c -> c.relic != null && c.relic.relicId.equals(fly.relicId))
                                .collect(Collectors.toList());
                        // 如果找到了符合条件的奖励项，增加其计数器
                        if (!rewards.isEmpty()) {
                            RewardItem rewardItem = rewards.get(0);
                            rewardItem.relic.counter++;
                        } else {
                            // 否则，向奖励列表中添加新的奖励项
                            AbstractDungeon.getCurrRoom().addRelicToRewards(fly);
                        }
                        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, fly));
                    }
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
