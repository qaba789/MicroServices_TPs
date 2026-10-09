import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            GestionLivresService service = (GestionLivresService) registry.lookup("GestionLivresService");

            Scanner scanner = new Scanner(System.in);
            int choix = 0;

            do {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Ajouter un livre");
                System.out.println("2. Rechercher un livre");
                System.out.println("3. Lister tous les livres");
                System.out.println("4. Quitter");
                System.out.print("Choisissez une option : ");

                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    choix = 0;
                }

                switch (choix) {
                    case 1:
                        System.out.print("Entrez l'ID du livre : ");
                        String id = scanner.nextLine();
                        System.out.print("Entrez le titre du livre : ");
                        String titre = scanner.nextLine();
                        System.out.print("Entrez l'auteur du livre : ");
                        String auteur = scanner.nextLine();

                        Livre nouveauLivre = new Livre(id, titre, auteur);
                        service.ajouterLivre(nouveauLivre);
                        System.out.println("Livre ajoute avec succes !");
                        break;

                    case 2:
                        System.out.print("Entrez l'ID du livre à rechercher : ");
                        String idRecherche = scanner.nextLine();
                        Livre livreTrouve = service.rechercherLivre(idRecherche);

                        if (livreTrouve != null) {
                            System.out.println("Livre trouve : " + livreTrouve);
                        } else {
                            System.out.println("Aucun livre trouve avec cet ID.");
                        }
                        break;

                    case 3:
                        List<Livre> livres = service.listerLivres();
                        if (livres.isEmpty()) {
                            System.out.println("La bibliothEque est vide.");
                        } else {
                            System.out.println("\nListe des livres :");
                            for (Livre l : livres) {
                                System.out.println(l);
                            }
                        }
                        break;

                    case 4:
                        System.out.println("Fin du programme.");
                        break;

                    default:
                        System.out.println("Option invalide, veuillez recharger.");
                }
            } while (choix != 4);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}