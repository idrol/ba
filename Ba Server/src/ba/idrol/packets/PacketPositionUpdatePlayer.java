package ba.idrol.packets;

public class PacketPositionUpdatePlayer {
	public float x, y;
	public int id;
	// False = Right, True = Left;
	public boolean direction = false;
}
