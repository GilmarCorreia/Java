package players;

import boats.structs.Coordinate;
import boats.structs.Shot;

public interface GamePlayerFunctions{
	
	// Função que retorna coordenada que o jogador irá shootCoordr
	public abstract Coordinate shoot();
	
	// Função que retorna o tipo de tiro recebido
	public abstract Shot checkShot(Coordinate shot);
	
	//Função que retorn o 
	public abstract boolean validatePosition(Coordinate initial, Coordinate finals);
	
	
}