package in.unicodelabs.filemanagerdialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.io.File;
import java.util.ArrayList;

import in.unicodelabs.filemanagerdialog.interfaces.OnFileSelectListener;

/**
 * Created by saurabh on 27/2/18.
 */

public class FileRecycleView extends RecyclerView {

    private FileRecycleViewAdapter adapter;

    FileRecycleView(Context context) {
        super(context);
        init();
    }

    FileRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    FileRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new FileRecycleViewAdapter(this);
    }

    void start() {
        setAdapter(adapter);
        adapter.start();
    }

    void setDefaultDir(File file) {
        adapter.setDefaultDir(file);
    }

    void setDefaultDir(String path) {
        setDefaultDir(new File(path));
    }

    File getSelected() {
        return adapter.getSelected();
    }

    void goToDefaultDir() {
        adapter.goToDefault();
    }

    /**
     * Pass file extension to adapter so adapter can filter the files before listing them
     *
     * @param extensionFilter
     */
    void setFileFilter(ArrayList<String> extensionFilter) {
        adapter.setFileFilter(extensionFilter);
    }

    /**
     * Just pass flag to adater of the recycle view
     *
     * @param showDirOnly
     */
    public void showDirectoriesOnly(boolean showDirOnly) {
       adapter.showDirectoriesOnly(showDirOnly);
    }

    /**
     * Just pass listener to adapter
     *
     * @param onFileSelectListener
     */
    protected void setOnFileSelectListener(OnFileSelectListener onFileSelectListener) {
        adapter.setOnFileSelectListener(onFileSelectListener);
    }
}