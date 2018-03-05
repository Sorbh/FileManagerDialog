# FileManagerDialog
A Dialog for select file in Android. You can customize your request to select file.
  
  *  It pickup colorAccent of the root project and applied it to FileManager dialog color theme. 

# Getting started

## Installing 
To use this library simply import it by placing the following line under dependencies in your app module's build.gradle file

This library is posted in jCenter

#### Gradle
```
implementation 'in.unicodelabs.sorbh:FileManagerDialog:1.0.1'
```

#### Maven
```
<dependency>
  <groupId>in.unicodelabs.sorbh</groupId>
  <artifactId>FileManagerDialog</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

This library has android support library dependencies. Please add this variable to your project's root build.gradle

```
ext{
    supportLibraryVerion = "<your support lib version>"
}
```

# Usage

After Importing this library you just have to build the FileDialogManager through builder pattern to list down all the file and folder and get callback for selected file/folder.

  *  Get the simple basic FileDialog without any callback with default setting
      ```
      FileManagerDialog.Builder builder =  new FileManagerDialog.Builder(context);
      builder.show();
      ```
  This will return you instance of FileManager dialog,which you can dismiss on file selection or folder selection.
  
  
  *  To add theme to dialog
      ```
      builder.setTheme(themeResId);
      ```
  
  
  *  To add custom title to dialog box
      ```
      builder.setTitle(title);
      ```
  
  
  *  To get callback on File Selection
      ```
      builder.setOnFileSelectListener(new OnFileSelectListener() {
                          @Override
                          public void onFileSelected(FileManagerDialog fileManagerDialog, File file, String path) {
                              //Your business logic here
                          }
      }); 
      ```
  
  
  *  To get callback on folder Selection
      ```
      builder.setOnFolderSelectListener(new OnFolderSelectListener() {
                          @Override
                          public void onFolderSelected(FileManagerDialog fileManagerDialog, File folder, String path) {
                          //Your business logic here
                          }
      });
      ```
  
  
  *  To set the file type to filter the files to be listed in FileManaget Dialog
      ```
       builder.setFileFilter(FILE_FILTER.ALL_FILES);
      ```
  
  
  *  FILE_FILTER Enum have following possible values
      ```
      builder.setFileFilter(FILE_FILTER.ALL_FILES);
      builder.setFileFilter(FILE_FILTER.IMAGE_ONLY);
      builder.setFileFilter(FILE_FILTER.AUDIO_ONLY);
      builder.setFileFilter(FILE_FILTER.VIDEO_ONLY);
      builder.setFileFilter(FILE_FILTER.DOC_ONLY);
      ```
  
  
  *  Even you set custom filter too, like you want to list only .doc file.
      ```
      builder.setFileFilter(".doc")
      ```
      or
      ```
      builder.setFileFilter("doc")
      ```
  
  
  *  In case you want to show directory only in the list,by default this setting is false
      ```
      builder.showDirectoriesOnly(true);
      ```
  
  
  *  In case you want to show hidden files and folder too, by default this setting is false
      ```
      builder.showHiddenFolder(true);
      ```
  
  
  *  If you want to give user option to create new folder in FileManager Dialog, by default this setting is false
      ```
       builder.showCreateFolder(true);
      ```
  
  
  *  If you want to change default directory, by default is sd card root.
      ```
       builder.setDefaultDir(file);
      ```
      or
      ```
      builder.setDefaultDir(folderPath);
      ```
  
  *  finally after all these setting to get the FileManager dialog instance call create()
      ```
      FileManagerDialog fileManagerDialog = builder.create();
      ```
  
  
  *  To show the dialog just call show() on builder or FileManager instance;
      ```
      builder.show();
      ```
      
      or
      ```
      fileManagerDialog.show();
      ```
  
##### FileManagerDialog class is extends AlertDialog, so all the alert dialog methods are available to this class.

# Screenshots
##### FileManager Dialog with root project color accent
![alt text](https://github.com/sorbh/FileManagerDialog/blob/master/raw/1.jpg) 
![alt text](https://github.com/sorbh/FileManagerDialog/blob/master/raw/2.jpg)

#### After Selecting File
![alt text](https://github.com/sorbh/FileManagerDialog/blob/master/raw/3.jpg)

#### After Selecting Folder
![alt text](https://github.com/sorbh/FileManagerDialog/blob/master/raw/4.jpg)

# Author
  * **Saurabh K Sharma - [GIT](https://github.com/Sorbh)**
  
      I am very new to open source community. All suggestion and improvement are most welcomed. 
  
 
## Contributing

1. Fork it (<https://github.com/sorbh/FileManagerDialog/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request


# License

```
Copyright 2018 Saurabh Kumar Sharma

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
