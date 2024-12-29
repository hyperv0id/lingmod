//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lingmod.util.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.audio.OpenALAudio;
import com.badlogic.gdx.backends.lwjgl.audio.OpenALMusic;
import com.badlogic.gdx.files.FileHandle;

import java.nio.ByteBuffer;

class RawMusic extends OpenALMusic {
    VideoDecoder decoder;
    ByteBuffer backBuffer;

    public RawMusic(VideoDecoder decoder, ByteBuffer buffer, int channels, int sampleRate) {
        super((OpenALAudio) Gdx.audio, (FileHandle) null);
        this.decoder = decoder;
        this.backBuffer = buffer;
        this.backBuffer.position(this.backBuffer.limit());
        this.setup(channels, sampleRate);
    }

    public int read(byte[] buffer) {
        int sizeNeeded = buffer.length;
        int currentIndex = 0;

        while (sizeNeeded > 0) {
            if (this.backBuffer.remaining() > 0) {
                int numBytes = Math.min(this.backBuffer.remaining(), sizeNeeded);
                this.backBuffer.get(buffer, currentIndex, numBytes);
                currentIndex += numBytes;
                sizeNeeded -= numBytes;
            } else {
                this.backBuffer.rewind();
                this.decoder.updateAudioBuffer();
            }
        }

        return buffer.length;
    }

    public void reset() {
    }
}
