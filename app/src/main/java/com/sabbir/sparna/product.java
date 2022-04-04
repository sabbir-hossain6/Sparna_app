package com.sabbir.sparna;

public class product {
    private String type;
    public String name;
    public String itemId;
    public String price;
    public String onOffer;
    public String offerPrice;
    public String description;
    public String size;
    public int quantity;
    public String image;

    public product() {
    }

    public product(String type, String name, String itemId, String price, String onOffer, String offerPrice, String description, String size, int quantity, String image) {
        this.type = type;
        this.name = name;
        this.itemId = itemId;
        this.price = price;
        this.onOffer = onOffer;
        this.offerPrice = offerPrice;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOnOffer() {
        return onOffer;
    }

    public void setOnOffer(String onOffer) {
        this.onOffer = onOffer;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity+"";
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
