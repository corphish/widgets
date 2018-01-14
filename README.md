# Widgets
[![](https://jitpack.io/v/corphish/Widgets.svg)](https://jitpack.io/#corphish/Widgets)  
Collection of custom views or widgets that I use in my apps.
Could be useful for others too.

## Setup
- Add the jitpack repo in __root level__ `build.gradle` if not already.
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
- Add the library dependency in __app level__ `build.gradle`.
```
dependencies {
	compile 'com.github.corphish:widgets:1.1'
}
```

## Widgets present
- [KeyValueView](https://github.com/corphish/Widgets/blob/master/widgets/docs/KeyValueView.md)
- [PlaceholderView](https://github.com/corphish/Widgets/blob/master/widgets/docs/PlaceholderView.md)

## License
GPLv3
