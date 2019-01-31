/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package word.generator;

import java.util.Random;

/**
 *
 * @author ESDRAS
 */
public class Generateur {
    private TraiterDico traiterDico=new TraiterDico("./src/liste_anglais.txt");
    
    public Generateur() {
    }

    private int choisirLettre(double[] distribution,Random r){
        double rand=r.nextDouble();
        double som=0;
        int i=0;
        for(i=0;i<distribution.length;i++){
            if(rand>=som && rand<som+distribution[i]){
                return i;
            }
            else
                som=som+distribution[i];
        }
        return i;
    }
    public String formerMot(){
        Random r= new Random();
        String mot=new String();
        int nb=choisirLettre(traiterDico.getDistributionIni(),r);
        while(nb<26){
            mot=mot+(char)(97+nb);
            nb=choisirLettre(traiterDico.getMatriceTransition()[nb],r);
        }
        return mot;
    }

    public TraiterDico getTraiterDico() {
        return traiterDico;
    }

    public void setTraiterDico(TraiterDico traiterDico) {
        this.traiterDico = traiterDico;
    }
    
}
