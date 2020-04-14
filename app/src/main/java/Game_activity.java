public class Game_activity {
    public static int distribuerCarte (Joueur joueur,int[] paquet,int currentCard ){
        System.out.println(currentCard);
        int newCard = currentCard ;
        for(int i = 0; i < joueur.main.length;i++){
            if (joueur.main[i] == 0){
                joueur.main[i]=paquet[newCard];
                System.out.println(joueur.main[i]);
                newCard ++;
                break;
            }
        }
        return newCard;
    }

    public static void mise (Joueur joueur){
        String tempMise = JOptionPane.showInputDialog("Combien le joueueur "+joueur.nom+" veut-il miser ?");
        int mise = Integer.parseInt(tempMise);
        joueur.argent -= mise;
        joueur.mise = mise;
        System.out.println("Vous avez misé "+mise);
    }

    public static void menu (Joueur joueur,Joueur croupier,int[] paquet, int currentCard){
        Object[] selectionValues = { "Tirer une carte", "Rester", "Doubler la mise","Partager","S'assurer","Voir les cartes du croupier" };
        String initialSelection = "Tirer une carte";
        Object selection = JOptionPane.showInputDialog(null, "Que desirez-vous faire ?",
                "", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        if(selection == "Tirer une carte"){
            currentCard = autreCarte(joueur,paquet,currentCard);
        }
        if(selection == "Rester"){
            joueur.continuer=false;
        }
        if(selection == "Doubler la mise"){
            JFrame parent = new JFrame();
            int nbCartes = 0;
            for(int i = 0; i<5;i++){
                if(joueur.main[i] !=0 ){
                    nbCartes ++;
                }
            }
            if(nbCartes==2){
                joueur.mise *= 2;
                JOptionPane.showMessageDialog(parent, "Votre mise est maintenant de "+joueur.mise);
                currentCard=autreCarte(joueur,paquet,currentCard);
            }
            if(nbCartes>2){
                JOptionPane.showMessageDialog(parent, "Vous ne pouvez pas doubler votre mise");
                menu(joueur,croupier,paquet,currentCard);
            }
        }
        if(selection == "Partager"){
            // split la main
        }
        if(selection == "S'assurer"){
            int nbCartes = 0;
            for(int i = 0; i<5;i++){ // on compte le nombre de carte du croupier
                if(joueur.main[i] !=0 ){
                    nbCartes ++;
                }
            }
            if(BlackJack.PaquetCartes.valeurCarte(croupier.main[0]) == 11 && nbCartes == 1){
                /*joueur.mise /= 2;*/ //faire l'assurance
            }
        }
        if(selection == "Voir les cartes du croupier"){
            System.out.println("Cartes du croupier : ");
            BlackJack.Joueur.afficherMain(croupier);
            System.out.println("Valeur des cartes du croupier : " + BlackJack.Joueur.pointsMain(croupier));
        }
    }

    public static int autreCarte(Joueur joueur,int[] paquet,int currentCard){
        int carte = currentCard;
        carte = distribuerCarte(joueur,paquet,carte);
        System.out.println("Vous avez pioché ");
        BlackJack.PaquetCartes.carte(paquet[carte-1]);
        System.out.println("Votre main vaux : "+BlackJack.Joueur.pointsMain(joueur));
      /*if(BlackJack.Joueur.pointsMain(joueur) > 21 ){
        joueur.perdu = true ;
        return carte ;
      }*/
        return carte;
    }

    public static int suitePartie(Joueur croupier,int[] paquet, int currentCard){ // la suite du jeu pour le croupier
        int carte = currentCard;
        while(BlackJack.Joueur.pointsMain(croupier) <17){ // le croupier tire des cartes tant qu'il ne dépasse pas 16
            carte = BlackJack.jeu.distribuerCarte(croupier,paquet,carte);
            System.out.println("le croupier a tiré  ");
            BlackJack.PaquetCartes.carte(paquet[carte-1]);
            System.out.println("Sa main vaux : "+BlackJack.Joueur.pointsMain(croupier));
            if(BlackJack.Joueur.pointsMain(croupier)>21){ // le croupier a dépassé 21, il a donc perdu
                croupier.perdu = true;
                return carte;
            }
        }
        croupier.continuer = false; // le croupier ne peu peut piocher de carte (il a dépassé 16)
        return carte;
    }

    public static void gagnant(Joueur joueur, Joueur croupier){
        JFrame parent = new JFrame();

        if(joueur.continuer == true && joueur.perdu == false){ // le joueur n'a pas perdu et veux continuer
            if(BlackJack.Joueur.pointsMain(joueur) > 21){ // on verifie si il a perdu
                joueur.perdu = true;
                joueur.continuer = false;
                JOptionPane.showMessageDialog(parent, "Vous avez perdus");
                joueur.argent -= joueur.mise;
                JOptionPane.showMessageDialog(parent, "Votre perte est de "+joueur.mise+" et vous possedez "+joueur.argent);
            }
            if(BlackJack.Joueur.pointsMain(joueur)<=21){
                // on ne fait rien, le joueur a le droit et l'envie de continuer a piocher
            }
        }
        if(croupier.perdu == true && joueur.perdu == false){ // le joueur n'a pas perdu mais le croupier oui
            JOptionPane.showMessageDialog(parent, "Vous avez gagner");
            joueur.argent += (joueur.mise*2);
            JOptionPane.showMessageDialog(parent, "Votre gain est de "+(joueur.mise*2)+" et vous possedez "+joueur.argent);
        }
        if(joueur.continuer == false && joueur.perdu == false){ // le joueur n'a pas encore perdu mais a décidé de s'arreter

            if(BlackJack.Joueur.pointsMain(joueur) <= 21 && BlackJack.Joueur.pointsMain(joueur) < BlackJack.Joueur.pointsMain(croupier)){ // le joueur a une main plus faible que le coupier il a donc perdu
                joueur.perdu = true;
                joueur.continuer = false;
                JOptionPane.showMessageDialog(parent, "Vous avez perdus");
                joueur.argent -= joueur.mise;
                JOptionPane.showMessageDialog(parent, "Votre perte est de "+joueur.mise+" et vous possedez "+joueur.argent);
            }

            if(BlackJack.Joueur.pointsMain(joueur) <= 21 && BlackJack.Joueur.pointsMain(joueur) == BlackJack.Joueur.pointsMain(croupier)){ // le joueur et le croupier ont la même main (en terme de points)
                joueur.perdu = true;
                joueur.continuer = false;
                JOptionPane.showMessageDialog(parent, "Vous avez égalisé avec le croupier");
                joueur.argent += joueur.mise;
                JOptionPane.showMessageDialog(parent, "Votre gain est de "+joueur.mise+" et vous possedez "+joueur.argent);
            }
            if(BlackJack.Joueur.pointsMain(joueur) <= 21 && BlackJack.Joueur.pointsMain(joueur) > BlackJack.Joueur.pointsMain(croupier)){ // le joueur a battu le croupier
                JOptionPane.showMessageDialog(parent, "Vous avez gagner");
                joueur.argent += (joueur.mise*2);
                JOptionPane.showMessageDialog(parent, "Votre gain est de "+(joueur.mise*2)+" et vous possedez "+joueur.argent);
            }
        }
    }
