package model;

public record ListGamesRequest(int gameID, String whiteUsername, String blackUsername, String gameName) {
}
