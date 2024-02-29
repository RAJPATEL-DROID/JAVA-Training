import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class URLClassExample
{
    public static void main(String[] args) throws IOException
    {

        try
        {
            URL url = new URL("https://www.google.com");
            try(InputStream input = url.openStream();)
            {
            int c;
            while( (c = input.read()) != -1){
                System.out.write(c);
            }
            }

        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
