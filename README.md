## Indicator SeekBar version [![](https://jitpack.io/v/EsmaeelNabil/EsIndicatorSeekBar.svg)](https://jitpack.io/#EsmaeelNabil/EsIndicatorSeekBar)


### this library contains two customizable  views.

```gradle
 EsSeekBar
 EsRangeBar
```

![](static/seekbar.png)
![](static/rangbar.png)





###### in project --> build.gradle

```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }

    }
```
###### in app --> build.gradle file :

```gradle

android {
...
  compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
...
}

 dependencies {

	        implementation 'com.github.EsmaeelNabil:EsIndicatorSeekBar:0.2.4'

 }
```

XML Example
--------------


```xml

<com.esmaeel.indicatorseekbar.EsSeekBar
			android:id="@+id/_seekbar"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"/>

```



```xml

<com.esmaeel.rangeindicatorseekbar.EsRangeBar
			android:id="@+id/_rangebar"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"/>

```

Kotlin Example
--------------

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Simple EsSeekBar
        _seekbar.doTheMagicIn(this,::seekBarListener)
	
	//Simple EsRangeBar
	_rangebar.doTheMagicIn(this,::rangeBarListener)
    
    } 
```

OnChangeListeners
-----------------
#### First we need this :
* **write** a function for the `EsSeekBar` that takes an `Int` . 
* **path it** like this `doTheMagicIn(this,::seekBarListener)` .
* **here it is** ` Your Listener is up and ready!` . 
* **now i guess you know how to make one for `EsRangeBar` Yes? , Pravo :)**

```kotlin
    private fun seekBarListener(progress: Int) {    
	textView.text = " progress =  $progress "
    }
    
    private fun rangeBarListener(startRange: Int, endRange: Int) {
        textView.text = " start =  $startRange end =  $endRange"
    }
```

#### Custom EsSeekBar with default indicator background "BLACK"

```kotlin
        _seekbar.doTheMagicIn(this,::seekBarListener
            ,indicatorPrefix = "KM"
            ,prefixPositionStart = false)
```


#### Custom EsSeekBar

```java
        _seekbar.doTheMagicIn(this,::seekBarListener
            ,indicatorBackground = EsSeekBar.WHITE
            ,indicatorPrefix = "$"
            ,prefixPositionStart = true)
```


#### Custom EsSeekBar with custom `layout` resource file 
#### but it must have a `TextView` with `id`=`progress_text` 

```kotlin
        _seekbar.doTheMagicIn(this,::rangeBarListener
            ,indicatorBackground = EsSeekBar.CUSTOM_LAYOUT
            ,indicatorLayoutResource = R.layout.indicator_black
            ,indicatorPrefix = "KM"
            ,prefixPositionStart = false)
```


#### rangeBar with indicator default white background

```kotlin
        _rangebar.doTheMagicIn(this,::rangeBarListener
            ,indicatorBackground = EsSeekBar.WHITE
            ,indicatorPrefix = "KM"
            ,prefixPositionStart = false)
```


#### Custom rangeBar with indicator custom layout 

```kotlin
        _rangebar.doTheMagicIn(this,::rangeBarListener
            ,indicatorBackground = EsSeekBar.CUSTOM_LAYOUT
            ,indicatorLayoutResource = R.layout.indicator_black
            ,indicatorPrefix = "KM"
            ,prefixPositionStart = false)
```
	

Explaning Artical
-----------------

https://www.linkedin.com/pulse/indicator-seekbar-android-kotlin-esmaeel-napil/

Thanks to 
--------
https://github.com/Jay-Goo/RangeSeekBar @Jay-Goo

