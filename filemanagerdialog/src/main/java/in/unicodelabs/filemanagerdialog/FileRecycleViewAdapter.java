package in.unicodelabs.filemanagerdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import in.unicodelabs.filemanagerdialog.interfaces.OnFileSelectListener;
import in.unicodelabs.filemanagerdialog.interfaces.OnFolderSelectListener;
import in.unicodelabs.filemanagerdialog.utils.FileUtils;
import in.unicodelabs.filemanagerdialog.utils.ThemeUtils;

/**
 * Created by saurabh on 27/2/18.
 */

public class FileRecycleViewAdapter extends RecyclerView.Adapter<FileRecycleViewAdapter.FileListHolder> {

    private List<File> data = new LinkedList<>();


    private Context context;
    private FileRecycleView listerView;

    private boolean unreadableDir;
    private int colorAccent = -1;
    private OnFileSelectListener fileSelectListener;
    private OnFolderSelectListener folderSelectListener;
    private ArrayList<String> extensionFilter = new ArrayList<>();
    private boolean showDirOnly = false;
    private boolean showHiddenFolder = false;
    private boolean showCreateFolder = false;
    private File defaultDir = Environment.getExternalStorageDirectory();
    private File selectedFile = defaultDir;

    FileRecycleViewAdapter(FileRecycleView view) {
        this.context = view.getContext();
        listerView = view;

        colorAccent = ThemeUtils.resolveAccentColor(context);
    }

    void start() {
        fileLister(defaultDir);
    }

