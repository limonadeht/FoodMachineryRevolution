package client;

import org.lwjgl.input.Keyboard;

import server.ServerProxy;

public class ClientProxy extends ServerProxy{

	@Override
	public boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}
}
