/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package word.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ESDRAS
 */
public class TraiterDico {
    private double [] distributionIni= new double[27];
    private double [][] matriceTransition= new double[27][27];
    public static BufferedReader fis= null;
    public String nomFichier;
    private int nbMots=0;
    private double[] nbOccurencesLettres= new double[26];
    public TraiterDico(String nomFichier){
        this.nomFichier=nomFichier;
        for(int i=0;i<distributionIni.length;i++){
            distributionIni[i]=0;
            if(i<distributionIni.length-1)
                nbOccurencesLettres[i]=0;
            for(int j=0;j<distributionIni.length;j++){
                matriceTransition[i][j]=0;
            }
        }
        matriceTransition[26][26]=1;
    }
    public void lire(){
        try{
            Scanner scanner=new Scanner(new File(nomFichier));
            String line=new String();
            while(scanner.hasNextLine()){
                line=scanner.nextLine();
                System.out.println(line);
                analyserPhrase(line);
            }
            System.out.println(nbMots);
            if(nbMots>0){
                initialiserDistri();
                initialiserMatrice();
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        /*BufferedReader fis;
        try {
            fis = new BufferedReader(new FileReader(nomFichier));
            String currentLine;
            while ((currentLine = fis.readLine()) != null) {
                System.out.println(currentLine);
                actualiserPremiereLettre(currentLine);
                analyserPhrase(currentLine);
            }
            initialiserDistri();
            initialiserMatrice();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TraiterDico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TraiterDico.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    public void actualiserPremiereLettre(String currentLine){
        char lettre=currentLine.charAt(0);
        lettre=mettreMinuscule(lettre);
        distributionIni[lettre-97]++;
    }
    public void initialiserDistri(){
        System.out.println("Distribution :");
        double somme=0;
        for(int i=0;i<distributionIni.length;i++){
            distributionIni[i]=distributionIni[i]/nbMots;
            somme+=distributionIni[i];
            if(i<26)
                System.out.println((char)(97+i)+" "+ distributionIni[i]);
        }
        System.out.println("Somme distribution "+ somme);
    }
    public void initialiserMatrice(){
        double somme=0;
        DecimalFormat df = new DecimalFormat ( ) ;
        df.setMaximumFractionDigits(3) ; //arrondi à 2 chiffres apres la virgules
        sommerOccur();
        for(int i=0;i<distributionIni.length-1;i++){
            if(i<26){
                System.out.print("Probabilites d'apparition apres "+(char)(97+i)+": ");
            }
            somme=0;
            for(int j=0;j<distributionIni.length;j++){
                matriceTransition[i][j]=matriceTransition[i][j]/nbOccurencesLettres[i];
                somme+=matriceTransition[i][j];
                if(i<26){
                    System.out.print("---"+(char)(97+j)+" "+(df.format(matriceTransition[i][j])));
                }
                else{
                    System.out.println("le mot se termine "+matriceTransition[i][j]);
                }
            }
            System.out.println("Somme de proba de "+(char)(97+i)+": "+somme);
        }
    }
    public void sommerOccur(){
        for(int i=0;i<distributionIni.length-1;i++){
            for(int j=0;j<distributionIni.length;j++){
                nbOccurencesLettres[i]+=matriceTransition[i][j];
            }
        }
    }
    public void analyserPhrase(String currentLine){
        int i=0;
        boolean bool=true;
        char lettre1,lettre2;
        for(i=0;i<currentLine.length()-1;i++){
            lettre1=mettreMinuscule(currentLine.charAt(i));
            lettre2=mettreMinuscule(currentLine.charAt(i+1));
            if(lettre1-97<26 && lettre1-97>=0 && lettre2-97<26 && lettre2-97>=0){
                matriceTransition[lettre1-97][lettre2-97]++;
            }
            else{
                bool=false;
                break;
            }
        }
        if(bool){
            lettre1=mettreMinuscule(currentLine.charAt(currentLine.length()-1));
            matriceTransition[lettre1-97][26]++; 
            nbMots++;
            actualiserPremiereLettre(currentLine);
        }
    }

    private char mettreMinuscule(char lettre) {
        if(!java.lang.Character.isLowerCase(lettre))// juste pour montrer que ça existe
            return java.lang.Character.toLowerCase(lettre);
        else{
            return lettre;
        }
    }

    public double[] getDistributionIni() {
        return distributionIni;
    }

    public double[][] getMatriceTransition() {
        return matriceTransition;
    }
    

}
