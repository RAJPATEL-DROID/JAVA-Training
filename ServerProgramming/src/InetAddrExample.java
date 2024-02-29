import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

public class InetAddrExample
{
    public static void main(String[] args)
    {
        try
        {
//            InetAddress localHost = InetAddress.getLocalHost();
//            System.out.println(localHost.isLoopbackAddress());
            //            System.out.println(localHost);
//            InetAddress address = InetAddress.getLoopbackAddress();
//            System.out.println(address.getHostAddress());
//            System.out.println(address.getHostName());
//            System.out.println(address.getCanonicalHostName());
//            System.out.println();
//            System.out.println(localHost.getHostName());
//            System.out.println(localHost.getHostAddress());
//            System.out.println(localHost.getCanonicalHostName());

//            InetAddress[] address1 = InetAddress.getAllByName("google.com");
//            for(InetAddress ip : address1){
//            System.out.println(ip.getHostName());
//            System.out.println(ip.getHostAddress());
//            }
            Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
            while(ni.hasMoreElements()){
                NetworkInterface n = ni.nextElement();
                System.out.println(n.getDisplayName());
                Enumeration addresses = n.getInetAddresses();
                while(addresses.hasMoreElements()){
                    System.out.println(addresses.nextElement());
                }
                System.out.println();
            }

        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
