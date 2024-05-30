package service;

import dataaccess.DataAccessException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import model.ListGamesRequest;

import java.util.ArrayList;

public class ListGamesService {

  private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
  private static MemoryGameDAO gameDAO = new MemoryGameDAO();

  public ArrayList<ListGamesRequest> listGames(String authToken)throws DataAccessException{
    if(!authDAO.validateAuthToken(authToken)){
      throw new DataAccessException("Error: unauthorized");
    }

    return gameDAO.getListOfGames();
  }
}
