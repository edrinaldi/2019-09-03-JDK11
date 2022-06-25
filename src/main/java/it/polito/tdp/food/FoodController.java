/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PorzioneConnessa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	
    	// controllo grafo
    	if(!this.model.isGrafoCreato()) {
    		this.txtResult.setText("Errore: devi prima creare il grafo.");
    		return;
    	}
    	
    	// controllo partenza
    	String partenza = this.boxPorzioni.getValue();
    	if(partenza == null) {
    		this.txtResult.setText("Errore: devi prima selezionare un tipo di porzione.");
    		return;
    	}
    	
    	// controllo N
    	int N = 0;
    	try {
    		N = Integer.parseInt(this.txtPassi.getText());
    	}
    	catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.setText("Errore: devi prima inserire un valore intero per i passi.");
    		return;
    	}
    	
    	// calcolo il cammino
    	this.model.calcolaCammino(N, partenza);
    	
    	// stampo il risultato
    	txtResult.setText(String.format("Trovato un cammino di peso %d\n", this.model.getPesoCammino()));
		for(PorzioneConnessa p : this.model.getCammino()) {
			this.txtResult.appendText("- " + p.getTipoPorzione() + "\n");
		}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	
    	// controllo grafo
    	if(!this.model.isGrafoCreato()) {
    		this.txtResult.setText("Errore: devi prima creare il grafo.");
    		return;
    	}
    	
    	// controllo partenza
    	String partenza = this.boxPorzioni.getValue();
    	if(partenza == null) {
    		this.txtResult.setText("Errore: devi prima selezionare un tipo di porzione.");
    		return;
    	}
    	
    	// trovo le porzioni correlate
    	List<PorzioneConnessa> correlate = this.model.trovaPorzioniConnesse(partenza);
    	
    	// stampo il risultato
    	txtResult.setText(String.format("Porzioni correlate a %s:\n", partenza));
    	for(PorzioneConnessa p : correlate) {
    		this.txtResult.appendText(p.toString() + "\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	// controllo le calorie
    	int calorie = 0;
    	try {
    		calorie = Integer.parseInt(this.txtCalorie.getText());
    	}
    	catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.setText("Errore: devi prima inserire un valore intero per le calorie.");
    		return;
    	}
    	
    	// creo il grafo
    	this.model.creaGrafo(calorie);
    	
    	// stampo il risultato
    	txtResult.setText(String.format("Creato grafo con %d vertici e %d archi", this.model.nVertici(), 
    			this.model.nArchi()));
    	
    	// riempio la tendina
    	this.boxPorzioni.getItems().clear();
    	this.boxPorzioni.getItems().addAll(this.model.getVertici());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
