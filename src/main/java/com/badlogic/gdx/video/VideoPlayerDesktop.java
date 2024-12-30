package com.badlogic.gdx.video;

import com.badlogic.gdx.audio.Music;
import java.nio.ByteBuffer;

public class VideoPlayerDesktop extends CommonVideoPlayerDesktop {
   Music createMusic(VideoDecoder decoder, ByteBuffer audioBuffer, int audioChannels, int sampleRate) {
      return new RawMusic(decoder, audioBuffer, audioChannels, sampleRate);
   }
}
