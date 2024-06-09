package lingmod.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import lingmod.character.Ling;
import lingmod.patch.OnSaveLoadPatch;
import lingmod.powers.Sui7DealPower;

import static lingmod.ModCore.makeID;

/**
 * 绩老七：
 * 1. 攻击时给予等量缠绕
 * 2. 选择1-n张牌消耗，然后替换成随机卡牌，每交易一张，绩获得1力量
 */
public class MonsterSui_7 extends CustomMonster {
  public static final String ID = makeID(MonsterSui_7.class.getSimpleName());
  protected static final MonsterStrings ms =
      CardCrawlGame.languagePack.getMonsterStrings(ID);
  protected static final String NAME = ms.NAME;
  protected static final String[] MOVES = ms.MOVES;
  public static final String[] DIALOGS = ms.DIALOG;

  public static final int MAX_HP = 200;
  protected boolean firstTurn = true;
  protected int baseDamage = 5;

  public MonsterSui_7() {
    super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, (String)null,
          -50.0F, 30.0F);
    // TODO: 使用绩的立绘，而不是老头
    this.loadAnimation("images/monsters/theForest/timeEater/skeleton.atlas",
                       "images/monsters/theForest/timeEater/skeleton.json",
                       1.0F);
    AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
    e.setTime(e.getEndTime() * MathUtils.random());
    this.stateData.setMix("Hit", "Idle", 0.1F);
    e.setTimeScale(0.8F);
    this.type = EnemyType.ELITE;
    this.dialogX = -200.0F * Settings.scale;
    this.dialogY = 10.0F * Settings.scale;
    this.damage.add(new DamageInfo(this, baseDamage));
  }

  @Override
  public void usePreBattleAction() {
    super.useUniversalPreBattleAction();
    AbstractPlayer p = AbstractDungeon.player;
    addToTop(new ApplyPowerAction(p, this, new Sui7DealPower(p, this, 1)));
  }

  @Override
  protected void getMove(int num) {
    this.setMove((byte)1, Intent.ATTACK_DEBUFF, this.baseDamage);
    // this.setMove((byte) 1, Intent.MAGIC);
  }

  @Override
  public void takeTurn() {
    AbstractPlayer p = AbstractDungeon.player;
    DamageInfo info = this.damage.get(0);
    if (firstTurn && OnSaveLoadPatch.saveTimes > 0) {
      EventStrings es =
          CardCrawlGame.languagePack.getEventString(makeID("SL_Monster_Talk"));
      addToBot(new TalkAction(this, es.DESCRIPTIONS[0], 0.5f, 2.0f));
    }
    if (firstTurn && p.chosenClass == Ling.Enums.PLAYER_LING) {
      addToBot(new TalkAction(this, DIALOGS[0], 0.5F, 2.0F));
      firstTurn = false;
    }
    switch (this.nextMove) {
    case 1:
      addToBot(new TalkAction(this, DIALOGS[2], 0.5F, 2.0F));
      addToBot(new DamageAction(p, info, AttackEffect.FIRE));
      addToBot(new ApplyPowerAction(
          p, this, new ConstrictedPower(p, this, info.output)));
      break;
    default:
      break;
    }
    AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
  }
}
