package com.epam.cdp;

public class FileUploader {
  FileStorage storage;
  String name;
  boolean isActive;

  public FileUploader(FileStorage storage, String name, boolean isActive) {
    this.storage = storage;
    this.name = name;
    this.isActive = isActive;
  }

  public synchronized void upload(FileUploader otherUploader) {
    while (isActive) {
      if (storage.currentUploader != this) {
        try {
          wait(10);
        } catch (InterruptedException ignored) {}
      }
      if (otherUploader.isActive) {
        doMemoryConsumptionStuff(storage);
        System.out.println("changing current uploader to " + name);
        storage.setCurrentUploader(otherUploader);
        continue;
      }
      System.out.println(this.name + " is done");
      isActive = false;
    }
  }

  private void doMemoryConsumptionStuff(FileStorage storage) {
    storage.log(new String("changing current uploader to " + name));
  }
}
