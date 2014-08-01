package ba.idrol.packets;

/*
 * Packet to add game object on client side.
 */
public class PacketAddGameObject {
	public int width, height, id;
	public float x, y;
	public String spriteName;
}
