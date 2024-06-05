package lingmod.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GifAnimation {
    private Animation<TextureRegion> animation;
    private float elapsed;
    GifDecoder gdec;
    private static HashMap<String, Animation<TextureRegion>> cache = new HashMap<>();

    public GifAnimation(String filePath) {
        this.create(filePath);
    }

    private void create(String filePath) {
        if (cache.containsKey(filePath)) {
            this.animation = (Animation<TextureRegion>) cache.get(filePath);
        } else {
            this.animation = GifDecoder.loadGIFAnimation(PlayMode.LOOP, Gdx.files.internal(filePath).read());
            cache.put(filePath, this.animation);
        }

    }

    public void render(SpriteBatch sb, float x, float y, float width, float height) {
        this.elapsed += Gdx.graphics.getDeltaTime();
        sb.draw((TextureRegion) this.animation.getKeyFrame(this.elapsed), x, y, width, height);
    }

    public void render(SpriteBatch sb, float x, float y, float originX, float originY, float width, float height,
            float scaleX, float scaleY, float rotation) {
        this.elapsed += Gdx.graphics.getDeltaTime();
        sb.draw((TextureRegion) this.animation.getKeyFrame(this.elapsed), x, y, originX, originY, width, height, scaleX,
                scaleY, rotation);
    }

    public void dispose(SpriteBatch sb) {
        sb.dispose();
    }
}
