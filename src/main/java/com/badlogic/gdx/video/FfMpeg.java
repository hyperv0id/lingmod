package com.badlogic.gdx.video;

import com.badlogic.gdx.utils.SharedLibraryLoader;

public class FfMpeg {
   public static final String NATIVE_LIBRARY_NAME = "gdx-video-desktop";
   private static boolean loaded = false;
   private static String libraryPath;

   public static void setLibraryFilePath(String path) {
      libraryPath = path;
   }

   public static boolean loadLibraries() {
      if (loaded) {
         return true;
      } else {
         SharedLibraryLoader libLoader;
         if (libraryPath == null) {
            libLoader = new SharedLibraryLoader();
         } else {
            libLoader = new SharedLibraryLoader(libraryPath);
         }

         try {
            libLoader.load("gdx-video-desktop");
         } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            loaded = false;
            return false;
         }

         loaded = true;
         return true;
      }
   }

   public static boolean isLoaded() {
      return loaded;
   }

   public static void setDebugLogging(boolean debugLogging) {
      if (loaded || loadLibraries()) {
         setDebugLoggingNative(debugLogging);
      }
   }

   private static native void setDebugLoggingNative(boolean var0);
}
