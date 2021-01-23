import java.net.*;
import java.io.*;
import java.util.Scanner;

class client 
{
    
    // init socket and input output streams
    private Socket socket = null;
    private InputStream input = null;
    private OutputStream out = null;
    Scanner scan = new Scanner(System.in);

    // constructor to perform all functions when called in main
    public client() 
    {
        
        //read in IP Address and port number
        System.out.println("Input IP address: ");
        String ipaddr = scan.nextLine();
        //System.out.println("Input port number: ");
        //int portnum = scan.nextInt();
        int portnum = 16000;
        
        try 
        {
            
            //Create a connection to the server
            socket = new Socket(ipaddr, portnum);
            System.out.printf("IP Address: %s\nPort Number: %d\n", ipaddr, portnum);
            System.out.println("Connection Successful");

            // Get input from the socket
            input = socket.getInputStream();

            // sends output to the socket
            out = socket.getOutputStream();

            
            //To read data
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            
            //To print data 
            PrintWriter writer = new PrintWriter(out, false);

            // string to read message from input
            String line;
            StringBuilder s;

            while (true) 
            {
               
                //read in line and print back what the user has input
                line = scan.nextLine();
                System.out.println("client: " + line);
                
                //checking the input to see if the user wants to QUIT the program
                if (line.contentEquals("QUIT")) 
                {
                    writer.write("QUIT\n");
                    writer.flush();
                    char[] temp = new char[200];
                    String str;
                    reader.read(temp);
                    str = new String(temp);
                    System.out.print("Server: " + str.trim());
                    break;
                }

                
                //Checking the input to see if the user wants to POST some data to the server
                else if (line.contentEquals("POST")) 
                {
                    s = new StringBuilder();
                    String usermsg;
                    s.append("POST\n");
                    while (true) {
                        usermsg = scan.nextLine();
                        System.out.println("client: " + usermsg);
                        s.append(usermsg);
                        s.append("\n");
                        if (usermsg.contentEquals(".")) 
                        {
                            break;
                        }
                    }
                    writer.print(s);
                    writer.flush();
                    char[] temp = new char[200];
                    String str;
                    reader.read(temp);
                    str = new String(temp);
                    System.out.println("Server: " + str.trim());

                }

                
                //Checking the input to see if the user wants to READ all the data from the server
                else if (line.contentEquals("READ")) 
                {
                    writer.write("READ\n");
                    writer.flush();
                    String str;
                    while (true) 
                    {
                        char[] temp = new char[200];
                        reader.read(temp);
                        str = new String(temp);
                        System.out.println(str.trim());
                        if (str.trim().contentEquals(".")) 
                        {
                            break;
                        }
                    }
                }
                
                else {
                    
                    //Checking if the line is empty
                    if (line.isEmpty())
                        out.write("\n".getBytes());
                    
                    //All other cases    
                    else
                        out.write(line.getBytes());
                    out.flush();
                    char[] temp = new char[200];
                    String str;
                    reader.read(temp);
                    str = new String(temp);
                    System.out.println(str.trim());
                }
            }
            // closing connections created for socket, inputstream and output stream
            input.close();
            out.close();
            socket.close();
        } 
        
        //Checking to see if the connection to the server has failed
        catch (IOException i) 
        {
            System.out.println("Connection has failed ");
        }
        //closing the scanner object
        scan.close();
    }

    public static void main(String args[]) 
    {

        //running the program in main
        client cli = new client();
    }
}
