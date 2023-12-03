package e3;
//--module-path /home/iutinfo/ExternalJars/javafx-sdk-11.0.2/lib/ --add-modules javafx.controls,javafx.fxml

import javafx.stage.FileChooser;


//import fr.ulille.but.sae2_02.graphes.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Slider;

public class InterfaceV2 extends Application {
	
	//Initialisation des objets
	static String csvFilePath;//=chooseCSVFile();
    static ArrayList<Teenager> teenagers;// = Platform.CSVtoTEENAGERS(csvFilePath);
	static ArrayList<String> teenagersNames;// = Platform.teenagersToString(teenagers);
	ObservableList<String> items;// = FXCollections.observableArrayList(teenagersNames);
	 
	static ArrayList<Teenager> partie1;
	static ArrayList<Teenager> partie2;
	static ArrayList<Teenager> misDeCotes;
	static GrapheNonOrienteValue<Teenager> graphe;
	static CalculAffectation<Teenager> calcul;
	static List<Arete<Teenager>> appariements;
	static List<String> appariementsNames;
	static ArrayList<Arete<Teenager>> aforcer=new ArrayList<Arete<Teenager>>();
	static ArrayList<Arete<Teenager>> aempecher=new ArrayList<Arete<Teenager>>();
	static ArrayList<String> listePays;
	
	static Teenager t1EoF;
  	static Teenager t2EoF;
	
  	//Initialisation des nodes
	//Création scene intro
	Label labelIntro = new Label ("Veuillez importer un fichier CSV pour commencer.");
	Button importIntro = new Button ("Importer");
	
	
	//Création empecher/forcer
	Label labelEmpecherOuForcer= new Label ("Veuillez sélectionner un binôme d'étudiant.");
	ListView<String> listEoF;
	TextArea infoEoF;
	TextField searchBarEoF = new TextField();
	Button searchButtonEoF = new Button("Rechercher");
    Button retourEoF= new Button("Retour");
    Button renitialiserEoF= new Button("Rénitialiser");
    Button forcerEoF= new Button("Forcer cette affectation");
    Button empecherEoF= new Button("Empecher cette affectation");
    Button supprimerEoF= new Button("Supprimer les modifications");
	
	
	//Création des boutons scene etudiants
	ListView<String> listEtu;
	TextArea infoEtu;
	TextField searchBar = new TextField();
	Button searchButton = new Button("Rechercher");
    Button lancement = new Button("Lancer les appariements");
    
  //Création des boutons scene appariements
  	ListView<String> listAppa;
  	TextArea infoAppa;
  	TextField searchBarAppa = new TextField();
  	Button searchButtonAppa = new Button("Rechercher");
  	Button annuler = new Button("Annuler");
  	Button desistement = new Button("Signaler un désistement de");
  	ChoiceBox<String> choixDesistement = new ChoiceBox<String>();
    
	
	//Création du menu
    Menu fileMenu = new Menu("Options");
    MenuItem importItem = new MenuItem("Importer un CSV");
    MenuItem eofItem = new MenuItem("Forcer/Empêcher une affectation");
    MenuItem reglageItem = new MenuItem("Réglages"); //Pondérations et PAYS
    MenuBar menuBar = new MenuBar();
    
    //Initialisation de paramètres de réglages
    static String countryHost;
    static String countryGuest;
    
    static Arete<Teenager> selectedBinome;
    
    double coefAllergies=1;
	double coefDiet=1;
	double coefAge=1;
	double coefHistory=1;
	double coefGender=1;
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	
    	// Créer le menu
        fileMenu.getItems().addAll(importItem, eofItem, reglageItem);
        menuBar.getMenus().add(fileMenu);
        
        // Création des éléments de l'interface
        listEtu = new ListView<>();
        listEtu.setMinWidth(400);
        infoEtu = new TextArea();
        infoEtu.setEditable(false);
        
        listAppa = new ListView<>();
        listAppa.setMinWidth(400);
        infoAppa = new TextArea();
        infoAppa.setEditable(false);
        
        listEoF = new ListView<>();
        listEoF.setMinWidth(400);
        infoEoF = new TextArea();
        infoEoF.setEditable(false);
    	
    	// Création du conteneur principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setLeft(listEtu);
        root.setCenter(infoEtu);
        root.setTop(createTopBar());
        root.setBottom(createSearchBar());
        
