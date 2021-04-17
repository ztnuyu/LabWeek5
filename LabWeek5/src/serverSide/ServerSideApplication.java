package serverSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.ItemProduct;

public class ServerSideApplication {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		try {	
			
			// Port to receive and respond to request
			int portNo = 4228;
			ServerSocket serverSocket = new ServerSocket(portNo);
			
			System.out.println("Ready for request");
			
			// Server need to be alive forever thus the while(true)
			while(true) {	
				
				// Accept client request for connection
				Socket socket = serverSocket.accept();
				
				// Create input stream to read object
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				
				// Read object from stream and cast it to ArrayList of Location
				List<ItemProduct> itemProduct = (ArrayList<ItemProduct>) objectInputStream.readObject();
				
				// Process object - assign location id
				for (int index=0; index < itemProduct.size(); index++) {					
					ItemProduct currentLocation = itemProduct.get(index);					
					currentLocation.setItemProductId((index + 1) * 1000);					
				}
				
				// Create output stream to send object
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(itemProduct);
								
				System.out.println("Ready for next request");
				
				// Close all streams
				objectInputStream.close();
				objectOutputStream.close();		
			
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
