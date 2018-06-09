package ztf.handler;

import ztf.bean.Msg;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class STCSendPortHandle implements Runnable{

	private Msg msg;
	private String ip;
	private String port;
	private Socket socket;
	
	public STCSendPortHandle(String ip,String port,Msg msg) {
		this.ip=ip;
		this.port=port;
		this.msg=msg;
	}

	@Override
	public void run() {
		try {
			socket=new Socket(ip, Integer.valueOf(port));
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(msg);
			os.writeObject(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