        BorderPane introRoot = new BorderPane();
        introRoot.setPadding(new Insets(10));
        introRoot.setTop(labelIntro);
        introRoot.setCenter(importIntro);
        
        BorderPane rootAppariements = new BorderPane();
        rootAppariements.setPadding(new Insets(10));
        rootAppariements.setLeft(listAppa);
        rootAppariements.setCenter(infoAppa);
        rootAppariements.setTop(createTopBarAppa());
        rootAppariements.setBottom(createSearchBarAppa());
        
        BorderPane rootEoF = new BorderPane();
        rootEoF.setPadding(new Insets(10));
        rootEoF.setLeft(listEoF);
        rootEoF.setCenter(infoEoF);
        rootEoF.setTop(createTopBarEoF());
        rootEoF.setBottom(createSearchBarEoF());
        
        BorderPane rootReglages = new BorderPane();
        rootReglages.setPadding(new Insets(10));
        Label labelCoefAllergies = new Label("Sélectioner une pondération pour l'attribut Allergie :");
        Label labelCoefDiet = new Label("Sélectioner une pondération pour l'attribut Régimes :");
        Label labelCoefAge = new Label("Sélectioner une pondération pour l'attribut Age :");
        Label labelCoefHistory = new Label("Sélectioner une pondération pour l'attribut Historique:");
        Label labelCoefGender = new Label("Sélectioner une pondération pour l'attribut Genre :");
        Slider sliderAllergies = new Slider();
        sliderAllergies.setMin(0);
        sliderAllergies.setMax(3);
        sliderAllergies.setValue(1);
        sliderAllergies.setBlockIncrement(1);
        sliderAllergies.setShowTickLabels(true);
        sliderAllergies.setShowTickMarks(true);
        Slider sliderDiet = new Slider();
        sliderDiet.setMin(0);
        sliderDiet.setMax(3);
        sliderDiet.setValue(1);
        sliderDiet.setBlockIncrement(1);
        sliderDiet.setShowTickLabels(true);
        sliderDiet.setShowTickMarks(true);
        Slider sliderAge = new Slider();
        sliderAge.setMin(0);
        sliderAge.setMax(3);
        sliderAge.setValue(1);
        sliderAge.setBlockIncrement(1);
        sliderAge.setShowTickLabels(true);
        sliderAge.setShowTickMarks(true);
        Slider sliderHistory = new Slider();
        sliderHistory.setMin(0);
        sliderHistory.setMax(3);
        sliderHistory.setValue(1);
        sliderHistory.setBlockIncrement(1);
        sliderHistory.setShowTickLabels(true);
        sliderHistory.setShowTickMarks(true);
        Slider sliderGender = new Slider();
        sliderGender.setMin(0);
        sliderGender.setMax(3);
        sliderGender.setValue(1);
        sliderGender.setBlockIncrement(1);
        sliderGender.setShowTickLabels(true);
        sliderGender.setShowTickMarks(true);
        Button retour = new Button("Retour");
        Label messagePays = new Label("");
        Label choixPaysHote = new Label("Choissisez le pays hôte:");
        ChoiceBox<String> choiceCountryHost = new ChoiceBox<>();
        Label choixPaysGuest = new Label("Choissisez le pays hôte:");
        ChoiceBox<String> choiceCountryGuest = new ChoiceBox<>();
        VBox sliders = new VBox(10);
        sliders.setPadding(new Insets(10));
      	sliders.getChildren().addAll(labelCoefAllergies,sliderAllergies,labelCoefDiet, sliderDiet,labelCoefAge,sliderAge,labelCoefHistory,sliderHistory,labelCoefGender,sliderGender,choixPaysHote,choiceCountryHost,choixPaysGuest,choiceCountryGuest,messagePays);
      	
      	rootReglages.setCenter(sliders);
      	rootReglages.setBottom(retour);
        
        // Création de la scène
        Scene sceneIntro = new Scene(introRoot, 650, 350);
        Scene scenePrincipale = new Scene(root, 650, 350);
        Scene sceneAppariements = new Scene(rootAppariements, 650, 350);
        Scene sceneReglages = new Scene(rootReglages, 850, 650);
        Scene sceneEoF = new Scene(rootEoF, 650, 350);
        
