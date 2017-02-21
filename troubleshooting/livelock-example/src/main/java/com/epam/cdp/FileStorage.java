package com.epam.cdp;

import java.util.*;

public class FileStorage {
  FileUploader currentUploader;
  Queue<String> logQueue = new LinkedList<>();

  synchronized void setCurrentUploader(FileUploader currentUploader) {
    this.currentUploader = currentUploader;
  }
  synchronized void log(String message) {
    logQueue.add(message);
  }
}
