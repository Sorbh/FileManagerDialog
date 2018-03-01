package in.unicodelabs.filemanagerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;

import in.unicodelabs.filemanagerdialog.FileManagerDialog;
import in.unicodelabs.filemanagerdialog.enums.FILE_FILTER;
import in.unicodelabs.filemanagerdialog.interfaces.OnFileSelectListener;
import in.unicodelabs.filemanagerdialog.interfaces.OnFolderSelectListener;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    TextView textView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        context = this;

        textView = (TextView) findViewById(R.id.textView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FileManagerDialog.Builder builder = new FileManagerDialog.Builder(context);
//               builder.setTheme(themeResId);
                builder.setTitle("File Manage Dialog");
                builder.setOnFileSelectListener(new OnFileSelectListener() {
                    @Override
                    public void onFileSelected(FileManagerDialog fileManagerDialog, File file, String path) {
                        //Your business logic here
                        textView.setText(path);
                    }
                });

                builder.setOnFolderSelectListener(new OnFolderSelectListener() {
                    @Override
                    public void onFolderSelected(FileManagerDialog fileManagerDialog, File folder, String path) {
                        //Your business logic here
                        textView.setText(path);
                    }
                });
//
//                builder.setFileFilter(FILE_FILTER.ALL_FILES);
//                builder.setFileFilter(FILE_FILTER.IMAGE_ONLY);
//                builder.setFileFilter(FILE_FILTER.AUDIO_ONLY);
//                builder.setFileFilter(FILE_FILTER.VIDEO_ONLY);
//                builder.setFileFilter(FILE_FILTER.DOC_ONLY);
//
//                builder.showDirectoriesOnly(true);
//
//                builder.showHiddenFolder(true);
//
                builder.showCreateFolder(true);
//
//                builder.setDefaultDir(file);
//
//                builder.setDefaultDir(folderPath);
//
//                FileManagerDialog fileManagerDialog = builder.create();

                builder.show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
