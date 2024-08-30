package lingmod.monsters;

import static lingmod.ModCore.makeID;
import static lingmod.ModCore.makeImagePath;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.abstracts.CustomMonster;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import lingmod.actions.MyApplyPower_Action;
import lingmod.character.Ling;
import lingmod.patch.OnSaveLoadPatch;

/**
 * 绩老七：
 * 1. 攻击时给予等量缠绕
 * 2. 选择1-n张牌消耗，然后替换成随机卡牌，每交易一张，绩获得1力量
 */
public class MonsterSui_7_Ji extends CustomMonster implements PostExhaustSubscriber, PostBattleSubscriber {
  public static final String ID = makeID(MonsterSui_7_Ji.class.getSimpleName());
  public static final int MAX_HP = 200;
  protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
  public static final String NAME = ms.NAME;
  public static final String[] MOVES = ms.MOVES;
  public static final String[] DIALOGS = ms.DIALOG;
  protected static final String IMG_PATH = makeImagePath("monsters/MonsterSui_7_Ji.png");
  public static int STRENGTH_GROW = 1;
  protected boolean firstTurn = true;
  protected int baseDamage = 5;

  public MonsterSui_7_Ji() {
    super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, null,
            -50.0F, 30.0F);
    this.img = ImageMaster.loadImage(IMG_PATH);
    this.type = EnemyType.ELITE;
    this.dialogX = -200.0F * Settings.scale;
    this.dialogY = 10.0F * Settings.scale;
    this.damage.add(new DamageInfo(this, baseDamage));
  }

  @Override
  public void usePreBattleAction() {
    BaseMod.subscribe(this);
    super.useUniversalPreBattleAction();
    // AbstractPlayer p = AbstractDungeon.player;
    // addToTop(new MyApplyPower_Action(p, this, new Sui7DealPower(p, this, 1)));
  }

  @Override
  protected void getMove(int num) {
    this.setMove((byte) 1, Intent.ATTACK_DEBUFF, this.baseDamage);
    // this.setMove((byte) 1, Intent.MAGIC);
  }

  @Override
  public void takeTurn() {
    AbstractPlayer p = AbstractDungeon.player;
    DamageInfo info = this.damage.get(0);
    if (firstTurn && OnSaveLoadPatch.saveTimes > 0) {
      EventStrings es = CardCrawlGame.languagePack.getEventString(makeID("SL_Monster_Talk"));
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
        addToBot(new MyApplyPower_Action(
                p, this, new ConstrictedPower(p, this, info.output)));
        break;
      default:
        break;
    }
    AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
  }

  @Override
  public void receivePostExhaust(AbstractCard arg0) {
    addToBot(new MyApplyPower_Action(this, this, new StrengthPower(this, STRENGTH_GROW)));
  }

  @Override
  public void receivePostBattle(AbstractRoom arg0) {
    BaseMod.unsubscribeLater(this);
  }

}
