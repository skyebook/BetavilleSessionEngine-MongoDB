/**  
*  MongoDB Betaville Session Engine - Betaville session storage in MongoDB
*  Copyright (C) 2011 Skye Book
*  
*  This program is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
*  
*  This program is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*  
*  You should have received a copy of the GNU General Public License
*  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.skyebook.betaville.mongosession;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import edu.poly.bxmc.betaville.server.session.Session;
import edu.poly.bxmc.betaville.server.session.availability.SessionTracker;
import edu.poly.bxmc.betaville.util.Crypto;

/**
 * @author Skye Book
 *
 */
public class MongoSessionTracker extends SessionTracker {
	
	private Mongo mongo;
	private DB database;
	private DBCollection collection;
	

	/**
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 * 
	 */
	public MongoSessionTracker() throws UnknownHostException, MongoException {
		mongo = new Mongo("localhost");
		database = mongo.getDB("betaville-sessions");
		collection = database.getCollection("sessions");
	}

	/* (non-Javadoc)
	 * @see edu.poly.bxmc.betaville.server.session.availability.SessionProvider#addSession(int, java.lang.String)
	 */
	@Override
	public synchronized Session addSession(int sessionID, String user) {
		String tokenCandidate = Crypto.createSessionToken();
		while(sessionTokenExists(tokenCandidate)){
			tokenCandidate = Crypto.createSessionToken();
		}
		
		Session session = new Session(user, sessionID, tokenCandidate);
		insertSessionToDB(session);
		
		return session;
	}
	
	private void insertSessionToDB(Session session){
		BasicDBObject sessionObject = new BasicDBObject();
		sessionObject.put(SessionConstants.USER, session.getUser());
		sessionObject.put(SessionConstants.PASS, session.getPassword());
		sessionObject.put(SessionConstants.SESSION_ID, session.getSessionID());
		sessionObject.put(SessionConstants.SESSION_TOKEN, session.getSessionToken());
		collection.insert(sessionObject);
	}

	/* (non-Javadoc)
	 * @see edu.poly.bxmc.betaville.server.session.availability.SessionProvider#killSession(java.lang.String)
	 */
	@Override
	public int killSession(String sessionToken) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see edu.poly.bxmc.betaville.server.session.availability.SessionProvider#getSession(java.lang.String)
	 */
	@Override
	public Session getSession(String sessionToken) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.poly.bxmc.betaville.server.session.availability.SessionProvider#sessionTokenExists(java.lang.String)
	 */
	@Override
	public boolean sessionTokenExists(String tokenCandidate) {
		// TODO Auto-generated method stub
		return false;
	}

}
