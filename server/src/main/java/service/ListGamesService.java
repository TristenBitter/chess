package service;

import dataaccess.DataAccessException;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.sql.MySqlAuthDAO;
import dataaccess.sql.MySqlGameDAO;
import model.ListGamesRequest;

import java.util.ArrayList;

public class ListGamesService {

  private static AuthDAO authDAO = new MySqlAuthDAO();
  private static GameDAO gameDAO = new MySqlGameDAO();

  public ArrayList<ListGamesRequest> listGames(String authToken)throws DataAccessException{
    if(!authDAO.validateAuthToken(authToken)){
      throw new DataAccessException("Error: unauthorized");
    }

    return gameDAO.getListOfGames();
  }
}
