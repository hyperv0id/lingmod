package lingmod.effects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ChangeSceneEffect extends AbstractGameEffect {
    private final Texture img;
    public float x;
    private float timer;
    public Color color;

    public ChangeSceneEffect(Texture img) {
        this.color = Color.WHITE.cpy();
        this.renderBehind = true;
        this.img = img;
        this.x = (float) (-Settings.WIDTH) * 1.7F;
        this.timer = 0.0F;
    }

    @Override
    public void update() {
        if (this.x < 0.0F) {
            this.x += 1500.0F * Gdx.graphics.getDeltaTime() * Settings.scale;
            this.timer -= Gdx.graphics.getDeltaTime();
            if (this.timer < 0.0F) {
                this.timer += 0.02F;
            }
        } else {
            this.x = 0.0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.flush();
        sb.setColor(Color.WHITE.toFloatBits());
        sb.draw(this.img, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
    }

    @Override
    public void dispose() {
        this.isDone = true;
    }
}
