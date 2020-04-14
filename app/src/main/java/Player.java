public class Player {
    String nom;
    int argent;
    int [] main = new int [5];
    int mise;
    boolean continuer;
    boolean perdu;

    Player (String nom, int argentDepart, int [] main){
        this.nom = nom;
        argent = argentDepart;
        this.main = main;
        mise = 0;
        continuer = true;
        perdu = false;
    }

    public static int pointsMain(Player joueur){
        int somme = 0;
        for (int i = 0; i<5;i++){
            somme += BlackJack.PaquetCartes.valeurCarte(joueur.main[i]);
        }
        if(somme > 21){
            int nbAs = 0;
            for (int i = 0;i<5;i++){
                if(BlackJack.PaquetCartes.valeurCarte(joueur.main[i])==11){
                    nbAs ++;
                }
            }
            if(nbAs != 0){
                somme -=10*nbAs;
            }
        }
        return somme;
    }

    public static void afficherMain(Player joueur){
        for (int i = 0; i<5;i++){
            BlackJack.PaquetCartes.carte(joueur.main[i]);
        }
    }

    public static void toPrint(Player joueur){
        System.out.println(joueur.nom+" "+joueur.argent);
    }

    public static void reset (Player joueur){
        joueur.perdu = false;
        joueur.continuer = true;
        for(int i = 0;i<5;i++){
            joueur.main[i] = 0;
        }
    }

}
