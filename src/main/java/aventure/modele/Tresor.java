package aventure.modele;

import java.io.Serializable;

public class Tresor implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String description;

    public Tresor(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
