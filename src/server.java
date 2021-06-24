import java.io.*;
import java.nio.file.Files;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class server extends UnicastRemoteObject implements ServerInterface {
    protected server() throws RemoteException {
        super();
    }

    @Override
    public byte[] file(String fileName) throws RemoteException {
        File file = new File(fileName);
        byte[] fileContent = null;
        try {
            file.createNewFile();
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileContent;
    }
    public static void main(String[] args) {
        if (args[0].equals("server")) {
            String hostName = "localhost";
            String serviceName = "ServerInterface";

            try {
                server hello = new server();
                Registry reg = LocateRegistry.createRegistry(1224);

                reg.bind("ServerInterface", hello);

                System.out.println("ready");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String hostName = "localhost";
            String serviceName = "ServerInterface";

            try {
                Registry myreg = LocateRegistry.getRegistry(hostName, 1224);
                ServerInterface hello = (ServerInterface) myreg.lookup("ServerInterface");
                byte[] bytes = hello.file("rmi1.txt");
                File f = new File("rmi2.txt");
                f.createNewFile();
                OutputStream os
                        = new FileOutputStream(f);

                os.write(bytes);
                System.out.println("Successfully");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

