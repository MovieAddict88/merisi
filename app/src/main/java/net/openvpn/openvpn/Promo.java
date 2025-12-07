package net.openvpn.openvpn;

public class Promo {
    private int id;
    private String name;
    private String iconUrl;

    public Promo(int id, String name, String iconUrl) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}

