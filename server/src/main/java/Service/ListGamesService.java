package Service;

import dataaccess.DataAccessException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import model.ListGamesRequest;

import java.util.ArrayList;

public class ListGamesService {

  private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
  private static MemoryGameDAO gameDAO = new MemoryGameDAO();

  public ArrayList<ListGamesRequest> listGames(String authToken)throws DataAccessException{
      //ValidateToken
    if(!authDAO.validateAuthToken(authToken)){
      throw new DataAccessException("Error: unauthorized");
      //return null;
    }

      //Retrieve Games (including the board)

    return gameDAO.getListOfGames();
  }

  public ArrayList<ListGamesRequest> RetrieveGames(){

    return null;
  }
}
