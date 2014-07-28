package ba.idrol.Game;

import org.lwjgl.input.Keyboard;

import ba.idrol.net.Main;
import ba.idrol.net.Sprite;
import ba.idrol.network.client.Network;
import ba.idrol.server.packets.PacketPlayerKeyPress;
import ba.idrol.server.packets.PacketPositionUpdate;

public class LocalPlayer extends Player {
	private float networkX = 0, networkY = 0;
	private boolean jumping = false, moving_left = false, moving_right = false;
	public LocalPlayer(Sprite sprite, float x, float y) {
		super(sprite, x, y);
	}
	
	@Override
	public void update(){
		super.update();
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_LEFT;
			Game.getNetwork().client.sendTCP(packet);
			this.moving_left = true;
		}else if(this.moving_left){
			this.moving_left = false;
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_LEFT;
			packet.keyReleased = true;
			Game.getNetwork().client.sendTCP(packet);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_RIGHT;
			Game.getNetwork().client.sendTCP(packet);
		}else if(this.moving_right){
			this.moving_right = false;
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_RIGHT;
			packet.keyReleased = true;
			Game.getNetwork().client.sendTCP(packet);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_UP;
			Game.getNetwork().client.sendTCP(packet);
		}else if(this.jumping){
			this.jumping = false;
			PacketPlayerKeyPress packet = new PacketPlayerKeyPress();
			packet.keyPressed = Keyboard.KEY_UP;
			packet.keyReleased = true;
			Game.getNetwork().client.sendTCP(packet);
		}
	}
	
	public void jump(){
		if(this.onGround){
			this.vertical_speed += 1.2f;
		}
	}
	public void networkUpdate(){
		Network network = Game.getNetwork();
		if(this.networkX != this.x || this.networkY != this.y){
			PacketPositionUpdate packet = new PacketPositionUpdate();
			packet.x = this.x;
			packet.y = this.y;
			network.client.sendUDP(packet);
			this.networkX = this.x;
			this.networkY = this.y;
		}
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}

}
