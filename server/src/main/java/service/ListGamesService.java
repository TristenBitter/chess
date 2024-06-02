package service;

import dataaccess.DataAccessException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.ListGamesRequest;

import java.util.ArrayList;

public class ListGamesService {

  private static AuthDAO authDAO = new MemoryAuthDAO();
  private static GameDAO gameDAO = new MemoryGameDAO();

  public ArrayList<ListGamesRequest> listGames(String authToken)throws DataAccessException{
    if(!authDAO.validateAuthToken(authToken)){
      throw new DataAccessException("Error: unauthorized");
    }

    return gameDAO.getListOfGames();
  }
}
