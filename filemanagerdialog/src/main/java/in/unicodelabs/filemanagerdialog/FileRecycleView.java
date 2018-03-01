package in.unicodelabs.filemanagerdialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by saurabh on 27/2/18.
 */

public class FileRecycleView extends RecyclerView {

    private FileRecycleViewAdapter adapter;

    FileRecycleView(Context context) {
        super(context);
    }

    FileRecycleView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    FileRecycleView(Context context,AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}