package lc.utils;

import com.badlogic.gdx.math.MathUtils;

/**
 * @author Death
 */
public final class NoisePerlin {

    public static float[][] getNoise(int w, int h){
        return getNoise(w, h, MathUtils.random(1f, 20f));
    }
    
    
    public static float[][] getNoise(int w, int h, float rnd){
        float[][] noise = new float[w][h];
        
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                noise[i][j] = perlinNoise(i, j, rnd);
            }
        }
        return noise;
    }
    
    private static float noise2D(float xl, float yl){
        int x = (int)(xl);
        int y = (int)(yl);
        int n = x + y * 57;
        n = (n << 13) ^ n;
        return (1.0f - ((n * (n * n * 1573199 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0f);
    }
    
    private static float smoothedNoise2D(float x, float y){
        float cornes = (noise2D(x - 1, y - 1) + noise2D(x + 1, y - 1) + noise2D(x - 1, y + 1) + noise2D(x + 1, y + 1)) / 16f;
        float sides = (noise2D(x - 1, y) + noise2D(x + 1, y) + noise2D(x, y + 1) + noise2D(x, y - 1)) / 8f;
        float center = noise2D(x, y) / 4f;
        return cornes + sides + center;
    }
    
    private static float cosineInterpolate(float x, float a, float b){
        float ft = x + 3.1415927f;
        float f = (float) ((1 - Math.cos(ft)) * 0.5f);
        return a * (1 - f) + b * f;
    }
    
    private static float jamesLong(float x, float y, float a){
        float fac1 = (float) (3 * Math.pow(1 - a, 2) - 2 * Math.pow(1 - a, 3));
        float fac2 = (float) (3 * Math.pow(a, 2) - 2 * Math.pow(a, 3));
        return x * fac1 + y * fac2;
    }
    
    private static float compileNoise(float x, float y){
        float intX = (float)Math.floor(x);
        float intY = (float)Math.floor(y);
        float fractalX = x - intX;
        float fractalY = y - intY;
        float v1 = smoothedNoise2D(intX, intY);
        float v2 = smoothedNoise2D(intX + 1, intY);
        float v3 = smoothedNoise2D(intX, intY + 1);
        float v4 = smoothedNoise2D(intX + 1, intY + 1);
        
        float i1 = jamesLong(v1, v2, fractalX);
        float i2 = jamesLong(v3, v4, fractalX);
        
        return jamesLong(i1, i2, fractalY);
    }
    
    private static float perlinNoise(float x, float y, float factor){
        float total = 0;
        float persistence = MathUtils.PI * 4;
       
        float frequency = 0.25f;
        float amplitude = 3;
        
        x += factor;
        y += factor;
        
        for(int i = 0; i < 1; i++){
            total += compileNoise(x * frequency, y * frequency) * amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }
        return (total);
    }
}
