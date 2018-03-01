package in.unicodelabs.filemanagerdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import in.unicodelabs.filemanagerdialog.enums.FILE_FILTER;
import in.unicodelabs.filemanagerdialog.interfaces.OnFileSelectListener;
import in.unicodelabs.filemanagerdialog.interfaces.OnFolderSelectListener;
import in.unicodelabs.filemanagerdialog.utils.FileUtils;

/**
 * Created by saurabh on 27/2/18.
 */

public class FileManagerDialogBuilder {

    private Context context;
    private int themeResId = 0;
    private String title = "";
    private OnFileSelectListener fileSelectListener;
    private OnFolderSelectListener folderSelectListener;
    private ArrayList<String> extensionFilter = new ArrayList<>();
    private boolean showDirOnly = false;
    private boolean showHiddenFolder = false;
    private boolean showCreateFolder = false;
    private File defaultDir;


    public FileManagerDialogBuilder(@NonNull Context context) {
        this.context = context;
    }

    public FileManagerDialogBuilder setTheme(int themeResId) {
        this.themeResId = themeResId;
        return this;
    }

    public FileManagerDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public FileManagerDialogBuilder setOnFileSelectListener(OnFileSelectListener listener) {
        this.fileSelectListener = listener;
        return this;
    }

    public FileManagerDialogBuilder setOnFolderSelectListener(OnFolderSelectListener listener) {
        this.folderSelectListener = listener;
        return this;
    }

    public FileManagerDialogBuilder setFileFilter(FILE_FILTER... fileFilter) {
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
        return this;
    }

    public FileManagerDialogBuilder setFileFilter(String... extension) {
        for (String s : extension) {
            //Check for . before extension
            if (s.startsWith(".")) s.replace(".", "");

            extensionFilter.add(s);
        }
        return this;
    }

    public FileManagerDialogBuilder showDirectoriesOnly(boolean showDirOnly) {
        this.showDirOnly = showDirOnly;
        return this;
    }

    public FileManagerDialogBuilder showHiddenFolder(boolean showHiddenFolder) {
        this.showHiddenFolder = showHiddenFolder;
        return this;
    }

    public FileManagerDialogBuilder showCreateFolder(boolean showHiddenFolder) {
        this.showCreateFolder = showCreateFolder;
        return this;
    }

    public FileManagerDialogBuilder setDefaultDir(@NonNull File file) {
        if (file.exists() || file.isDirectory()) {
            defaultDir = file;
            return this;
        } else {
            throw new RuntimeException("Default Dir dont exists or it is not a folder");
        }
    }

    public FileManagerDialogBuilder setDefaultDir(@NonNull String foldePath) {
        File file = new File(foldePath);
        if (file.exists() || file.isDirectory()) {
            defaultDir = file;
            return this;
        } else {
            throw new RuntimeException("Default Dir dont exists or it is not a folder");
        }
    }

    public AlertDialog create() {
        FileRecycleView fileRecycleView = new FileRecycleView(context);
        final FileRecycleViewAdapter adapter = new FileRecycleViewAdapter(fileRecycleView);
        fileRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fileRecycleView.setAdapter(adapter);

        //Set File and Folder Select delegates to adapter
        adapter.setOnFileSelectListener(new OnFileSelectListener() {
            @Override
            public void onFileSelected(File file, String path) {
                if(fileSelectListener != null)
                    fileSelectListener.onFileSelected(file,path);
            }
        });

        adapter.setOnFolderSelectListener(new OnFolderSelectListener() {
            @Override
            public void onFolderSelected(File folder, String path) {
                if(folderSelectListener != null)
                    folderSelectListener.onFolderSelected(folder,path);
            }
        });

        if (defaultDir != null)
            adapter.setDefaultDir(defaultDir);

        adapter.showDirectoriesOnly(showDirOnly);
        adapter.setShowHiddenFolder(showHiddenFolder);
        adapter.setShowCreateFolder(showCreateFolder);
        if (!extensionFilter.isEmpty())
            adapter.setExtensionFilter(extensionFilter);


        //Create Dialog to show folder/File list
        AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
        if (TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        } else {
            if (showDirOnly) {
                builder.setTitle("Select a Directory");
            } else {
                builder.setTitle("Select a file");
            }
        }

        builder.setView(fileRecycleView);

        builder.setNeutralButton("Default Dir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.goToDefault();
            }
        });

        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (folderSelectListener != null)
                    folderSelectListener.onFolderSelected(adapter.getSelectedFile(), adapter.getSelectedFile().getAbsolutePath());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        adapter.start();

        return builder.create();
    }

    public AlertDialog show() {
        final AlertDialog alertDialog = create();
        alertDialog.show();
        return alertDialog;
    }

}
