package photoOrderManager.model;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

import java.io.File;
import java.io.IOException;

public class ImageInformation {

    private String pathToImage;
    private String imageName;
    private int orientation;

    public ImageInformation(String pathToImage, String imageName, int orientation) {
        this.pathToImage = pathToImage;
        this.imageName = imageName;
        this.orientation = orientation;
    }

    private ImageInformation(String pathToImage, String imageName) {
        this.pathToImage = pathToImage;
        this.imageName = imageName;
        orientation=1;
    }

    public static ImageInformation createImageInformation(String path){
        File file =new File(path);
        String fileName=getImageNameFromPath(path);
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException | IOException e) {

        }
        if (metadata!=null) {
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            int orientation = 1;
            if (directory != null) {
                try {
                    orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                } catch (MetadataException me) {

                }
                return new ImageInformation(path,fileName,orientation);
            }
        }
        return new ImageInformation(path,fileName);
    }

    private static String getImageNameFromPath(String path){
        int lastIndexOf = path.lastIndexOf("\\");
        String name=path;
        if (lastIndexOf!=-1){
            name = path.substring(lastIndexOf + 1);
        }
        return name;
    }

    public int getOrientation() {
        return orientation;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public String getImageName() {
        return imageName;
    }
}
