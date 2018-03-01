# FileManagerDialog
A Dialog for select file in Android. You can customize your request to select file.

# Getting started

## Installing 
To use this library simply import it by placing the following line under dependencies in your app module's build.gradle file

This library is posted in jCenter

#### Gradle
```
implementation 'in.unicodelabs.sorbh:FileManagerDialog:1.0.0'
```

#### Maven
```
<dependency>
  <groupId>in.unicodelabs.sorbh</groupId>
  <artifactId>FileManagerDialog</artifactId>
  <version>1.0.0</version>
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

  Get the simple basic FileDialog without any callback
  ```
  new FileManagerDialogBuilder(context).show();
  ```
  This will return you instance of alert dialog manager,which you can dismiss on file selection or folder selection.
  
  
# Author
  * **Saurabh K Sharma - [GIT](https://github.com/Sorbh)**
  

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
