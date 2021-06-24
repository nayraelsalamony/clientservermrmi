import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public byte[] file(String fileName) throws RemoteException;
}
