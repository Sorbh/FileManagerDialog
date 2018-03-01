package in.unicodelabs.filemanagerdialog.interfaces;

import java.io.File;

import in.unicodelabs.filemanagerdialog.FileManagerDialog;

/**
 * Created by saurabh on 1/3/18.
 */

public interface OnFolderSelectListener {
    void onFolderSelected(FileManagerDialog fileManagerDialog,File folder, String path);
}
