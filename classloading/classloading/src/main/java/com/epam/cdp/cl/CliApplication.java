package com.epam.cdp.cl;

import java.util.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class CliApplication {

  static final Logger logger = Logger.getLogger(PluginLoader.class);
  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    PluginLoader pluginLoader = new PluginLoader();
    printPrompt();

    while (true) {
      String command = scanner.next().toLowerCase();
      switch (command) {
        case "all":
          pluginLoader.listLoadedPlugins();
          break;
        case "load1":
          pluginLoader.load(PluginLoader.PLUGIN1);
          break;
        case "load2":
          pluginLoader.load(PluginLoader.PLUGIN2);
          break;
        case "call1":
          pluginLoader.call(PluginLoader.PLUGIN1);
          break;
        case "call2":
          pluginLoader.call(PluginLoader.PLUGIN1);
          break;
        case "exit":
          return;
        default:
          print("Please, try again\n");
          break;
      }
    }
  }

  private static void print(String call2) {
    System.out.println(call2);
  }

  private static void printPrompt() {
    print("Menu: \n" +
        "All - show loaded modules\n" +
        "Load1 - load first plugin with given name\n" +
        "Load2 - load second plugin with given name\n" +
        "Call1 - call first's plugin functionality\n" +
        "Call2 - call second plugin functionality\n");
  }
}
