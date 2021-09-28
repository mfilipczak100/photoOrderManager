package photoOrderManager.model;

import java.util.ArrayList;

public class DirectoryInfo {

    private String directoryName;
    private String directoryPath;
    private int index;
    private ArrayList<ImageInformation> pathToImagesInDirectory;

    public DirectoryInfo(String directoryName, String directoryPath) {
        this.directoryName = directoryName;
        this.directoryPath = directoryPath;
        index=0;
        pathToImagesInDirectory=new ArrayList<>();
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<ImageInformation> getPathToImagesInDirectory() {
        return pathToImagesInDirectory;
    }

    @Override
    public String toString() {
        if (!directoryName.equals("")) {
            return directoryName;
        }
        return "/";
    }
}
