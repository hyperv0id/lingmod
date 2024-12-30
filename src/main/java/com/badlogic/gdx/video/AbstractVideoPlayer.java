package com.badlogic.gdx.video;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public abstract class AbstractVideoPlayer implements VideoPlayer {
   protected Texture.TextureFilter minFilter;
   protected Texture.TextureFilter magFilter;

   public AbstractVideoPlayer() {
      this.minFilter = TextureFilter.Linear;
      this.magFilter = TextureFilter.Linear;
   }

   public void setFilter(Texture.TextureFilter minFilter, Texture.TextureFilter magFilter) {
      if (this.minFilter != minFilter || this.magFilter != magFilter) {
         this.minFilter = minFilter;
         this.magFilter = magFilter;
         Texture texture = this.getTexture();
         if (texture != null) {
            texture.setFilter(minFilter, magFilter);
         }
      }
   }
}
