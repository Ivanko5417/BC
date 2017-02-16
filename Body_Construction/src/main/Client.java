package main;
import javax.swing.JTable;

import com.sun.org.apache.xerces.internal.util.URI;
public class Client implements Comparable<Client> {
	private int id, numberDates, cost, typeOfTrain = -1, status;// номер перезвона, больше 3-х в слив
	private String source, // откуда спам
			accountSpam, // ссылка на акк, с которого был спам
			name, // имя человека, которого отспамили
			spam, // имя спамщика
			call, // имя звонильщика
			trainer, // имя тренера
			number, // номер телефона
			accountClient, // ссылка на акк клиента
			date, // дата спама
			address, // адресс клиента
			comment, // комментарий
			gym, // зал, в кот. хочет заниматься
			courier, // имя доставщика
			 dateCall = "          "; // статус клиента(на обзвоне, готов к пробной и
								// т.д.)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAccountSpam() {
		return accountSpam;
	}
	public void setAccountSpam(String accountSpam) {
		this.accountSpam = accountSpam;
	}
	public String getSpam() {
		return spam;
	}
	public void setSpam(String spam) {
		this.spam = spam;
	}
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	public String getAccountClient() {
		return accountClient;
	}
	public void setAccountClient(String accountClient) {
		this.accountClient = accountClient;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getGym() {
		return gym;
	}
	public void setGym(String gym) {
		this.gym = gym;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] toStringSpam() {
		String[] s = {call, courier, trainer, gym, accountSpam, name, number,
				accountClient, date, address, comment, ""+status};
		return s;
	}
	public int getNumberDates() {
		return numberDates;
	}
	public void setNumberDates(int numberDates) {
		this.numberDates = numberDates;
	}
	@Override
	public String toString() {
		return getDateCall() + " " + getDate();
	}
	@Override
	public int compareTo(Client o) {
		if(this.getStatus() == 10 && this.getStatus() == o.getStatus())
			return 0;
		else
		if (this.getStatus() == 10)
			return 999999;
		else if (o.getStatus() == 10)
			return -999999;
		else
		return this.toString().compareTo(o.toString());
	}
	@Override
	public boolean equals(Object obj) {
		
		  if (obj == null) { return false; } if (getClass() != obj.getClass())
		  { return false; }
		 
		final Client other = (Client) obj;
		if (this.toString().compareTo(other.toString()) != 0) {
			// if(this.id != other.id){
			return false;
		}
		return true;
	}
	public String getDateCall() {
		return dateCall;
	}
	public void setDateCall(String dateCall) {
		this.dateCall = dateCall;
	}
	public int getTypeOfTrain() {
		return typeOfTrain;
	}
	public void setTypeOfTrain(int typeOfTrain) {
		this.typeOfTrain = typeOfTrain;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String get(int i)
	{
		switch(i)
		{
			case 0:
				return getCall();
			case 1:
				return getCourier();
			case 2:
				return getTrainer();
			case 3:
				return getGym();
			case 4:
				return getAccountSpam();
			case 5:
				return getName();
			case 6:
				return getNumber();
			case 7:
				return getAccountClient();
			case 8:
				return getDate();
			case 9:
				return getAddress();
			case 10:
				return getComment();
			case 11:
				return getStatus()+ "";
			case 12:
				return getCost()+ "";
			case 13:
				return getTypeOfTrain() + "";
		}
		return "";
	}
}
