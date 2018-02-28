package in.unicodelabs.filemanagerdialog.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by saurabh on 27/2/18.
 */

public class FileUtils {

    public static String[] audioExtension = new String[]{"flac","mp3","mid","midi","xmf","mxmf","rtttl","rtx","ota","imy","ogg","wav","ts","aac","mp4","m4a","3gp","mkv"};
    public static String[] videoExtension = new String[]{"mkv","avi","mp4","wmv","3gp","webm","flv","vob","ogg","mng","mov","qt","m4v","mpg","mpeg","ts"};
    public static String[] imageExtension = new String[]{"jpg","jpeg","png","bmp","gif"};
    public static String[] docExtension = new String[]{"pdf","excel","text","doc","docx"};


    /**
     * Check whether the file is an image or not
     *
     * @param filename file path
     * @return Returns true if the file is an Image
     */
    public static boolean isImage(String filename) {
        String ext = getExtension(filename).toLowerCase();
        return Arrays.asList(imageExtension).contains(ext);
    }

    /**
     * Check whether the file is an image or not
     *
     * @param file file
     * @return Returns true if the file is an Image
     */
    public static boolean isImage(File file) {
        return isImage(file.getAbsolutePath());
    }

    /**
     * Check whether the file is a video or not
     *
     * @param filename file path
     * @return Returns true if the file is an Video
     */
    public static boolean isVideo(String filename) {
        String ext = getExtension(filename).toLowerCase();
        return Arrays.asList(videoExtension).equals(ext);
    }

    /**
     * Check whether the file is a video or not
     *
     * @param file file
     * @return Returns true if the file is an Video
     */
    public static boolean isVideo(File file) {
        return isVideo(file.getAbsolutePath());
    }

    /**
     * Check whether the file is a Document or not
     *
     * @param filename file path
     * @return Returns true if the file is an Video
     */
    public static boolean isDoc(String filename) {
        String ext = getExtension(filename).toLowerCase();
        return Arrays.asList(videoExtension).equals(ext);
    }

    /**
     * Check whether the file is a Document or not
     *
     * @param file file
     * @return Returns true if the file is an Video
     */
    public static boolean isDoc(File file) {
        return isDoc(file.getAbsolutePath());
    }

    /**
     * Check whether the file is a audio or not
     *
     * @param filename file
     * @return Returns true if the file is an Audio
     */
    public static boolean isAudio(String filename) {
        String ext = getExtension(filename).toLowerCase();
        return Arrays.asList(audioExtension).equals(ext);
    }

    /**
     * Check whether the file is a audio or not
     *
     * @param file file
     * @return Returns true if the file is an Audio
     */
    public static boolean isAudio(File file) {
        return isAudio(file.getAbsolutePath());
    }

    public static String getExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    public static String getExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }


    public static String[] getPhysicalPaths() {
        return new String[]{
                "/storage/sdsetCompatVectorFromResourcesEnabledcard0",
                "/storage/sdcard1",                 //Motorola Xoom
                "/storage/extsdcard",               //Samsung SGS3
                "/storage/sdcard0/external_sdcard", //User request
                "/mnt/extsdcard",
                "/mnt/sdcard/external_sd",          //Samsung galaxy family
                "/mnt/external_sd",
                "/mnt/media_rw/sdcard1",            //4.4.2 on CyanogenMod S3
                "/removable/microsd",               //Asus transformer prime
                "/mnt/emmc",
                "/storage/external_SD",             //LG
                "/storage/ext_sd",                  //HTC One Max
                "/storage/removable/sdcard1",       //Sony Xperia Z1
                "/data/sdext",
                "/data/sdext2",
                "/data/sdext3",
                "/data/sdext4",
                "/sdcard1",                         //Sony Xperia Z
                "/sdcard2",                         //HTC One M8s
                "/storage/microsd"                  //ASUS ZenFone 2
        };
    }
}
