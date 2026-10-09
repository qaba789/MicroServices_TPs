import java.io.Serializable;

public class Livre implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String titre;
    private String auteur;

    public Livre(String id, String titre, String auteur) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
    }

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    @Override
    public String toString() {
        return "Livre [ID=" + id + ", Titre=" + titre + ", Auteur=" + auteur + "]";
    }
}