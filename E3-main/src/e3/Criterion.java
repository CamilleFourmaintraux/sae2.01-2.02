package e3;

//classe à jour P00v2

// classe représentant un critère
public class Criterion {
	//Valeur que le critère "inspectera", sera fournie par le fichier csv.
	private String value;
	// Correspond au Nom du critère
    private CriterionName label;
    
    // 
    /**
     * crée un critère en prenant deux string en paramètres 
     * @param value Valeur du critère
     * @param label Nom du Critère
     */
    public Criterion(String value, CriterionName label) {
        this.value = value;
        this.label = label;
    }

    
    /**
     * @return Renvoie la valeur du critère
     */
    public String getValue() {
        return value;
    }
    
  //Renvoie la valeur du critère
    public void setValue(String newValue) {
        value=newValue;
    }
    
    /**
     * @return Renvoie le label du critère
     */
    public CriterionName getLabel() {
        return label;
    }
    
    //
    /**
     * @return Renvoie le Nom du Critère associé à ce Critère dans l'enum CriterionName
     */
    public CriterionName getCriterionName() {
        return label;
    }
    
    /**
     * Cette méthode renvoie un boolean et ne prend pas de paramètres. Permet de déterminer si le critère est valide et conforme aux attentes.
     * @return Renvoie true si le critère est valide et conforme au attente
     */
    public boolean isValid() {
        char type = this.getCriterionName().getType();
        try {
        	if (type == 'T') {
            	if(this.getCriterionName()==CriterionName.PAIR_GENDER) return (value.equals("male")||value.equals("female")||value.equals("other"));
            	if(this.getCriterionName()==CriterionName.HISTORY) return (value.equals("same")||value.equals("other")||value.equals(""));
            	//if(this.getCriterionName()==CriterionName.GUEST_FOOD) return (value.equals("vegetarian")||value.equals("nonuts")||value.equals(""));
            	//if(this.getCriterionName()==CriterionName.HOST_FOOD) return (value.equals("vegetarian")||value.equals("nonuts")||value.equals(""));
            	
            	return value instanceof String;
            } else if (type == 'N') {
                return value instanceof String;
            } else if (type == 'B') {
                return (value=="yes"||value=="no");
            }
        }
        catch(Exception e) {
        	System.out.println("Exeption : isValid() in Criterion");
        	return false;
        }
        return false;
    }
    
    //
    /**
     * Méthode toString() pour afficher le critère.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.label);
        sb.append(": ");
        sb.append(this.value);
        sb.append("\n");
        return sb.toString();
    }

}
