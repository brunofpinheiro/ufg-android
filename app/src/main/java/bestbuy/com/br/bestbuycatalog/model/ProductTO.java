package bestbuy.com.br.bestbuycatalog.model;

import java.io.Serializable;


public class ProductTO implements Serializable {

    private String  sku;
    private String  name;
    private String  description;
    private String  price;
    private Boolean freeShipping;
    private String  image;

    public ProductTO(String sku, String name, String description, String price, Boolean freeShipping, String image) {
        this.sku          = sku;
        this.name         = name;
        this.description  = description;
        this.price        = price;
        this.freeShipping = freeShipping;
        this.image        = image;
    }



    /**
     * GETTERS AND SETTERS
     */
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

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

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
