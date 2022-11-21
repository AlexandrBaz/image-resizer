import java.io.File;

public class Main {

    public static int newWidth = 300;

    public static void main(String[] args) {
        String scrFolder = "C:\\Users\\Admin\\Desktop\\fototest";
        String dtsFolder = "C:\\Users\\Admin\\Desktop\\resize";

        File scrDir = new File(scrFolder);

        long start = System.currentTimeMillis();

        File[] files = scrDir.listFiles();

        int cores = Runtime.getRuntime().availableProcessors();

        assert files != null;
        int half = (int)Math.ceil((double) files.length / cores);
        int partStart = 0;
        int arrayLength = half;
        for (int i = 0; i<cores; i++) {
            File [] partOfFiles = new File[half];
            System.arraycopy(files, partStart, partOfFiles, 0, partOfFiles.length);
            ImageResizer resizer = new ImageResizer(partOfFiles, newWidth, dtsFolder, start,i);
            new Thread(resizer).start();
            partStart +=half;
            arrayLength +=half;
            if (arrayLength > files.length){
                half = files.length - partStart;
            }
        }
    }
}
