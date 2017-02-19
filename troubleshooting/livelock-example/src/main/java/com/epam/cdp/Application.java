package com.epam.cdp;

public class Application {

  public static final String THREAD_NAME_1 = "uploader-1";
  public static final String THREAD_NAME_2 = "uploader-2";

  public static void main(String[] args) {
    FileStorage storage = new FileStorage();

    final FileUploader fileUploader1 = new FileUploader(storage, THREAD_NAME_1, true);
    final FileUploader fileUploader2 = new FileUploader(storage, THREAD_NAME_2, true);

    storage.currentUploader = fileUploader1;

    new Thread(() -> fileUploader1.upload(fileUploader2), THREAD_NAME_1).start();
    new Thread(() -> fileUploader2.upload(fileUploader1), THREAD_NAME_2).start();
  }
}
