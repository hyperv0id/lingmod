package com.badlogic.gdx.video.scenes.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.video.VideoPlayer;

public class VideoActor extends Actor {
   private final VideoPlayer player;

   public VideoActor(VideoPlayer player) {
      if (player == null) {
         throw new GdxRuntimeException("VideoActor: player must not be null!");
      } else {
         this.player = player;
      }
   }

   public void act(float delta) {
      super.act(delta);
      this.player.update();
   }

   public void draw(Batch batch, float parentAlpha) {
      Texture texture = this.player.getTexture();
      if (texture != null) {
         batch.draw(texture, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 0, 0, this.player.getVideoWidth(), this.player.getVideoHeight(), false, false);
      }
   }

   public VideoPlayer getVideoPlayer() {
      return this.player;
   }
}
