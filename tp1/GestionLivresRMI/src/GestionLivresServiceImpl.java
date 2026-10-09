import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class GestionLivresServiceImpl extends UnicastRemoteObject implements GestionLivresService {
    private List<Livre> bibliotheque;

    public GestionLivresServiceImpl() throws RemoteException {
        super();
        bibliotheque = new ArrayList<>();
    }

    @Override
    public synchronized void ajouterLivre(Livre livre) throws RemoteException {
        bibliotheque.add(livre);
        System.out.println("Livre ajoute : " + livre.getTitre());
    }

    @Override
    public synchronized Livre rechercherLivre(String id) throws RemoteException {
        for (Livre l : bibliotheque) {
            if (l.getId().equalsIgnoreCase(id)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public List<Livre> listerLivres() throws RemoteException {
        return new ArrayList<>(bibliotheque);
    }
}