    private void fileLister(File dir) {
        Log.d("FileManager", "Load Dir " + dir.getAbsolutePath());

        LinkedList<File> fs = new LinkedList<>();

        if (dir.getAbsolutePath().equals("/")
                || dir.getAbsolutePath().equals("/storage")
                || dir.getAbsolutePath().equals("/storage/emulated")
                || dir.getAbsolutePath().equals("/mnt")) {
            unreadableDir = true;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                File[] vols = context.getExternalFilesDirs(null);
                if (vols != null && vols.length > 0) {
                    for (File file : vols) {
                        if (file != null) {
                            String path = file.getAbsolutePath();
                            path = path.replaceAll("/Android/data/([a-zA-Z_][.\\w]*)/files", "");
                            fs.add(new File(path));
                        }
                    }
                } else {
                    fs.add(Environment.getExternalStorageDirectory());
                }
            } else {
                String s = System.getenv("EXTERNAL_STORAGE");
                if (!TextUtils.isEmpty(s))
                    fs.add(new File(s));
                else {
                    String[] paths = FileUtils.getPhysicalPaths();
                    for (String path : paths) {
                        File f = new File(path);
                        if (f.exists())
                            fs.add(f);
                    }
                }
                s = System.getenv("SECONDARY_STORAGE");
                if (!TextUtils.isEmpty(s)) {
                    final String[] rawSecondaryStorages = s.split(File.pathSeparator);
                    for (String path : rawSecondaryStorages) {
                        File f = new File(path);
                        if (f.exists())
                            fs.add(f);
                    }
                }
            }
        } else {
            unreadableDir = false;
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    //Check if file is hidden or not
                    String fileName = file.getName();
                    if (!isShowHiddenFolder() && fileName.startsWith(".")) {
                        return false;
                    }

                    if (isShowDirOnly()) {
                        return file.isDirectory();
                    } else {
                        if (file.isDirectory()) {
                            return true;
                        } else {
                            if (!extensionFilter.isEmpty()) {
                                String ext = FileUtils.getExtension(file);
                                return extensionFilter.contains(ext);
                            } else {
                                return true;
                            }
                        }
                    }
                }
            });
            if (files != null) {
                fs = new LinkedList<>(Arrays.asList(files));
            }
        }

        data = new LinkedList<>(fs);
        Collections.sort(data, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if ((f1.isDirectory() && f2.isDirectory()))
                    return f1.getName().compareToIgnoreCase(f2.getName());
                else if (f1.isDirectory() && !f2.isDirectory())
                    return -1;
                else if (!f1.isDirectory() && f2.isDirectory())
                    return 1;
                else if (!f1.isDirectory() && !f2.isDirectory())
                    return f1.getName().compareToIgnoreCase(f2.getName());
                else return 0;
            }
        });
        selectedFile = dir;
        if (!dir.getAbsolutePath().equals("/")) {
            dirUp();
        }
        notifyDataSetChanged();
        listerView.scrollToPosition(0);

    }

    private void dirUp() {
        if (!unreadableDir) {
            data.add(0, selectedFile.getParentFile());
            if (isShowCreateFolder())
                data.add(1, null);
        }
    }

    @Override
    public FileListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileListHolder(View.inflate(context, R.layout.item_file_lister, null));
    }

    @Override
    public void onBindViewHolder(FileListHolder holder, int position) {
        File f = data.get(position);
        if (f != null) {
            holder.name.setText(f.getName());
        } else if (!unreadableDir) {
            holder.name.setText("Create a new Folder here");
            holder.icon.setImageResource(R.drawable.ic_create_new_folder_black_48dp);
        }
        if (unreadableDir) {
            if (f != null) {
                if (position == 0) {
                    holder.name.setText(f.getName() + " (Internal)");
                } else {
                    holder.name.setText(f.getName() + " (External)");
                }
            }
        }
        if (position == 0 && f != null && !unreadableDir) {
            holder.icon.setImageResource(R.drawable.ic_subdirectory_up_black_48dp);
        } else if (f != null) {
            if (f.isDirectory())
                holder.icon.setImageResource(R.drawable.ic_folder_black_48dp);
            else if (FileUtils.isImage(f))
                holder.icon.setImageResource(R.drawable.ic_photo_black_48dp);
            else if (FileUtils.isVideo(f))
                holder.icon.setImageResource(R.drawable.ic_videocam_black_48dp);
            else if (FileUtils.isAudio(f))
                holder.icon.setImageResource(R.drawable.ic_audiotrack_black_48dp);
            else
                holder.icon.setImageResource(R.drawable.ic_insert_drive_file_black_48dp);
        }
    }

    class FileListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView icon;

        FileListHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);

            icon.setColorFilter(colorAccent);

            itemView.findViewById(R.id.layout).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (data.get(getPosition()) == null) {
                View view = View.inflate(context, R.layout.dialog_create_folder, null);
                final AppCompatEditText editText = view.findViewById(R.id.edittext);
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setView(view)
                        .setTitle("Enter the folder name")
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = editText.getText().toString();
                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(context, "Please enter a valid folder name", Toast.LENGTH_SHORT).show();
                        } else {
                            File file = new File(selectedFile, name);
                            if (file.exists()) {
                                Toast.makeText(context, "This folder already exists.\n Please provide another name for the folder", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                file.mkdirs();
                                fileLister(file);
                            }
                        }
                    }
                });
            } else {
                File f = data.get(getPosition());
                selectedFile = f;
                Log.d("From FileLister", f.getAbsolutePath());
                if (f.isDirectory()) {
                    fileLister(f);
                    if (folderSelectListener != null)
                        folderSelectListener.onFolderSelected(null,f, f.getAbsolutePath());
                } else {
                    if (fileSelectListener != null)
                        fileSelectListener.onFileSelected(null,f, f.getAbsolutePath());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected void showDirectoriesOnly(boolean showDirOnly) {
        this.showDirOnly = showDirOnly;
    }

    protected void setOnFileSelectListener(OnFileSelectListener onFileSelectListener) {
        this.fileSelectListener = onFileSelectListener;
    }

    protected void setOnFolderSelectListener(OnFolderSelectListener onFolderSelectListener) {
        this.folderSelectListener = onFolderSelectListener;
    }

    protected void goToDefault() {
        fileLister(defaultDir);
    }

    protected File getSelectedFile() {
        return selectedFile;
    }

    protected File getDefaultDir() {
        return defaultDir;
    }

    protected void setDefaultDir(File defaultDir) {
        this.selectedFile = defaultDir;
        this.defaultDir = defaultDir;
    }

    protected ArrayList<String> getExtensionFilter() {
        return extensionFilter;
    }

    protected void setExtensionFilter(ArrayList<String> extensionFilter) {
        this.extensionFilter = extensionFilter;
    }

    public boolean isShowDirOnly() {
        return showDirOnly;
    }

    public void setShowDirOnly(boolean showDirOnly) {
        this.showDirOnly = showDirOnly;
    }

    public boolean isShowHiddenFolder() {
        return showHiddenFolder;
    }

    public void setShowHiddenFolder(boolean showHiddenFolder) {
        this.showHiddenFolder = showHiddenFolder;
    }

    public boolean isShowCreateFolder() {
        return showCreateFolder;
    }

    public void setShowCreateFolder(boolean showCreateFolder) {
        this.showCreateFolder = showCreateFolder;
    }
}
