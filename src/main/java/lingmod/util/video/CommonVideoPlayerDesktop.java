//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lingmod.util.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public abstract class CommonVideoPlayerDesktop extends AbstractVideoPlayer implements VideoPlayer {
    VideoDecoder decoder;
    Texture texture;
    Music audio;
    long startTime = 0L;
    boolean showAlreadyDecodedFrame = false;
    boolean paused = false;
    boolean looping = false;
    boolean isFirstFrame = true;
    long timeBeforePause = 0L;
    int currentVideoWidth;
    int currentVideoHeight;
    int videoBufferWidth;
    VideoPlayer.VideoSizeListener sizeListener;
    VideoPlayer.CompletionListener completionListener;
    FileHandle currentFile;
    BufferedInputStream inputStream;
    ReadableByteChannel fileChannel;
    boolean playing = false;

    public CommonVideoPlayerDesktop() {
    }

    abstract Music createMusic(VideoDecoder var1, ByteBuffer var2, int var3, int var4);

    private int getTextureWidth() {
        return this.videoBufferWidth;
    }

    private int getTextureHeight() {
        return this.currentVideoHeight;
    }

    public boolean play(FileHandle file) throws FileNotFoundException {
        if (file == null) {
            return false;
        } else if (!file.exists()) {
            throw new FileNotFoundException("Could not find file: " + file.path());
        } else {
            if (!FfMpeg.isLoaded()) {
                FfMpeg.loadLibraries();
            }

            if (this.decoder != null) {
                this.stop();
            }

            VideoDecoder.setDebug(Gdx.app.getLogLevel() >= 3);
            this.currentFile = file;
            this.inputStream = file.read(262144);
            this.fileChannel = Channels.newChannel(this.inputStream);
            this.isFirstFrame = true;
            this.decoder = new VideoDecoder();

            try {
                VideoDecoder.VideoDecoderBuffers buffers = this.decoder.loadStream(new VideoDecoder.VideoFileReader() {
                    public int fillBuffer(ByteBuffer buffer) {
                        return CommonVideoPlayerDesktop.this.readFileContents(buffer);
                    }
                });
                if (buffers == null) {
                    return false;
                }

                ByteBuffer audioBuffer = buffers.getAudioBuffer();
                if (audioBuffer != null) {
                    if (this.audio != null) {
                        this.audio.dispose();
                    }

                    this.audio = this.createMusic(this.decoder, audioBuffer, buffers.getAudioChannels(), buffers.getAudioSampleRate());
                }

                this.currentVideoWidth = buffers.getVideoWidth();
                this.currentVideoHeight = buffers.getVideoHeight();
                this.videoBufferWidth = buffers.getVideoBufferWidth();
                if (this.texture != null && (this.texture.getWidth() != this.getTextureWidth() || this.texture.getHeight() != this.getTextureHeight())) {
                    this.texture.dispose();
                    this.texture = null;
                }
            } catch (Exception var4) {
                var4.printStackTrace();
                return false;
            }

            if (this.sizeListener != null) {
                this.sizeListener.onVideoSize((float) this.currentVideoWidth, (float) this.currentVideoHeight);
            }

            this.playing = true;
            return true;
        }
    }

    private int readFileContents(ByteBuffer buffer) {
        try {
            buffer.rewind();
            return this.fileChannel.read(buffer);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public boolean update() {
        if (this.decoder != null && (!this.paused || this.isFirstFrame) && this.playing) {
            if (!this.paused && this.startTime == 0L) {
                this.startTime = System.currentTimeMillis();
                if (this.audio != null) {
                    this.audio.play();
                }
            }

            boolean newFrame = false;
            if (!this.showAlreadyDecodedFrame) {
                ByteBuffer videoData = this.decoder.nextVideoFrame();
                if (videoData == null) {
                    if (this.isFirstFrame) {
                        return false;
                    }

                    if (this.looping) {
                        try {
                            float volume = this.getVolume();
                            this.play(this.currentFile);
                            this.setVolume(volume);
                            return false;
                        } catch (FileNotFoundException var6) {
                            throw new RuntimeException(var6);
                        }
                    }

                    this.playing = false;
                    if (this.completionListener != null) {
                        this.completionListener.onCompletionListener(this.currentFile);
                    }

                    return false;
                }

                if (this.texture == null) {
                    this.texture = new Texture(this.getTextureWidth(), this.getTextureHeight(), Format.RGB888);
                    this.texture.setFilter(this.minFilter, this.magFilter);
                }

                this.texture.bind();
                Gdx.gl.glTexImage2D(3553, 0, 6407, this.getTextureWidth(), this.getTextureHeight(), 0, 6407, 5121, videoData);
                newFrame = true;
            }

            this.isFirstFrame = false;
            long currentVideoTime = System.currentTimeMillis() - this.startTime;
            long millisecondsAhead = (long) this.getCurrentTimestamp() - currentVideoTime;
            this.showAlreadyDecodedFrame = millisecondsAhead > 20L;
            return newFrame;
        } else {
            return false;
        }
    }

    public Texture getTexture() {
        return this.texture;
    }

    public boolean isBuffered() {
        return this.decoder != null ? this.decoder.isBuffered() : false;
    }

    public void stop() {
        this.playing = false;
        if (this.audio != null) {
            this.audio.dispose();
            this.audio = null;
        }

        if (this.decoder != null) {
            this.decoder.dispose();
            this.decoder = null;
        }

        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }

            this.inputStream = null;
        }

        this.startTime = 0L;
        this.showAlreadyDecodedFrame = false;
        this.isFirstFrame = true;
    }

    public void pause() {
        if (!this.paused) {
            this.paused = true;
            if (this.audio != null) {
                this.audio.pause();
            }

            if (this.startTime != 0L) {
                this.timeBeforePause = System.currentTimeMillis() - this.startTime;
            } else {
                this.timeBeforePause = 0L;
            }
        }

    }

    public void resume() {
        if (this.paused) {
            this.paused = false;
            if (this.audio != null) {
                this.audio.play();
            }

            this.startTime = System.currentTimeMillis() - this.timeBeforePause;
        }

    }

    public void dispose() {
        this.stop();
        if (this.texture != null) {
            this.texture.dispose();
            this.texture = null;
        }

    }

    public void setOnVideoSizeListener(VideoPlayer.VideoSizeListener listener) {
        this.sizeListener = listener;
    }

    public void setOnCompletionListener(VideoPlayer.CompletionListener listener) {
        this.completionListener = listener;
    }

    public int getVideoWidth() {
        return this.currentVideoWidth;
    }

    public int getVideoHeight() {
        return this.currentVideoHeight;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public float getVolume() {
        return this.audio == null ? 0.0F : this.audio.getVolume();
    }

    public void setVolume(float volume) {
        if (this.audio != null) {
            this.audio.setVolume(volume);
        }

    }

    public boolean isLooping() {
        return this.looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public int getCurrentTimestamp() {
        return (int) (this.decoder.getCurrentFrameTimestamp() * 1000.0);
    }
}
