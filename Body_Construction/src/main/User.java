package main;

public class User  implements Comparable<User>{
	static public String CurrentUser,
		CurrentUser0;
	static public int CurrentType;
	private int Type, //Тип пользователя
	id;
	private String Name_User,//полное имя
	Name0,//имя
	Date,//дата регистрации
	Gym = "",// ЗАл
	login,
	password;
	private boolean active;
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public String getName_User() {
		return Name_User;
	}
	public void setName_User(String name_User) {
		Name_User = name_User;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getName0() {
		return Name0;
	}
	public void setName0(String name0) {
		Name0 = name0;
	}
	public String getGym() {
		return Gym;
	}
	public void setGym(String gym) {
		Gym = gym;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return Name_User + Type + Gym;
	}
	@Override
	public int compareTo(User o) {
			return this.toString().compareTo(o.toString());
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (this.toString().compareTo(other.toString()) != 0) {
			return false;
		}
		return true;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
