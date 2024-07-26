package lingmod.util;

import com.badlogic.gdx.graphics.Pixmap;

public class DixieMap extends Pixmap {


    DixieMap(int w, int h, Pixmap.Format f) {
        super(w, h, f);
    }

    DixieMap(int[] data, int w, int h, Pixmap.Format f) {
        super(w, h, f);

        int x, y;

        for (y = 0; y < h; y++) {
            for (x = 0; x < w; x++) {
                int pxl_ARGB8888 = data[x + y * w];
                int pxl_RGBA8888 = ((pxl_ARGB8888 >> 24) & 0x000000ff) | ((pxl_ARGB8888 << 8) & 0xffffff00);
                // convert ARGB8888 > RGBA8888
                drawPixel(x, y, pxl_RGBA8888);
            }
        }
    }

    void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        getPixels();
        for (y = 0; y < height; y++) {
            for (x = 0; x < width; x++) {
                int pxl_ARGB8888 = pixels[x + y * width];
                int pxl_RGBA8888 = ((pxl_ARGB8888 >> 24) & 0x000000ff) | ((pxl_ARGB8888 << 8) & 0xffffff00);
                // convert ARGB8888 > RGBA8888
                drawPixel(x, y, pxl_RGBA8888);
            }
        }

    }

    void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        java.nio.ByteBuffer bb = getPixels();

        int k, l;

        for (k = y; k < y + height; k++) {
            int _offset = offset;
            for (l = x; l < x + width; l++) {
                int pxl = bb.getInt(4 * (l + k * width));

                // convert RGBA8888 > ARGB8888
                pixels[_offset++] = ((pxl >> 8) & 0x00ffffff) | ((pxl << 24) & 0xff000000);
            }
            offset += stride;
        }
    }
}