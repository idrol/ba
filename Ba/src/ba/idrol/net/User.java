package ba.idrol.net;

public class User {
	public String userName, salt;
	public String toString(){
		return this.userName+", "+this.salt;
	}
}
