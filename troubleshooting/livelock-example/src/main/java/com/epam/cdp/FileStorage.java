package com.epam.cdp;

public class FileStorage {
  FileUploader currentUploader;
  synchronized void setCurrentUploader(FileUploader currentUploader) {
    this.currentUploader = currentUploader;
  }
}
