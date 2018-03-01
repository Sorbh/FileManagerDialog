package in.unicodelabs.filemanagerdialog.interfaces;

import java.io.File;

import in.unicodelabs.filemanagerdialog.FileManagerDialog;

/**
 * Created by saurabh on 27/2/18.
 */

public interface OnFileSelectListener {
    void onFileSelected(FileManagerDialog fileManagerDialog,File file, String path);
}
