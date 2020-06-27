/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenti;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	Integer anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.appendText("Devi selezionare un anno!\n");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	this.txtResult.appendText("Grafo creato! \n");
    	this.txtResult.appendText("# VERTICI: "+this.model.nVertici() +"\n");
    	this.txtResult.appendText("# ARCHI: "+this.model.nArchi()+"\n");
    	
    	
    	for(Integer i : model.vertici()) {
    		this.txtResult.appendText("Adiacenti al distretto "+i+"\n");
    		for(Adiacenti a : model.getAdiacenti(i)) {
    			txtResult.appendText(a.toString()+"\n");
    		}
    	}
    	
    	this.boxGiorno.getItems().addAll(this.model.getGiorni(anno));
    	this.boxMese.getItems().addAll(this.model.getMesi(anno));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String n = this.txtN.getText();
    	int agenti = 0;
    	try {
    		agenti = Integer.parseInt(n);
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero intero!\n");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(this.model.getAnni());
    }
}
