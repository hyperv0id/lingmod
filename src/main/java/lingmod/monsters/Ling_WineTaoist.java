package lingmod.monsters;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import lingmod.util.Wiz;

import static lingmod.ModCore.*;

/**
 * 二哥，你又在算计哦。陪我喝两小酒好不好
 */
public class Ling_WineTaoist extends CustomMonster {

    public static final String ID = makeID(Ling_WineTaoist.class.getSimpleName());
    public static final int MAX_HP = 200;
    protected static final MonsterStrings ms = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = ms.NAME;
    public static final String[] MOVES = ms.MOVES;
    public static final String[] DIALOGS = ms.DIALOG;
    protected static final String IMG_PATH = makeImagePath("monsters/Ling_WineTaoist.png");
    public boolean playerMorphed = false;

    public Ling_WineTaoist() {
        super(NAME, ID, MAX_HP, -10.0F, -30.0F, 476.0F, 410.0F, null,
                -50.0F, 30.0F);
        this.img = ImageMaster.loadImage(IMG_PATH);
        this.hb = new Hitbox(img.getWidth() * Settings.scale, img.getHeight() * Settings.scale);
        this.hb_h = hb.height;
        this.hb_w = hb.width;
        logger.info("Is IMG NULL?: " + this.img);
        this.type = EnemyType.ELITE;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, 5));
    }

    public void die() {
        // Restore Ling Texture

        if (Wiz.isPlayerLing()) {
            AbstractPlayer ling = AbstractDungeon.player;
            ling.img = null;
        }
        super.die();
    }

    @Override
    protected void getMove(int arg0) {
        setMove((byte) 0, Intent.ATTACK);
    }

    @Override
    public void takeTurn() {
    }

}
