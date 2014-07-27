package ba.idrol.Game;

import org.lwjgl.input.Keyboard;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.network.client.Network;
import ba.idrol.server.packets.PacketPositionUpdate;

public class LocalPlayer extends Player {

	public LocalPlayer(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	@Override
	public void update(){
		super.update();
		int x = 0, y = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * Main.getDeltaTime();
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * Main.getDeltaTime();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) this.jump();
		this.move(x, y);
		Network network = Game.getNetwork();
		
		if(x != 0 || y != 0){
			PacketPositionUpdate packet = new PacketPositionUpdate();
			packet.x = this.x;
			packet.y = this.y;
			network.client.sendUDP(packet);
		}
	}
	
	public void jump(){
		if(this.onGround){
			this.vertical_speed += 1.2f;
		}
		Network network = Game.getNetwork();
		if(x != 0 || y != 0){
			PacketPositionUpdate packet = new PacketPositionUpdate();
			packet.x = this.x;
			packet.y = this.y;
			network.client.sendUDP(packet);
		}
	}

}
