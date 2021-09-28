package photoOrderManager.service;

import photoOrderManager.App;
import photoOrderManager.model.DirectoryInfo;
import photoOrderManager.model.ImageInformation;

import java.io.File;
import java.util.List;

public class PhotoFinder {

    private App app;

    public PhotoFinder(App app) {
        this.app = app;
    }

    public int findPhotos(String path, boolean recursiveSearching){
        File f=new File(path);
        File[] tab=f.listFiles();
        int photoCount=0;
        DirectoryInfo directoryInfo=new DirectoryInfo(f.getName(),f.getPath());
        if (tab != null) {
            for (int i=0;i<tab.length;i++){
                if (tab[i].isDirectory()&&recursiveSearching){
                    int count=findPhotos(tab[i].getPath(),recursiveSearching);
                    photoCount+=count;
                }else{
                    int lastIndex=tab[i].getName().lastIndexOf(".");
                    if (lastIndex!=-1) {
                        String extension = tab[i].getName().substring(lastIndex);
                        if (extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".jpg")) {
                            ImageInformation imageInformation=ImageInformation.createImageInformation(tab[i].getAbsolutePath());
                            directoryInfo.getPathToImagesInDirectory().add(imageInformation);
                            photoCount++;
                        }
                    }
                }
            }
        }
        if (directoryInfo.getPathToImagesInDirectory().size()>0){
            app.getOrder().getDirectoryList().add(directoryInfo);
        }
        return photoCount;
    }

    public int findPhotosInAllDirectories(List<String> directoriesList){
        int numberOfPhotos=0;
        for (String path:directoriesList){
            int count=findPhotos(path,false);
            numberOfPhotos+=count;
        }
        return numberOfPhotos;
    }
}
