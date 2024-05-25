package model;


public record GameData(int GameID, String whiteUsername, String blackUsername, String gameName, chess.ChessGame game) {
}
