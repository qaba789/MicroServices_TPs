import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GestionLivresService extends Remote {
    void ajouterLivre(Livre livre) throws RemoteException;
    Livre rechercherLivre(String id) throws RemoteException;
    List<Livre> listerLivres() throws RemoteException;
}