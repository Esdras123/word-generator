/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package word.generator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author ESDRAS
 */
public class WordGeneratorController implements Initializable {
    private Generateur generateur=new Generateur();
    @FXML
    private TextArea textarea;
    @FXML
    private Button genererButton;
    @FXML
    private Button viderButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateur.getTraiterDico().lire();
    }    

    @FXML
    private void generer(ActionEvent event) {
        String mot=generateur.formerMot();
        textarea.setText(textarea.getText()+"\n"+mot);
    }

    @FXML
    private void vider(ActionEvent event) {
        textarea.clear();
    }
    
}