        choiceCountryHost.setOnAction(e -> {
            String selectedOption = choiceCountryHost.getValue();
            if(selectedOption!=null) {
            	if(!selectedOption.equals(countryGuest)) {
                	countryHost=selectedOption;
                	messagePays.setText("Sélection valide");
            	}else {
            		messagePays.setText("Cela ne doit pas être dans le même pays.");
            	}
            }
        });
        
        choiceCountryGuest.setOnAction(e -> {
            String selectedOption = choiceCountryGuest.getValue();
            if(selectedOption!=null) {
            	if(!selectedOption.equals(countryHost)) {
                	countryGuest=selectedOption;
                	messagePays.setText("Sélection valide");
            	}else {
            		messagePays.setText("Cela ne doit pas être dans le même pays.");
            	}
            }
            
        });
        
        
        searchButton.setOnAction(event -> {
        	search(event);
        	infoEtu.clear();
        });
        
        searchButtonAppa.setOnAction(event -> {
        	searchAppa(event);
        	infoAppa.clear();
        });
        
        searchButtonEoF.setOnAction(event -> {
        	searchEoF(event);
        });
        
        lancement.setOnAction(event -> {
        	//System.out.println("TESTeventLancement"); //VALIDE
        	ArrayList<ArrayList<Teenager>> prep = InterfaceV2.preparationGraphe(teenagers,listePays,countryHost,countryGuest);
	    	partie1 = prep.get(0);
	    	partie2 = prep.get(1);
	    	misDeCotes = prep.get(2);
	    	teenagers.clear();
	    	teenagers.addAll(partie1);
	    	teenagers.addAll(partie2);
			graphe = AffectationUtil.initGraph(teenagers,"historiqueInterfaceFX",sliderAllergies.getValue(),sliderDiet.getValue(),sliderAge.getValue(),sliderHistory.getValue(),sliderGender.getValue(),aforcer, aempecher); //Rajouter les sliders
	    	calcul = new CalculAffectation<Teenager>(graphe,partie1,partie2);
	    	appariements = calcul.calculerAffectation();
	    	appariementsNames = Platform.teenagersToString(appariements);
	    	appariementsNames.add("---Etudiants rejeté---");
	    	appariementsNames.addAll(Platform.teenagersToString(misDeCotes));
	    	items = FXCollections.observableArrayList(appariementsNames);
	    	listAppa.setItems(items);
        	primaryStage.setScene(sceneAppariements);
        });
        
        annuler.setOnAction(nullevent -> {
        	teenagers = Platform.CSVtoTEENAGERS(csvFilePath);
        	teenagersNames = Platform.teenagersToString(teenagers);
        	items = FXCollections.observableArrayList(teenagersNames);
        	listEtu.setItems(items);
        	primaryStage.setScene(scenePrincipale);
        });
        retour.setOnAction(nullevent -> {
        	primaryStage.setScene(scenePrincipale);
        });
        renitialiserEoF.setOnAction(nullevent -> {
        	t1EoF=null;
        	t2EoF=null;
        	infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->");
        });
        supprimerEoF.setOnAction(nullevent -> {
        	aforcer.clear();
        	aempecher.clear();
        	t1EoF=null;
        	t2EoF=null;
        	infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->");
        });
        forcerEoF.setOnAction(nullevent -> {
        	if(t1EoF!=null&&t2EoF!=null) {
        		Arete<Teenager> temp = new Arete<Teenager>(t1EoF,t2EoF);
        		if(Platform.combinaisonValide(temp,aforcer)) {
        			aforcer.add(temp);
        			t1EoF=null;
                	t2EoF=null;
                	infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->");
        		}else {
        			t1EoF=null;
                	t2EoF=null;
        			infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->\n\nSaisie incorrecte !\n Remarque:\nVous ne pouvez pas saisir deux fois le même adolescent.");
            	}
        		
        	}else {
        		t1EoF=null;
            	t2EoF=null;
    			infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->\n\nSaisie invalide ! \n Remarque: \nVous ne pouvez pas saisir deux fois le même adolescent.");
        	}
        });
        empecherEoF.setOnAction(nullevent -> {
        	if(t1EoF!=null&&t2EoF!=null) {
        		Arete<Teenager> temp = new Arete<Teenager>(t1EoF,t2EoF);
        		if(Platform.combinaisonValide(temp,aempecher)) {
        			aempecher.add(temp);
        			t1EoF=null;
                	t2EoF=null;
                	infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->");
        		}else {
        			t1EoF=null;
                	t2EoF=null;
        			infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->\n\nSaisie incorrecte ! \n Remarque: \nVous ne pouvez pas saisir deux fois le même adolescent.");
            	}
        		
        	}else {
        		t1EoF=null;
            	t2EoF=null;
    			infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->\n\nSaisie invalide ! \n Remarque: \nVous ne pouvez pas saisir deux fois le même adolescent.");;
        	}
        });
        retourEoF.setOnAction(nullevent -> {
        	items = FXCollections.observableArrayList(teenagersNames);
        	listEtu.setItems(items);
        	infoEoF.clear();
        	primaryStage.setScene(scenePrincipale);
        });
        eofItem.setOnAction(nullevent -> {
        	items = FXCollections.observableArrayList(teenagersNames);
        	listEoF.setItems(items);
        	infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->\n\nDeuxième étudiant sélectionné :\n->");
        	primaryStage.setScene(sceneEoF);
        });
        
