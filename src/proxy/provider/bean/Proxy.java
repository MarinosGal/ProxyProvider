package proxy.provider.bean;

public class Proxy {

    private String ip;
    private int port;

    public Proxy(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
