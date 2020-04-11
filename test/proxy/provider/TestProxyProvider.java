package proxy.provider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestProxyProvider {

    private ProxyProvider proxyProvider;

    @Before
    public void setUpProxyProvider() {
        proxyProvider = new ProxyProvider();
    }

    @Test
    public void testGetProxyNotNull() {
        Assert.assertNotNull(proxyProvider.getProxy());
    }

    @Test
    public void testGetProxyIpIsNotNull(){
        Assert.assertNotNull(proxyProvider.getProxy().getIp());
    }

    @Test
    public void testCountryCodesEmpty(){
        proxyProvider = new ProxyProvider();
        Assert.assertNotNull(proxyProvider.getProxy());
    }

    @Test
    public void testPortAboveZero(){
        Assert.assertNotEquals(0, proxyProvider.getProxy().getPort());
    }
}
