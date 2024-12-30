package lingmod.ui;


import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.VictoryScreen;

import basemod.interfaces.PreRoomRenderSubscriber;

public class VideoBackground implements PreRoomRenderSubscriber {
    private Hitbox hb;
    private VideoPlayer videoPlayer = VideoPlayerCreator.createVideoPlayer();
    private static final String COMMENT = "The video format needs to be webm !!!";
    private boolean isDone = false;

    public VideoBackground(String videoPath) {
        this.hb = new Hitbox((float) Settings.WIDTH, (float) Settings.HEIGHT);
        this.hb.x = 0.0F;
        this.hb.y = 0.0F;
        if (this.videoPlayer == null) {
            this.dispose();
        } else {
            this.videoPlayer.setOnCompletionListener((e) -> this.dispose());
            (new Thread(() -> {
                try {
                    this.videoPlayer.play(Gdx.files.internal(videoPath));
                } catch (Exception e) {
                    e.printStackTrace();
                    this.dispose();
                }

            })).start();
        }
    }

    @Override
    public void receivePreRoomRender(SpriteBatch sb) {
        Texture texture = this.videoPlayer.getTexture();
        if (texture != null) {
            float width = (float) texture.getWidth() * Settings.scale;
            float height = (float) texture.getHeight() * Settings.scale;
            float x = ((float) Settings.WIDTH - width) / 2.0F;
            float y = ((float) Settings.HEIGHT - height) / 2.0F;
            sb.setColor(Color.WHITE);
            sb.draw(texture, x, y, width, height);
        }
    }

    public void update() {
        this.videoPlayer.update();
        this.hb.update();
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            InputHelper.justClickedLeft = false;
            this.hb.clickStarted = true;
        }

        if (this.hb.clicked) {
            this.hb.clicked = false;
            this.isDone = true;
            if (this.videoPlayer != null) {
                this.videoPlayer.dispose();
                this.videoPlayer = null;
            }
        }
    }

    public void render(SpriteBatch sb) {

    }

    public void dispose() {
        this.isDone = true;
        AbstractDungeon.screen = CurrentScreen.VICTORY;
        AbstractDungeon.victoryScreen = new VictoryScreen((MonsterGroup) null);
        if (this.videoPlayer != null) {
            this.videoPlayer.dispose();
            this.videoPlayer = null;
        }
    }
}
