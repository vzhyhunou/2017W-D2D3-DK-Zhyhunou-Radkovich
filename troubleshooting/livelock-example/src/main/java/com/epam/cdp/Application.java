package com.epam.cdp;

public class Application {
  public static void main(String[] args) {
    FileStorage storage = new FileStorage();

    final FileUploader fileUploader1 = new FileUploader(storage, "uploader-1", true);
    final FileUploader fileUploader2 = new FileUploader(storage, "uploader-2", true);

    storage.currentUploader = fileUploader1;

    new Thread(() -> fileUploader1.upload(fileUploader2)).start();
    new Thread(() -> fileUploader2.upload(fileUploader1)).start();
  }
}
