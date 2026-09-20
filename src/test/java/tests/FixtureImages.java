package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public final class FixtureImages {
    private static final String PNG =
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+aTe8AAAAASUVORK5CYII=";

    private FixtureImages() {}

    public static String[] createFive() throws IOException {
        Path dir = Files.createTempDirectory("realestate-qa-images-");
        dir.toFile().deleteOnExit();
        String[] paths = new String[5];
        for (int i = 0; i < paths.length; i++) {
            Path file = dir.resolve("property-" + i + ".png");
            Files.write(file, Base64.getDecoder().decode(PNG));
            file.toFile().deleteOnExit();
            paths[i] = file.toAbsolutePath().toString();
        }
        return paths;
    }
}
