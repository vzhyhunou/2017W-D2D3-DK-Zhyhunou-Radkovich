package com.epam.cdp.cl;

import org.apache.log4j.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.jar.*;

public class PluginLoader extends ClassLoader {
  static final Logger logger = Logger.getLogger(PluginLoader.class);

  public static final String PLUGIN1 = "plugin1";
  public static final String PLUGIN2 = "plugin2";
  private static final String M2_PATH = System.getProperty("user.home") + "/.m2/repository/";
  private static final String METHOD_NAME = "doWork";

  private Map<String, Class<?>> plugins = new HashMap<>();
  private Map<String, String> paths = new HashMap<>();
  private Map<String, String> packages = new HashMap<>();

  public PluginLoader() {
    paths.put(PLUGIN1, "com/epam/cdp/plugin-1/1.0-SNAPSHOT/plugin-1-1.0-SNAPSHOT.jar");
    paths.put(PLUGIN2, "com/epam/cdp/plugin-2/1.0-SNAPSHOT/plugin-2-1.0-SNAPSHOT.jar");

    packages.put(PLUGIN1, "com.epam.cdp.cl.plugin1.Plugin1");
    packages.put(PLUGIN2, "com.epam.cdp.cl.plugin2.Plugin2");
  }

  public Class<?> load(String pluginName) {
    Class<?> pluginClass = plugins.get(pluginName);

    if (pluginClass == null) {
      try {
        JarFile jarFile = new JarFile(M2_PATH + paths.get(pluginName));
        JarEntry jarEntry = jarFile.getJarEntry(packages.get(pluginName).replace(".","/") + ".class");
        byte[] classData = loadClassData(jarFile, jarEntry);

        if (classData != null) {
          Class<?> clazz = defineClass(packages.get(pluginName), classData, 0, classData.length);
          plugins.put(pluginName, clazz);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return pluginClass;
  }

  public void call(String pluginName) {
    Class<?> aClass = plugins.get(pluginName);
    if (aClass != null) {
      try {
        String result = (String) aClass.getMethod(METHOD_NAME).invoke(null);
        print(result);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        e.printStackTrace();
      }
    } else {
      print("Could not invoke because plugin was not loaded");
    }
  }

  private byte[] loadClassData(JarFile jarFile, JarEntry jarEntry) throws IOException {

    long size = jarEntry.getSize();
    if (size == -1 || size == 0)
      return null;

    byte[] data = new byte[(int) size];
    InputStream in = jarFile.getInputStream(jarEntry);
    in.read(data);

    return data;
  }

  public void listLoadedPlugins() {
    if (plugins.entrySet().isEmpty())
      print("No classes loaded");
    print("Loaded classes are");
    for (Map.Entry<String, Class<?>> entry : plugins.entrySet()) {
      print("  - " + entry.getKey() + " : [" + entry.getValue().getName() + "]");
    }
  }

  public static void print(String message) {
    logger.info(message);
  }
}