        listEoF.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	int selectedIndex=0;
        	if(listEoF.getSelectionModel().getSelectedItem()!=null) {
        		if (listEoF.getSelectionModel().getSelectedItem().charAt(0)=='-') {
        			infoEoF.clear();
        		}else {
        			selectedIndex = Integer.parseInt(listEoF.getSelectionModel().getSelectedItem().substring(0,3));
        			//Rajouter condition if listView>max
        			if(selectedIndex>teenagersNames.size()) {
        				selectedIndex = listEoF.getSelectionModel().getSelectedIndex();
        			}
        			if (t1EoF==null) {
                		t1EoF = teenagers.get(selectedIndex);
                		infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->"+t1EoF.getName()+" "+t1EoF.getForename()+"\n\nDeuxième étudiant sélectionné :\n->");
                	}else {
                		t2EoF = teenagers.get(selectedIndex);
                		infoEoF.setText("A empêcher:"+aempecher+"\nA forcer:"+aforcer+"\nPremier étudiant sélectionné :\n->"+t1EoF.getName()+" "+t1EoF.getForename()+"\n\nDeuxième étudiant sélectionné :\n->"+t2EoF.getName()+" "+t2EoF.getForename()+"\n");
                	}
        		}
        	}		
        });
        
        
        reglageItem.setOnAction(event -> {
        	listePays=listerPays(teenagers);
        	choiceCountryGuest.setItems(FXCollections.observableArrayList(listePays));
        	choiceCountryHost.setItems(FXCollections.observableArrayList(listePays));
        	choiceCountryHost.setValue(countryHost);
            choiceCountryGuest.setValue(countryGuest);
        	primaryStage.setScene(sceneReglages);
        });
        
        importIntro.setOnAction(event -> {
        	csvFilePath=chooseCSVFile();
            teenagers = Platform.CSVtoTEENAGERS(csvFilePath);
            listePays=listerPays(teenagers);
            countryHost=listePays.get(0);
            countryGuest=listePays.get(1);
            choiceCountryHost.setValue(countryHost);
            choiceCountryGuest.setValue(countryGuest);
        	teenagersNames = Platform.teenagersToString(teenagers);
            primaryStage.setScene(scenePrincipale);
        	items = FXCollections.observableArrayList(teenagersNames);
        	listEtu.setItems(items);
        	choixDesistement.setItems(FXCollections.observableArrayList("HOST","GUEST","BOTH"));
        	choixDesistement.setValue("HOST");
        });
        
        importItem.setOnAction(event -> {
        	csvFilePath=chooseCSVFile();
            teenagers = Platform.CSVtoTEENAGERS(csvFilePath);
            listePays=listerPays(teenagers);
        	teenagersNames = Platform.teenagersToString(teenagers);
        	items = FXCollections.observableArrayList(teenagersNames);
        	listEtu.setItems(items);
        });
        
        desistement.setOnAction(event -> {
        	if(selectedBinome!=null) {
        		appariements.remove(selectedBinome);
        		ArrayList<Teenager>listePossible=new ArrayList<Teenager>();
        		if(choixDesistement.valueProperty().equals("HOST")) {
        			for(Teenager t:misDeCotes) {
        				if(t.getCountry().equals(countryHost)) {
        					listePossible.add(t);
        				}
        			}
        			if(!listePossible.isEmpty()) {
        				double meilleur=AffectationUtil.advancedWeight(listePossible.get(0), selectedBinome.getExtremite2(),new History(""),coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aforcer,aempecher);
            			double temp=0.0;
            			Arete<Teenager> champion=new Arete<Teenager>(listePossible.get(0), selectedBinome.getExtremite2());
        	    		for(int i=1; i<listePossible.size();i+=1) {
        	    			temp=AffectationUtil.advancedWeight(listePossible.get(i), selectedBinome.getExtremite2(),new History(""),coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aforcer,aempecher);
        	    			if(temp<meilleur) {
        	    				meilleur=temp;
        	    				champion=new Arete<Teenager>(listePossible.get(i), selectedBinome.getExtremite2());
        	    			}
        	    		}
        	    		appariements.add(champion);
        	    	}
        		}else if(choixDesistement.valueProperty().equals("GUEST")) {
        			for(Teenager t:misDeCotes) {
        				if(t.getCountry().equals(countryGuest)) {
        					listePossible.add(t);
        				}
        			}
        			if(!listePossible.isEmpty()) {
        				double meilleur=AffectationUtil.advancedWeight(selectedBinome.getExtremite1(),listePossible.get(0),new History(""),coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aforcer,aempecher);
            			double temp=0.0;
            			Arete<Teenager> champion=new Arete<Teenager>(selectedBinome.getExtremite1(),listePossible.get(0));
        	    		for(int i=1; i<listePossible.size();i+=1) {
        	    			temp=AffectationUtil.advancedWeight(selectedBinome.getExtremite1(),listePossible.get(i),new History(""),coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aforcer,aempecher);
        	    			if(temp<meilleur) {
        	    				meilleur=temp;
        	    				champion=new Arete<Teenager>(selectedBinome.getExtremite1(),listePossible.get(i));
        	    			}
        	    		}
        	    		appariements.add(champion);
        	    	}
        		}
    	    }
        	appariementsNames = Platform.teenagersToString(appariements);
    	    appariementsNames.add("---Etudiants rejeté---");
    	    appariementsNames.addAll(Platform.teenagersToString(misDeCotes));
        	items = FXCollections.observableArrayList(appariementsNames);
    	    listAppa.setItems(items);
    	    infoAppa.clear();
    	    	
        });
        
        // Ajout d'un écouteur d'événements pour la sélection de la liste
        	listEtu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	int selectedIndex=0;
        	if(listEtu.getSelectionModel().getSelectedItem()!=null) {
        		if (listEtu.getSelectionModel().getSelectedItem().charAt(0)=='-') {
        			infoEtu.clear();
        		}else {
        			selectedIndex = Integer.parseInt(listEtu.getSelectionModel().getSelectedItem().substring(0,3));
        			//Rajouter condition if listView>max
        			if(selectedIndex>teenagersNames.size()) {
        				selectedIndex = listEtu.getSelectionModel().getSelectedIndex();
        			}
        			Teenager selectedTeenager = teenagers.get(selectedIndex);
            		infoEtu.setText(InterfaceV2.informationToString(selectedTeenager));
        		}
        		
        	}
        });
        	
        	listAppa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            	int selectedIndex=0;
            	int limitIndex=0;
            	if(listAppa.getSelectionModel().getSelectedItem()!=null) {
            		if (listAppa.getSelectionModel().getSelectedItem().charAt(0)=='-') {
            			infoAppa.clear();
            		}else {
            			selectedIndex = Integer.parseInt(listAppa.getSelectionModel().getSelectedItem().substring(0,3));
            			limitIndex = listAppa.getSelectionModel().getSelectedIndex();
            			//Rajouter condition if listView>max
            			if(limitIndex>appariements.size()) {
            				Teenager selectedTeenager = misDeCotes.get(selectedIndex);
                    		infoAppa.setText(InterfaceV2.informationToString(selectedTeenager));
            			}else {
            				try {
            				selectedBinome = appariements.get(selectedIndex);
                    		infoAppa.setText(InterfaceV2.informationToString(selectedBinome));
            				}catch(Exception e) {
            					Teenager selectedTeenager = misDeCotes.get(selectedIndex);
                        		infoAppa.setText(InterfaceV2.informationToString(selectedTeenager));
            				}
            			}
            			
            		}
            	}
            });
        
        // Configuration de la fenêtre principale
        primaryStage.setTitle("Interface JavaFX");
        primaryStage.setScene(sceneIntro);
        primaryStage.show();
    }
    
    /**
	 * Crée et confugure une Hbox pour le bas de l'écran
	 * @return Hbox déjà configurée
	 */
    private HBox createSearchBar() {
        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(10));
        bottom.getChildren().addAll(searchBar, searchButton);
        return bottom;
    }
    /**
	 * Crée et confugure une Hbox pour le haut de l'écran
	 * @return Hbox déjà configurée
	 */
    private HBox createTopBar() {
        HBox top = new HBox(10);
        top.setPadding(new Insets(10));
      	top.getChildren().addAll(menuBar,lancement);
        return top;
    }
    /**
	 * Crée et confugure une Hbox pour le bas de la page des réglages EoF
	 * @return Hbox déjà configurée
	 */
    private HBox createSearchBarEoF() {
        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(10));
        bottom.getChildren().addAll(searchBarEoF, searchButtonEoF,supprimerEoF);
        return bottom;
    }
    /**
	 * Crée et confugure une Hbox pour le haut de la page des réglages EoF
	 * @return Hbox déjà configurée
	 */
    private HBox createTopBarEoF() {
        HBox top = new HBox(10);
        top.setPadding(new Insets(10));
      	top.getChildren().addAll(retourEoF,renitialiserEoF,forcerEoF,empecherEoF);
        return top;
    }
    /**
	 * Crée et confugure une Hbox pour le bas de la page des appariements
	 * @return Hbox déjà configurée
	 */
    private HBox createSearchBarAppa() {
        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(10));
        bottom.getChildren().addAll(searchBarAppa, searchButtonAppa);
        return bottom;
    }
    /**
	 * Crée et confugure une Hbox pour le haut de la page des appariements
	 * @return Hbox déjà configurée
	 */
    private HBox createTopBarAppa() {
        HBox top = new HBox(10);
        top.setPadding(new Insets(10));
      	top.getChildren().addAll(annuler, desistement, choixDesistement);
        return top;
    }
    
    
    /**
	 * Ouvre un explorateur de fichier pour sélectionner un fichier CSV
	 * @return String correspond au chemin vers le fichier CSV sélectionné
	 */
    private static String chooseCSVFile() {    	 
    		FileChooser fileChooser = new FileChooser();
      	    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/csv/"));
      	    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
      	    File selectedFile = fileChooser.showOpenDialog(null);

      	    if (selectedFile != null) {
      	        return selectedFile.getAbsolutePath();
      	    }

      	    return null;
      }
    
    /*private static ArrayList<String> teenagersToString(ArrayList<Teenager> students) {
    	ArrayList<String> studentsNames = new ArrayList<String>();
    	for(int i=0; i<students.size();i+=1) {
    		studentsNames.add(students.get(i).getForename()+" "+students.get(i).getName());
    	}
    	return studentsNames;
    }*/
    /**
	 * Lance la recherche dans la liste des étudiants à partir du texte de la barre de recherche
	 */
    void search(ActionEvent event) {
        listEtu.getItems().clear();
        listEtu.getItems().addAll(searchList(searchBar.getText(),teenagersNames));
        //listView.getItems().addAll(searchList(searchBar.getText(),appariementsNames));
    }
    /**
	 * Lance la recherche dans la liste des appariements à partir du texte de la barre de recherche
	 */
    void searchAppa(ActionEvent event) {
        listAppa.getItems().clear();
        listAppa.getItems().addAll(searchList(searchBarAppa.getText(),appariementsNames));
    }
    /**
	 * Lance la recherche dans la liste des étudiants sur la page des réglages EoF à partir du texte de la barre de recherche
	 */
    void searchEoF(ActionEvent event) {
        listEoF.getItems().clear();
        listEoF.getItems().addAll(searchList(searchBarEoF.getText(),teenagersNames));
    }
    
    /**
	 * Fonction commune à toute les méthodes search, permet la recherche
	 * *return List<String> correspond au résultat de la recherche
	 */
    private List<String> searchList(String searchWords, List<String> listOfStrings) {

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
    
    
    /**
	 * Converit les informations d'un étudiant en String et les met en page pour qu'elles soient lisibles
	 * @param Teenager t prend un étudiant t et convertit ses informations en String
	 * @return String informations sur l'étudiant
	 */
    public static String informationToString(Teenager t) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Nom: ");
    	sb.append(t.getName());
    	sb.append(" ");
    	sb.append(t.getForename());
    	sb.append("\nDate de naissance: ");
    	sb.append(t.getBirthDate());
    	sb.append("\nGenre: ");
    	sb.append(t.getGender());
    	sb.append("\nPays: ");
    	sb.append(t.getCountry());
    	return sb.toString();
    }
    
    /**
   	 * Converit les informations d'un binôme d'étudiants en String et les met en page pour qu'elles soient lisibles
   	 * @param Arete<Teenager> at prend un binôme d'étudiants at et convertit ses informations en String
   	 * @return String informations sur le binôme d'étudiants
   	 */
    public static String informationToString(Arete<Teenager> at) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("- Etudiant 1 -\n");
    	sb.append(informationToString(at.getExtremite1()));
    	sb.append("\n\n- Etudiant 2 -\n");
    	sb.append(informationToString(at.getExtremite2()));
    	sb.append("\n\nScore de compatibilité: ");
    	sb.append(at.getPoids());
    	return sb.toString();
    }
    
    /**
   	 * Liste les pays diifférents présents dans une liste d'étudiants
   	 * @param ArrayList<Teenager> students parcourt cette liste, et pour chaque nouveau pays, l'ajoute à une autre liste.
   	 * @return ArrayList<String> envoie la liste de tous les pays différents présents dans la lsite d'étudiants.
   	 */
    public static ArrayList<String> listerPays(ArrayList<Teenager> students){
    	ArrayList<String> pays = new ArrayList<String>();
    	for(Teenager t:students) {
    		if(!pays.contains(t.getCountry())) {
    			pays.add(t.getCountry());
    		}
    	}
    	return pays;
    }
    
    /**
   	 * Prépare une liste d'étudiants à être utilisé par un graphe. La trie et ne garde que deux pays différents
   	 * @param ArrayList<Teenager> students liste à préparer
   	 * @param ArrayList<String> pays liste des pays différents présents dans la liste de Teenager
   	 * @param String countryHost pays qui sera Hôte
   	 * @param String countryGuest pays qui sera Guest
   	 * @return ArrayList<ArrayList<String>> envoie une arrayList d'ArrayList car cette fonction coupe en deux la liste des étudiants en fonction du pays Hôte et du pays Guest
   	 */
    public static ArrayList<ArrayList<Teenager>> preparationGraphe(ArrayList<Teenager> students,ArrayList<String> pays, String countryHost, String countryGuest) {
		ArrayList<Teenager> partie1=new ArrayList<Teenager>();
		ArrayList<Teenager> partie2=new ArrayList<Teenager>();
		ArrayList<Teenager> misDeCotes=new ArrayList<Teenager>();
		ArrayList<ArrayList<Teenager>> listFinal = new ArrayList<ArrayList<Teenager>>();
		for(Teenager t:students) {
    		if(t.getCountry().equals(countryHost)) {
    			partie1.add(t);
    		}else if(t.getCountry().equals(countryGuest)) {
    			partie2.add(t);
    		}else {
    			misDeCotes.add(t);
    		}
    	}
		////
		if(partie1.size()>partie2.size()) {
			for(int id = partie2.size(); id<partie1.size(); id++) {
				misDeCotes.add(partie1.get(id));
			}
			partie1.removeAll(misDeCotes);
		}else if(partie2.size()>partie1.size()) {
			for(int id = partie1.size(); id<partie2.size(); id++) {
				misDeCotes.add(partie2.get(id));
			}
			partie2.removeAll(misDeCotes);
		}
		listFinal.add(partie1);
		listFinal.add(partie2);
		listFinal.add(misDeCotes);
		return listFinal;
	}
}
