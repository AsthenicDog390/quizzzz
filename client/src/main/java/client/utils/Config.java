package client.utils;

public class Config {
    private String serverLocation = "http://localhost:8080";

    public String getServerLocation() {
        return serverLocation;
    }

    public void setServerLocation(String serverLocation) {
        this.serverLocation = serverLocation;
    }
}
