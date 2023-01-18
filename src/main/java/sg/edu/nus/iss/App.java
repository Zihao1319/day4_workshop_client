package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 3032);

        try (OutputStream os = socket.getOutputStream()) {

            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Console cons = System.console();
            String readInput = "";
            String receivedMsg = "";

            try (InputStream is = socket.getInputStream()) {
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

                while (!readInput.equalsIgnoreCase("close")) {
                    readInput = cons.readLine(">>> ");
                    dos.writeUTF(readInput);
                    dos.flush();

                    receivedMsg = dis.readUTF();
                    System.out.println(receivedMsg);

                }

            } catch (EOFException e) {
                socket.close();
            }

        } catch (EOFException e) {
            socket.close();
        }

    }
}
