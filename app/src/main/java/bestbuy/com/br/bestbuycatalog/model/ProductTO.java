package bestbuy.com.br.bestbuycatalog.model;

import java.io.Serializable;


public class ProductTO implements Serializable {

    private String name;
    private String description;
    private String price;
    private String shipping;
    private int    image;

    public ProductTO(String name, String description, String price, String shipping, int imagem) {
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.shipping    = shipping;
        this.image       = imagem;
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
