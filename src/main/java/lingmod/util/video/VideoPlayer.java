//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lingmod.util.video;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.io.FileNotFoundException;

public interface VideoPlayer extends Disposable {
    boolean play(FileHandle var1) throws FileNotFoundException;

    boolean update();

    Texture getTexture();

    boolean isBuffered();

    void pause();

    void resume();

    void stop();

    void setOnVideoSizeListener(VideoSizeListener var1);

    void setOnCompletionListener(CompletionListener var1);

    int getVideoWidth();

    int getVideoHeight();

    boolean isPlaying();

    int getCurrentTimestamp();

    void dispose();

    float getVolume();

    void setVolume(float var1);

    boolean isLooping();

    void setLooping(boolean var1);

    void setFilter(Texture.TextureFilter var1, Texture.TextureFilter var2);

    public interface CompletionListener {
        void onCompletionListener(FileHandle var1);
    }

    public interface VideoSizeListener {
        void onVideoSize(float var1, float var2);
    }
}
