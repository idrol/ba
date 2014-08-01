package ba.idrol.server.packets;

/*
 * Packet for player key presses
 */
public class PacketPlayerKeyPress {
	public int keyPressed, id;
	public boolean keyReleased = false;
	public boolean isMouse = false;
}
