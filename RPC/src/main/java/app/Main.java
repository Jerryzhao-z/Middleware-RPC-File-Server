package app;

import org.apache.thrift.TException;
import java.lang.Exception;

public class Main {

  public static void main(String... args) throws Exception {
    try
    {
      if (args.length == 0) {
        kill();
      }
      switch (args[0]) {
        case "server":
          new Server().start();
          break;
        case "update":
          if (args.length != 3) {
            kill();
          }
          Client client_first = new Client();
          client_first.call(args[1]);
          client_first.updateFile(args[2]);
          break;
        case "delete":
          if (args.length != 3) {
            kill();
          }
          Client client_delete = new Client();
          client_delete.call(args[1]);
          client_delete.deleteFile(args[2]);
          break;
        case "get":
            if (args.length != 3) {
              kill();
            }
            Client client_get = new Client();
            client_get.call(args[1]);
            client_get.getFile(args[2]);
            break;
        case "client":
            if (args.length != 2) {
              kill();
            }
            Client client = new Client();
            client.call(args[1]);
            client.getListFile();
            break;
        default:
            break;
      }
    }catch(Exception e)
    {
      System.out.println(e.toString());
    }
  }

  private static void kill() {
    System.err.println("Server mode: server");
    System.err.println("Client mode: client hostname");
    System.exit(-1);
  }
}