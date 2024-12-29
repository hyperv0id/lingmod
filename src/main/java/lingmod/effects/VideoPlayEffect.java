package lingmod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lingmod.util.video.VideoPlayer;
import lingmod.util.video.VideoPlayerCreator;

public class VideoPlayEffect extends AbstractGameEffect {
    private boolean startPlay = false;
    private VideoPlayer videoPlayer;
    private float videoWidth;
    private float videoHeight;
    private float renderX;
    private float renderY;
    private float renderW;
    private float renderH;
    private float expandRate = 0.1F;
    private boolean inTail = false;

    public VideoPlayEffect(String path) {
        this.duration = this.startingDuration = 2.0F;
        this.color = Color.WHITE.cpy();
        this.videoPlayer = VideoPlayerCreator.createVideoPlayer();
        FileHandle file = Gdx.files.internal(path);
        if (this.videoPlayer == null) {
            this.over();
        } else {
            this.videoPlayer.setOnCompletionListener((e) -> {
                this.over();
            });
            this.videoPlayer.setOnVideoSizeListener((w, h) -> {
                this.videoWidth = w - 30.0F;
                this.videoHeight = h;
                float scale = this.videoHeight / (float) Settings.HEIGHT * 0.7F;
                this.renderW = this.videoWidth * scale;
                this.renderH = this.videoHeight * scale;
                this.renderX = ((float) Settings.WIDTH - this.renderW) / 2.0F;
                this.renderY = ((float) Settings.HEIGHT - this.renderH) / 2.0F;
            });
            (new Thread(() -> {
                try {
                    this.videoPlayer.play(file);
                    this.videoPlayer.setVolume(0.0F);
                    this.videoPlayer.pause();
                } catch (Exception var2) {
                    var2.printStackTrace();
                    this.over();
                }

            })).start();
        }
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (!this.startPlay && this.startingDuration - this.duration >= 1.0F) {
            this.startPlay = true;
            this.videoPlayer.resume();
            AbstractDungeon.overlayMenu.showBlackScreen();
        }

        if (this.startPlay) {
            this.videoPlayer.update();
            this.expandRate = MathUtils.lerp(this.expandRate, 1.0F, Gdx.graphics.getDeltaTime() * 5.0F);
            if (this.expandRate > 1.0F) {
                this.expandRate = 1.0F;
            }
        }

        if (!this.inTail && this.startingDuration - this.duration >= 7.0F) {
            this.inTail = true;
            AbstractDungeon.overlayMenu.hideBlackScreen();
        }

        if (this.inTail) {
            this.color.a = MathUtils.lerp(this.color.a, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
            if (this.color.a <= 0.01F) {
                this.over();
            }
        }

    }

    public void render(SpriteBatch sb) {
        if (this.startPlay) {
            Texture texture = this.videoPlayer.getTexture();
            if (texture != null) {
                sb.setColor(this.color);
                float offsetW = (1.0F - this.expandRate) * this.renderW / 2.0F;
                sb.draw(texture, this.renderX + offsetW, this.renderY, this.renderW - offsetW * 2.0F, this.renderH, (int) ((1.0F - this.expandRate) * this.videoWidth / 2.0F), 0, (int) (this.expandRate * this.videoWidth), (int) this.videoHeight, false, false);
            }
        }

    }

    public void dispose() {
    }

    private void over() {
        if (this.videoPlayer != null) {
            this.videoPlayer.dispose();
            this.videoPlayer = null;
        }

        this.isDone = true;
        AbstractDungeon.overlayMenu.hideBlackScreen();
    }
}
