/**
 * 
 */
package net.skyebook.betaville.mongosession;

import java.net.UnknownHostException;

import com.mongodb.MongoException;

import edu.poly.bxmc.betaville.server.session.availability.InMemorySessionTracker;
import edu.poly.bxmc.betaville.server.session.availability.SessionTracker;

/**
 * @author Skye Book
 *
 */
public class MongoSessionTest {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, UnknownHostException, MongoException{
		//SessionTracker tracker = (SessionTracker) Class.forName("net.skyebook.mongosession.MongoSessionTracker").newInstance();
		
		SessionTracker st = new MongoSessionTracker();
		InMemorySessionTracker it = new InMemorySessionTracker();
		
		
		long mongoStart = System.currentTimeMillis();
		for(int i=0; i<10000; i++){
			st.addSession(i, ("user"+i));
		}
		System.out.println("Mongo insert took " + (System.currentTimeMillis()-mongoStart) + "ms");
		
		long memoryStart = System.currentTimeMillis();
		for(int i=0; i<10000; i++){
			it.addSession(i, ("user"+i));
		}
		System.out.println("In Memory insert took " + (System.currentTimeMillis()-memoryStart) + "ms");
	}

}