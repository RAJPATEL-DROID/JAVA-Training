import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.JSONObject;

public class TwoWayCommClient
{
    public static void main(String[] args)
    {
        try
        {
        Socket socket = new Socket("localhost", 9090);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String str  = "", str2 ="";

            while(!str.equals("Stop")){
//                str = br.readLine();

                out.println(str);

                str2 = input.readLine();
                System.out.println("Server Says : " + str2);
            }
            out.close();
            input.close();
            br.close();

        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}

