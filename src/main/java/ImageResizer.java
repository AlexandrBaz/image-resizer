import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable {
    private final File[] files;
    private final int newWidth;
    private final String dstFolder;
    private final long start;
    private final int core;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start, int core){
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
        this.core = core;
    }

    @Override
    public void run() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }
                BufferedImage newImage = resizeImage(image, newWidth);
                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }

        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Error with resizer" + ex);
        }
        System.out.println("finished after start: " + " core - " + core + " - " + (System.currentTimeMillis() - start) + "ms");
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, Scalr.Method.BALANCED, Scalr.Mode.AUTOMATIC, targetWidth, Scalr.OP_ANTIALIAS);
    }
}

