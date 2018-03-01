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

public class FileManagerDialog extends AlertDialog {


    private FileManagerDialog(@NonNull Context context) {
        super(context);
    }

    private FileManagerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
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

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTheme(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setOnFileSelectListener(OnFileSelectListener listener) {
            this.fileSelectListener = listener;
            return this;
        }

        public Builder setOnFolderSelectListener(OnFolderSelectListener listener) {
            this.folderSelectListener = listener;
            return this;
        }

        public Builder setFileFilter(FILE_FILTER... fileFilter) {
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

        public Builder setFileFilter(String... extension) {
            for (String s : extension) {
                //Check for . before extension
                if (s.startsWith(".")) s.replace(".", "");

                extensionFilter.add(s);
            }
            return this;
        }

        public Builder showDirectoriesOnly(boolean showDirOnly) {
            this.showDirOnly = showDirOnly;
            return this;
        }

        public Builder showHiddenFolder(boolean showHiddenFolder) {
            this.showHiddenFolder = showHiddenFolder;
            return this;
        }

        public Builder showCreateFolder(boolean showCreateFolder) {
            this.showCreateFolder = showCreateFolder;
            return this;
        }

        public Builder setDefaultDir(@NonNull File file) {
            if (file.exists() || file.isDirectory()) {
                defaultDir = file;
                return this;
            } else {
                throw new RuntimeException("Default Dir dont exists or it is not a folder");
            }
        }

        public Builder setDefaultDir(@NonNull String foldePath) {
            File file = new File(foldePath);
            if (file.exists() || file.isDirectory()) {
                defaultDir = file;
                return this;
            } else {
                throw new RuntimeException("Default Dir dont exists or it is not a folder");
            }
        }

        public FileManagerDialog create() {
            FileRecycleView fileRecycleView = new FileRecycleView(context);
            final FileRecycleViewAdapter adapter = new FileRecycleViewAdapter(fileRecycleView);
            fileRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            fileRecycleView.setAdapter(adapter);


            if (defaultDir != null)
                adapter.setDefaultDir(defaultDir);

            adapter.showDirectoriesOnly(showDirOnly);
            adapter.setShowHiddenFolder(showHiddenFolder);
            adapter.setShowCreateFolder(showCreateFolder);
            if (!extensionFilter.isEmpty())
                adapter.setExtensionFilter(extensionFilter);


            //Create Dialog to show folder/File list
            final FileManagerDialog dialog = new FileManagerDialog(context, themeResId);
            if (TextUtils.isEmpty(title)) {
                dialog.setTitle(title);
            } else {
                if (showDirOnly) {
                    dialog.setTitle("Select a Directory");
                } else {
                    dialog.setTitle("Select a file");
                }
            }

            dialog.setView(fileRecycleView);

            dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Default Dir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.goToDefault();
                }
            });

            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Select", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (folderSelectListener != null)
                        folderSelectListener.onFolderSelected(dialog, adapter.getSelectedFile(), adapter.getSelectedFile().getAbsolutePath());
                }
            });

            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            //Set File and Folder Select delegates to adapter
            adapter.setOnFileSelectListener(new OnFileSelectListener() {
                @Override
                public void onFileSelected(FileManagerDialog fileManagerDialog, File file, String path) {
                    if (fileSelectListener != null)
                        fileSelectListener.onFileSelected(dialog, file, path);
                }
            });

            adapter.setOnFolderSelectListener(new OnFolderSelectListener() {
                @Override
                public void onFolderSelected(FileManagerDialog fileManagerDialog, File folder, String path) {
                    if (folderSelectListener != null)
                        folderSelectListener.onFolderSelected(dialog, folder, path);
                }
            });

            adapter.start();

            return dialog;
        }

        public FileManagerDialog show() {
            final FileManagerDialog alertDialog = create();
            alertDialog.show();
            return alertDialog;
        }
    }
}
