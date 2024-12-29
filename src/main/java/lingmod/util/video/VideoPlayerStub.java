//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lingmod.util.video;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.FileNotFoundException;

class VideoPlayerStub implements VideoPlayer {
    VideoPlayerStub() {
    }

    public boolean play(FileHandle file) throws FileNotFoundException {
        return true;
    }

    public boolean update() {
        return false;
    }

    public Texture getTexture() {
        return null;
    }

    public boolean isBuffered() {
        return true;
    }

    public void pause() {
    }

    public void resume() {
    }

    public void stop() {
    }

    public void setOnVideoSizeListener(VideoPlayer.VideoSizeListener listener) {
    }

    public void setOnCompletionListener(VideoPlayer.CompletionListener listener) {
    }

    public int getVideoWidth() {
        return 0;
    }

    public int getVideoHeight() {
        return 0;
    }

    public boolean isPlaying() {
        return false;
    }

    public int getCurrentTimestamp() {
        return 0;
    }

    public void dispose() {
    }

    public float getVolume() {
        return 0.0F;
    }

    public void setVolume(float volume) {
    }

    public boolean isLooping() {
        return false;
    }

    public void setLooping(boolean looping) {
    }

    public void setFilter(Texture.TextureFilter minFilter, Texture.TextureFilter magFilter) {
    }
}
