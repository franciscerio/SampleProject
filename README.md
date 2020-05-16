# SampleProject

[minSdk 23](https://twitter.com/minsdkversion) - as all practical, and rational developers should use

#### Architecture
App is purely written in kotlin, Used MVVM architectural pattern

#### UI annd Design
Functional, intuitive and aestethically pleasing design. Originally planned to be minimal and neutral but later on decided to choose my own palette, followed Material Design guidelines such as emphasis on vital information, proper spacing, proper use of shapes and white spaces.


#### Dependencies
Most of these are pretty much community standards.

* Dagger2 - [Depedency injector](https://github.com/google/dagger) for android.
* Room & ViewModel - used for persistence, also used ViewModels to mannage UI-related data i.e persisted data, view objects. 
* RxJava & LiveData - reactive UI, works seemlessly with data emmited by data sources.. Also for thread management.
* Unit testing - Mockito
