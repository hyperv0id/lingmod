package lingmod.monsters.whoisreal;

import basemod.BaseMod;
import basemod.abstracts.CustomMonster;
import basemod.interfaces.PostBattleSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lingmod.ModCore;
import lingmod.effects.ChangeSceneEffect;
import lingmod.util.WizArt;

import java.util.ArrayList;

import static lingmod.ModCore.makeID;

/**
 * 生活不易，夕宝卖艺
 * 1. 类似地精头子，召唤小怪
 */
public class Monster_Dusk_Opera extends CustomMonster implements PostBattleSubscriber {
    private GremlinLeader ref;
    public static final String ID = makeID(Monster_Dusk_Opera.class.getSimpleName());
    public static final int MAX_HP = 121;
    protected static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOGS = monsterStrings.DIALOG;
    protected static final String IMG_PATH = ModCore.makeCharacterPath("dusk/char_2015_dusk");
    private static final String BG_IMG_PATH = ModCore.makeImagePath("bg/Dusk_BG01.png");
    private static final Texture BG_IMG = new Texture(BG_IMG_PATH);
    private static ChangeSceneEffect changeSceneEffect;
    protected boolean firstTurn = true;
    protected int baseDamage = 7;

    public ArrayList<AbstractMonster> inks = new ArrayList<>();

    public Monster_Dusk_Opera() {
        super(NAME, ID, MAX_HP, 0F, 0F, 200.0F, 300.0F, null,
                0F, 0F);
        this.type = EnemyType.ELITE;
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 150.0F * Settings.scale;
        this.flipHorizontal = true;
        this.loadAnimation(IMG_PATH + ".atlas", IMG_PATH + ".json", 1.3F);

        this.hb_h = hb.height;
        this.hb_w = hb.width;
        this.damage.add(new DamageInfo(this, baseDamage));
    }

    @Override
    public void usePreBattleAction() {
        BaseMod.subscribe(this);
        WizArt.animateThenIdle(this, "Start");
        changeSceneEffect = new ChangeSceneEffect(new Texture(BG_IMG_PATH));
        AbstractDungeon.effectList.add(changeSceneEffect);
        this.inks.clear();
        this.inks.add(new AYao());
        this.inks.add(new XiaoZao());
    }

    @Override
    public void useFastAttackAnimation() {
        super.useFastAttackAnimation();
        WizArt.animateThenIdle(this, "Attack");
    }

    @Override
    public void useSlowAttackAnimation() {
        super.useSlowAttackAnimation();
        WizArt.animateThenIdle(this, "Skill3_Attack");
    }

    @Override
    protected void getMove(int moveID) {
        if (lastMove((byte) 2)) {
            this.setMove((byte) 1, Intent.ATTACK, this.baseDamage);
        } else {
            this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.baseDamage);
        }
        // this.setMove((byte) 1, Intent.MAGIC);
    }

    @Override
    public void takeTurn() {
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if (changeSceneEffect != null) {
            changeSceneEffect.dispose();
            changeSceneEffect.isDone = true;
        }
        BaseMod.unsubscribeLater(this);
    }
}
