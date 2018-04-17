# FirebaseAuthActivity

FirebaseAuthActivity includes necessary FirebaseAuth functions such as sign in and sign out for ease of use.

Description
-----------

The main functions are to sign in / sign up with Email and Password 
or sign in with Google account (with GoogleApiClient) 
and using FirebaseDatabase to store signed-in accounts.

Instructions
------------

To use FirebaseAuth service, you need to visit [Firebase console][1]
and add a project with same package name as your Android project 
package name (Example: com.google.firebase).

Then it will let you download a google-services.json file.
Download and place it in app folder.

Dependencies
-----------------

In build.gradle of project

```groovy
classpath 'com.google.gms:google-services:3.0.0'
```

In build.gradle of app

```groovy
compile 'com.google.firebase:firebase-database:10.0.1'
compile 'com.google.firebase:firebase-auth:10.0.1'
compile 'com.google.android.gms:play-services-auth:10.0.1'
```

and at the end

```groovy
apply plugin: 'com.google.gms.google-services'
```

Check them in build.gradle and app/build.gradle

[1]: https://console.firebase.google.com/
