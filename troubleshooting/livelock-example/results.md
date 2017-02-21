Run command below to build project

```sh
$ mvn clean install
```

Then run livelock example jar

```sh
$ java -jar -Xmx3m -XX:+HeapDumpOnOutOfMemoryError target/livalock-example-1.0-SNAPSHOT.jar
```

You'll see following output in console

```sh
changing current uploader to uploader-1
changing current uploader to uploader-2
changing current uploader to uploader-1
changing current uploader to uploader-2
changing current uploader to uploader-1
changing current uploader to uploader-2
changing current uploader to uploader-1
changing current uploader to uploader-2

...

Exception in thread "uploader-2"
Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "uploader-2"
Exception in thread "uploader-1" java.lang.OutOfMemoryError: Java heap space
```

So threads are doing useless transferring of control, uploading is never done. Livelock it is.

Let's check thread dump

```java
"uploader-2" #10 prio=5 os_prio=0 tid=0x00007fb700248000 nid=0x7580 in Object.wait() [0x00007fb6ed646000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at com.epam.cdp.FileUploader.upload(FileUploader.java:18)
	- locked <0x00000000ffc7aae0> (a com.epam.cdp.FileUploader)
	at com.epam.cdp.Application.lambda$main$1(Application.java:17)
	at com.epam.cdp.Application$$Lambda$2/1324119927.run(Unknown Source)
	at java.lang.Thread.run(Thread.java:745)

   Locked ownable synchronizers:
	- None

"uploader-1" #9 prio=5 os_prio=0 tid=0x00007fb700246800 nid=0x757f in Object.wait() [0x00007fb6ed747000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at com.epam.cdp.FileUploader.upload(FileUploader.java:18)
	- locked <0x00000000ffc7ab30> (a com.epam.cdp.FileUploader)
	at com.epam.cdp.Application.lambda$main$0(Application.java:16)
	at com.epam.cdp.Application$$Lambda$1/295530567.run(Unknown Source)
	at java.lang.Thread.run(Thread.java:745)

   Locked ownable synchronizers:
	- None
```

As we can see both are locked to make progress, waiting for each other to continue.
VisualVM screenshot is available in 'images' folder.

So now we have heap dump that was generated after OOME.

And then dump file can be analized using jhat utilite:
```sh
$ jhat <path_to_dump_file>
```

Let's use jvisualVm to analize heap dump. Threads are continiously logging their state to storage log queue during livelock.
I ideal case that should be logged two times since we have two threads. So logQueue is growing and causes OOME.
We can see it on screenshot of biggest objects
Screenshot: images/top_biggest_objects.png
