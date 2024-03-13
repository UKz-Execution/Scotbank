package uk.co.asepstrath.bank.businesses;

public class Business {

    public String id;
    public String name;
    public String category;
    public String sanctioned;


    public Business() {

    }


    public Business(String id, String name, String category, String sanctioned) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.sanctioned = sanctioned;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSanctioned() {
        return sanctioned;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSanctioned(String sanctioned) {
        this.sanctioned = sanctioned;
    }
}
