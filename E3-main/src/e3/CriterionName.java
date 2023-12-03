package e3;

//Tout les Critères possibles
public enum CriterionName{
	GUEST_ANIMAL_ALLERGY('B'),
    HOST_HAS_ANIMAL('B'),
    GUEST_FOOD('T'),
    HOST_FOOD('T'),
    PAIR_GENDER('T'),
    HOBBIES('T'),
    HISTORY('T');
	 
    /**
     * Associe un caractere aux critères pour désigner le type que l'on attend d'eux
     */
    private final char TYPE;
     
    /**
     * constructeur de l'énumération CriterionName
     * @param type type de critérion T ou B
     */
    CriterionName(char type) {
        this.TYPE = type;
    }
    
    /**
     * @return un caractère correspondant au type du critère
     */
    public char getType() {
        return TYPE;
    }
    
}

