-injars 'C:\Users\Corsair\Documents\GitHub\edgeville-launcher-noframe\obfuscate\EdgevilleLauncherIn.jar'
-outjars 'C:\Users\Corsair\Documents\GitHub\edgeville-launcher-noframe\obfuscate\Edgeville.jar'

-libraryjars 'C:\Program Files (x86)\Java\jre1.8.0_91\lib\rt.jar'

-dontskipnonpubliclibraryclassmembers
-dontnote
-dontwarn

-verbose

-keep class launcher.Launcher {
    public static void main(...);
}
