//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lingmod.util.video;

import com.badlogic.gdx.utils.Disposable;

import java.nio.ByteBuffer;

public class VideoDecoder implements Disposable {
    private long nativePointer;

    public VideoDecoder() {
        if (!FfMpeg.isLoaded()) {
            throw new IllegalStateException("The native libraries are not yet loaded!");
        } else {
            this.nativePointer = this.init();
        }
    }

    public static native void setDebug(boolean var0);

    public void close() {
        this.disposeNative();
        this.nativePointer = 0L;
    }

    public void dispose() {
        this.close();
    }

    private native long init();

    public native VideoDecoderBuffers loadStream(VideoFileReader var1) throws IllegalArgumentException, Exception;

    public native ByteBuffer nextVideoFrame();

    public native void updateAudioBuffer();

    public native double getCurrentFrameTimestamp();

    private native void disposeNative();

    public native boolean isBuffered();

    interface VideoFileReader {
        int fillBuffer(ByteBuffer var1);
    }

    public static class VideoDecoderBuffers {
        private final ByteBuffer videoBuffer;
        private final ByteBuffer audioBuffer;
        private final int videoBufferWidth;
        private final int videoWidth;
        private final int videoHeight;
        private final int audioChannels;
        private final int audioSampleRate;

        private VideoDecoderBuffers(ByteBuffer videoBuffer, ByteBuffer audioBuffer, int videoBufferWidth, int videoWidth, int videoHeight, int audioChannels, int audioSampleRate) {
            this.videoBuffer = videoBuffer;
            this.audioBuffer = audioBuffer;
            this.videoBufferWidth = videoBufferWidth;
            this.videoWidth = videoWidth;
            this.videoHeight = videoHeight;
            this.audioChannels = audioChannels;
            this.audioSampleRate = audioSampleRate;
        }

        public ByteBuffer getAudioBuffer() {
            return this.audioBuffer;
        }

        public ByteBuffer getVideoBuffer() {
            return this.videoBuffer;
        }

        public int getAudioChannels() {
            return this.audioChannels;
        }

        public int getAudioSampleRate() {
            return this.audioSampleRate;
        }

        public int getVideoBufferWidth() {
            return this.videoBufferWidth;
        }

        public int getVideoHeight() {
            return this.videoHeight;
        }

        public int getVideoWidth() {
            return this.videoWidth;
        }
    }
}
