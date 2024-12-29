package lingmod.events;

import basemod.ReflectionHacks;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import basemod.patches.com.megacrit.cardcrawl.rooms.AbstractRoom.EventCombatSave;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.interfaces.CampfireSleepEvent;
import lingmod.monsters.ZuoLe_ZuiFeiChen;
import lingmod.relics.Beans_DuskRelic;
import lingmod.util.Wiz;

import java.util.Objects;
import java.util.function.Consumer;

import static lingmod.ModCore.makeID;

@CampfireSleepEvent
public class ZuiFeiChenEvent extends PhasedEvent {
    public static final String ID = makeID(ZuiFeiChenEvent.class.getSimpleName());

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static String NAME = eventStrings.NAME;
    protected AbstractRelic relic = new Beans_DuskRelic();

    public ZuiFeiChenEvent() {
        super(ID, eventStrings.NAME, "");
        registerPhase("INTRO", new TextPhase(DESCRIPTIONS[0]).addOption(OPTIONS[1],
                (i) -> transitionKey("BATTLE")));
        registerPhase("BATTLE",
                new MusicPhase(new MonsterGroup(new ZuoLe_ZuiFeiChen()))
                        .withChangePosition(true)
        );
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        transitionKey("INTRO");
    }


    public static class MusicPhase extends CombatPhase {
        public MonsterGroup encounter;
        public boolean changePosition = false; // 切换玩家和敌人的位置，用于切换视角

        public MusicPhase(MonsterGroup grp) {
            super("");
            this.encounter = grp;
        }

        public MusicPhase withChangePosition(boolean change) {
            this.changePosition = change;
            return this;
        }

        public AbstractMonster.EnemyType getEnemyType() {
            return ReflectionHacks.getPrivate(this, CombatPhase.class, "type");
        }

        @Override
        public void transition(PhasedEvent event) {
            AbstractDungeon.getCurrRoom().cannotLose = false;
            AbstractDungeon.getCurrRoom().rewardTime = false;
            AbstractDungeon.getCurrRoom().monsters = encounter;
            AbstractEvent.type = EventType.ROOM;
            event.resetCardRarity();
            if (this.getEnemyType() == AbstractMonster.EnemyType.ELITE) {
                event.setCardRarity(40, 10);
                AbstractDungeon.getCurrRoom().eliteTrigger = true;
            } else if (this.getEnemyType() == AbstractMonster.EnemyType.BOSS) {
                event.setCardRarity(0, 420);
            }

            event.noCardsInRewards = !this.cardReward;
            AbstractDungeon.getCurrRoom().rewards.clear();
            AbstractDungeon.getCurrRoom().rewardAllowed = ReflectionHacks.getPrivate(this, CombatPhase.class,
                    "allowRewards");
            Consumer<AbstractRoom> addRewards = ReflectionHacks.getPrivate(this, CombatPhase.class, "addRewards");
            if (addRewards != null) {
                addRewards.accept(AbstractDungeon.getCurrRoom());
            }

            if (Objects.equals(ReflectionHacks.getPrivate(this, CombatPhase.class, "encounterKey"), "Shield " + "and Spear")) {
                AbstractDungeon.player.movePosition((float) Settings.WIDTH / 2.0F, AbstractDungeon.floorY);
            } else {
                AbstractDungeon.player.movePosition((float) Settings.WIDTH * 0.25F, AbstractDungeon.floorY);
                AbstractDungeon.player.flipHorizontal = false;
            }
            if (changePosition) {
                float pX = Wiz.adp().drawX;
                float pY = Wiz.adp().drawY;

                float mX = AbstractDungeon.getCurrRoom().monsters.monsters.get(0).drawX;
                float mY = AbstractDungeon.getCurrRoom().monsters.monsters.get(0).drawY;
                Wiz.adp().movePosition(mX, mY);
                Wiz.adp().flipHorizontal = !Wiz.adp().flipHorizontal;
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    monster.drawX = pX + (monster.drawX - mX);
                    monster.drawY = pY + (monster.drawY - mY);
                    monster.hb.move(monster.drawX, monster.drawY);
                    monster.flipHorizontal = !monster.flipHorizontal;
                }
            }

            event.enterCombat();
            if ((Boolean) ReflectionHacks.getPrivate(this, CombatPhase.class, "allowRewards")) {
                this.waitingRewards = true;
            }

            if (!(Boolean) ReflectionHacks.getPrivate(this, CombatPhase.class, "postCombatSave")) {
                EventCombatSave.preventSave();
            } else {
                EventCombatSave.allowSave();
            }
        }
    }
}
