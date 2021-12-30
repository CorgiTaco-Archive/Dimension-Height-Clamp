package corgitaco.dimensionheightclamp.config;

import corgitaco.dimensionheightclamp.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.Mth;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class Config {

    public static final Config DEFAULT = new Config(new IntClamper(-2048, -64), new IntClamper(320, 2048));

    public static Path configDir = null;

    private static Config INSTANCE = null;
    private final IntClamper minYClamp;
    private final IntClamper maxYClamp;

    public Config(IntClamper minYClamp, IntClamper maxYClamp) {
        this.minYClamp = minYClamp;
        this.maxYClamp = maxYClamp;
    }

    public static Config getConfig() {
        return getConfig(false);
    }

    public static Config getConfig(boolean serialize) {
        if (INSTANCE == null || serialize) {
            INSTANCE = readConfig();
        }

        return INSTANCE;
    }

    private static Config readConfig() {
        if (configDir == null) {
            throw new UnsupportedOperationException("Dimension type was statically initialized before the config directory was set or before the game was boot strapped, this is very very bad and is most likely the fault of another mod.");
        }
        final Path path = configDir.resolve(Constants.MOD_ID + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path.getParent());
                BufferedWriter writer = Files.newBufferedWriter(path);
                final String s = gson.toJson(DEFAULT);
                Files.write(path, s.getBytes());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            return gson.fromJson(new FileReader(path.toFile()), Config.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return DEFAULT;
    }

    public IntClamper minYClamp() {
        return minYClamp;
    }

    public IntClamper maxYClamp() {
        return maxYClamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Config) obj;
        return Objects.equals(this.minYClamp, that.minYClamp) &&
                Objects.equals(this.maxYClamp, that.maxYClamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minYClamp, maxYClamp);
    }

    @Override
    public String toString() {
        return "Config[" +
                "minYClamp=" + minYClamp + ", " +
                "maxYClamp=" + maxYClamp + ']';
    }


    public static final class IntClamper {
        private final int min;
        private final int max;

        public IntClamper(int min, int max) {
            if (min > max) {
                throw new IllegalArgumentException(String.format("Min cannot be greater than max: %s", this.toString()));
            }
            this.min = min;
            this.max = max;
        }

        public int clampAndGet(int dimensionY) {
            return (Mth.clamp(dimensionY, this.min, this.max) / 16) * 16;
        }

        public int min() {
            return min;
        }

        public int max() {
            return max;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (IntClamper) obj;
            return this.min == that.min &&
                    this.max == that.max;
        }

        @Override
        public int hashCode() {
            return Objects.hash(min, max);
        }

        @Override
        public String toString() {
            return "IntClamper[" +
                    "min=" + min + ", " +
                    "max=" + max + ']';
        }


    }
}
