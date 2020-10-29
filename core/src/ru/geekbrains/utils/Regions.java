package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Regions {

    private Regions() {
    }

    /**
     * Splits TextureRegion into frames
     * @param region - region
     * @param rows - number of rows
     * @param cols - number of columns
     * @param frames - number of frames
     * @return - array of regions
     */
    public static TextureRegion[] split(TextureRegion region, int rows, int cols, int frames) {
        if (region == null) throw new RuntimeException("Split null region");
        TextureRegion[] regions = new TextureRegion[frames];
        int tileWidth = region.getRegionWidth() / cols;
        int tileHeight = region.getRegionHeight() / rows;

        int frame = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                regions[frame] = new TextureRegion(region, tileWidth * j, tileHeight * i, tileWidth, tileHeight);
                if (frame == frames - 1) return regions;
                frame++;
            }
        }
        return regions;
    }

}
