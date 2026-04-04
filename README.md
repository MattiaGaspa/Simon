# Simon
First prototype of a Simon game variant, developed using Kotlin, Jetpack Compose and Material3 design for Android.

## Project architecture
The key files of the project are located in:
- `MainActivity.ky` and `HistoryActivity.kt`: Definition of the application's activities.
- `components/`: Package containing the Composable functions that make up the UI.
- `stateHolders/`: Package containing the classes that manage the activities' states.
- `ui.theme/Color.kt`: Where the application's colors are defined.

The application was developed with modularity and reusability in mind: each UI feature is defined in its own Composable function (in `composable/`).
Therefore, UI management within the activities is limited to arranging these macro-components.
Fragments were not used because they have not yet been covered in class and would be overkill for a project this scale.

Activity states are encapsulated in classes to centralize state management.
Components in the `components/` package only require the state holder class. Internally, the components will call
the state holder class' helper methods to interact with the states.
To make the components usable for every activity, an interface can be created to define which methods the components need.
Then, the component's interface is implemented for each activity's state holder class in which the component is used.

## Project development
This project was made with:
- **Android Studio Panda 1 | 2025.3.1 Patch 1**
  Build #AI-253.29346.138.2531.14876573, built on February 12, 2026
- Runtime version: 21.0.9+-14649483-b1163.86 amd64
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
- Toolkit: sun.awt.X11.XToolkit
- Linux 6.19.10-1-default
- openSUSE Tumbleweed; glibc: 2.42
- GC: G1 Young Generation, G1 Concurrent GC, G1 Old Generation
- Memory: 2048M
- Cores: 16
- Registry:
  - ide.experimental.ui=true
  - Current Desktop: KDE
- **Gradle 9.2.1**

## Tested on
 - Realme 9 Pro+ 5G: Android 14.0 ("UpsideDownCake"), API 34-ext21. Phone detailed specifications available [here](https://www.realme.com/it/realme-9-pro-plus/specs).
 - Virtual Pixel 2 phone.