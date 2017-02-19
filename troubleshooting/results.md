Run command below to build project

```sh
$ mvn clean install
```

Then run livelock example jar

```sh
$ java -jar target/livalock-example-1.0-SNAPSHOT.jar
```

You'll see following output in console

```sh
changing current uploader to uploader-1
wait...
changing current uploader to uploader-2
wait...
changing current uploader to uploader-1
wait...
changing current uploader to uploader-2
wait...
changing current uploader to uploader-1
wait...
changing current uploader to uploader-2
wait...
changing current uploader to uploader-1
wait...
changing current uploader to uploader-2
wait...
```

So threads are doing useless thransfering of control, uploading is never done. Livelock it is.

Let's check thread dump

```java
"uploader-2" #10 prio=5 os_prio=0 tid=0x00007fed6024a000 nid=0x2fa5 in Object.wait() [0x00007fed40a50000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at com.epam.cdp.FileUploader.upload(FileUploader.java:19)
	- locked <0x00000000ed0888f8> (a com.epam.cdp.FileUploader)
	at com.epam.cdp.Application.lambda$main$1(Application.java:17)
	at com.epam.cdp.Application$$Lambda$2/295530567.run(Unknown Source)
	at java.lang.Thread.run(Thread.java:745)

   Locked ownable synchronizers:
	- None

"uploader-1" #9 prio=5 os_prio=0 tid=0x00007fed60248800 nid=0x2fa4 in Object.wait() [0x00007fed40b51000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at com.epam.cdp.FileUploader.upload(FileUploader.java:19)
	- locked <0x00000000ed088910> (a com.epam.cdp.FileUploader)
	at com.epam.cdp.Application.lambda$main$0(Application.java:16)
	at com.epam.cdp.Application$$Lambda$1/1915318863.run(Unknown Source)
	at java.lang.Thread.run(Thread.java:745)

   Locked ownable synchronizers:
	- None
```

As we can see both are locked to make progress, waiting for each other to continue.
VisualVM screenshot is available in 'images' folder.


Also heap dump can be collected using jmap command like below:
```sh
$ jmap -dump:format=b,file=cheap.bin <pid>
```

And then dump file can be analized using jhat utilite:
```sh
$ jhat <path_to_dump_file>
```

Same things, of course can be done using visualVM.
