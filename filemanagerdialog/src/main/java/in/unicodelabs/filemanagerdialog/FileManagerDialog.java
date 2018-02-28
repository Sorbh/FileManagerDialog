package in.unicodelabs.filemanagerdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import in.unicodelabs.filemanagerdialog.interfaces.OnFileSelectListener;
import in.unicodelabs.filemanagerdialog.utils.FileUtils;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by saurabh on 27/2/18.
 */

public class FileManagerDialog {

    /**
     * File Filter for the FileManagerDialog
     */
    public enum FILE_FILTER {
        /**
         * List All Files
         */
        ALL_FILES,
        /**
         * List Directory and Image files
         */
        IMAGE_ONLY,
        /**
         * List Directory and Video files
         */
        VIDEO_ONLY,
        /**
         * List Directory and Audio files
         */
        AUDIO_ONLY,
        /**
         * List Directory and Doc files
         */
        DOC_ONLY
    }

    private AlertDialog alertDialog;

    private FileRecycleView fileRecycleView;

    private OnFileSelectListener onFileSelectListener;

    private ArrayList<String> extensionFilter = new ArrayList<>();
    private boolean showDirOnly = false;

    private FileManagerDialog(@NonNull Context context) {
        //super(context);
        alertDialog = new AlertDialog.Builder(context).create();
        init(context);
    }

    private FileManagerDialog(@NonNull Context context, int themeResId) {
        //super(context, themeResId);
        alertDialog = new AlertDialog.Builder(context, themeResId).create();
        init(context);
    }

    /**
     * Creates a default instance of FileManagerDialog
     *
     * @param context Context of the App
     * @return Instance of FileManagerDialog
     */
    public static FileManagerDialog createFileManagerDialog(@NonNull Context context) {
        return new FileManagerDialog(context);
    }

    /**
     * Creates an instance of FileManagerDialog with the specified Theme
     *
     * @param context Context of the App
     * @param themeId Theme Id for the dialog
     * @return Instance of FileManagerDialog
     */
    public static FileManagerDialog createFileManagerDialog(@NonNull Context context, int themeId) {
        return new FileManagerDialog(context, themeId);
    }

    private void init(Context context) {
        fileRecycleView = new FileRecycleView(context);

        alertDialog.setView(fileRecycleView);
        alertDialog.setButton(BUTTON_POSITIVE, "Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (onFileSelectListener != null)
                    onFileSelectListener.onFileSelected(fileRecycleView.getSelected(), fileRecycleView.getSelected().getAbsolutePath());
            }
        });
        alertDialog.setButton(BUTTON_NEUTRAL, "Default Dir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fileRecycleView.goToDefaultDir();
            }
        });
        alertDialog.setButton(BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    /**
     * Display the FileManagerDialog
     */
    public void show() {
        //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        if(showDirOnly){
            alertDialog.setTitle("Select a Directory");
        }else {
            alertDialog.setTitle("Select a file");
        }

        fileRecycleView.start();
        alertDialog.show();


    }

    /**
     * Listener to know which file/directory is selected
     *
     * @param onFileSelectListener Instance of the Listener
     */
    public void setOnFileSelectListener(OnFileSelectListener onFileSelectListener) {
        this.onFileSelectListener = onFileSelectListener;

        //Set file select listener for adapter too and in case of file selection
        //close the alert dialog and continue call back chain
        fileRecycleView.setOnFileSelectListener(new OnFileSelectListener() {
            @Override
            public void onFileSelected(File file, String path) {
                if (FileManagerDialog.this.onFileSelectListener != null) {
                    alertDialog.dismiss();
                    FileManagerDialog.this.onFileSelectListener.onFileSelected(file, path);
                }

            }
        });
    }

    /**
     * Set the initial directory to show the list of files in that directory
     *
     * @param file File pointing to the directory
     */
    public void setDefaultDir(@NonNull File file) {
        fileRecycleView.setDefaultDir(file);
    }

    /**
     * Set the initial directory to show the list of files in that directory
     *
     * @param file String denoting to the directory
     */
    public void setDefaultDir(@NonNull String file) {
        fileRecycleView.setDefaultDir(file);
    }

    /**
     * Set the file filter for listing the files
     *
     * @param fileFilter One of the FILE_FILTER values
     */
    public void setFileFilter(FILE_FILTER... fileFilter) {
        for (FILE_FILTER file_filter : fileFilter) {
            switch (file_filter) {
                case ALL_FILES:
                    extensionFilter.clear();
                    break;
                case IMAGE_ONLY:
                    extensionFilter.addAll(Arrays.asList(FileUtils.imageExtension));
                    break;
                case VIDEO_ONLY:
                    extensionFilter.addAll(Arrays.asList(FileUtils.videoExtension));
                    break;
                case AUDIO_ONLY:
                    extensionFilter.addAll(Arrays.asList(FileUtils.audioExtension));
                    break;
                case DOC_ONLY:
                    extensionFilter.addAll(Arrays.asList(FileUtils.docExtension));
                    break;
            }
        }
        fileRecycleView.setFileFilter(extensionFilter);
    }

    public void setFileFilter(String... extension) {
        for (String s : extension) {
            //Check for . before extension
            if (s.startsWith(".")) s.replace(".", "");
            extensionFilter.add(s);
        }
        fileRecycleView.setFileFilter(extensionFilter);
    }

    public void showDirectoriesOnly(boolean showDirOnly) {
        this.showDirOnly = showDirOnly;
        fileRecycleView.showDirectoriesOnly(showDirOnly);
    }

    public boolean isShowDirOnly() {
        return showDirOnly;
    }

}
