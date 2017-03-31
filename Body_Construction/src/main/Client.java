package main;
import static panels.Common.clients;
public class Client implements Comparable<Client> {
	public static final int NOTHING = -1, FALSE = 0, TRUE = 2, MID = 1;
	private int id, numberDates, // номер перезвона, больше 3-х в слив
			typeOfTrain = -1, // тип тренировки
			status, // статус клиента(на обзвоне, готов к пробной и
			lastDateId = -1, day = -1, // день мес€ца, в котором будет
										// тренировка
			numberTrain, // номер тренировки из пакета
			countOfTrain, // кол-во всех тренировок в пакете
			numberCV, // номер конверсии
			logo = -1, // есть ли майки
			PR = -1, // привЄл ли кого
			prePay = -1, // была ли предоплата на несколько мес€цев
			opinion = -1;// был ли отзыв
	private double debt, // долг
			cost; // цена пакета
	private boolean isClient = false; // €вл€етс€ ли он клиентом(была ли у него
										// платна€)
	private String source, // откуда спам
			accountSpam, // ссылка на акк, с которого был спам
			name, // им€ человека, которого отспамили
			spam, // им€ спамщика
			call, // им€ звонильщика
			trainer, // им€ тренера
			number, // номер телефона
			accountClient, // ссылка на акк клиента
			date, // дата спама
			dateCV, // дата первой конверсии
			address, // адресс клиента
			comment, // комментарий
			gym, // зал, в кот. хочет заниматьс€
			courier, // им€ доставщика
			lastDate = "          ";
	public static String getColor(int color) {
		switch (color) {
			case Client.NOTHING :
				return Constants.NameOfColors.WHITE;
			case Client.FALSE :
				return Constants.NameOfColors.RED;
			case Client.MID :
				return Constants.NameOfColors.YELLOW;
			case Client.TRUE :
				return Constants.NameOfColors.GREEN;
			default :
				return "";
		}
	}
	public static int getColor(String color) {
		switch (color) {
			case Constants.NameOfColors.WHITE :
				return Client.NOTHING;
			case Constants.NameOfColors.RED :
				return Client.FALSE;
			case  Constants.NameOfColors.YELLOW:
				return Client.MID;
			case  Constants.NameOfColors.GREEN:
				return Client.TRUE;
			default :
				return -1;
		}
	}
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
				accountClient, date, address, comment, "" + status};
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
		if (User.CurrentType != Constants.TypesOfUsers.TRAINER)
			return getStatus() + getLastDate() + " " + getDate();
		else
			return getLastDate() + " " + getDate();
	}
	@Override
	public int compareTo(Client o) {
		if (this.getStatus() == 10 && this.getStatus() == o.getStatus())
			return 0;
		else if (this.getStatus() == 10)
			return 999999;
		else if (o.getStatus() == 10)
			return -999999;
		else
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
		final Client other = (Client) obj;
		if (this.toString().compareTo(other.toString()) != 0) {
			// if(this.id != other.id){
			return false;
		}
		return true;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate, int id) {
		this.lastDate = lastDate;
		setLastDateId(id);
	}
	public int getTypeOfTrain() {
		return typeOfTrain;
	}
	public void setTypeOfTrain(int typeOfTrain) {
		this.typeOfTrain = typeOfTrain;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String get(int i) {
		switch (i) {
			case 0 :
				return getCall();
			case 1 :
				return getCourier();
			case 2 :
				return getTrainer();
			case 3 :
				return getGym();
			case 4 :
				return getAccountSpam();
			case 5 :
				return getName();
			case 6 :
				return getNumber();
			case 7 :
				return getAccountClient();
			case 8 :
				return getDate();
			case 9 :
				return getAddress();
			case 10 :
				return getComment();
			case 11 :
				return getStatus() + "";
			case 12 :
				return getCost() + "";
			case 13 :
				return getTypeOfTrain() + "";
		}
		return "";
	}
	public int getLastDateId() {
		return lastDateId;
	}
	public void setLastDateId(int lastDateId) {
		this.lastDateId = lastDateId;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public double getDebt() {
		return debt;
	}
	public void setDebt(double debt) {
		this.debt = debt;
	}
	public int getNumberTrain() {
		return numberTrain;
	}
	public void setNumberTrain(int numberTrain) {
		this.numberTrain = numberTrain;
	}
	public int getCountOfTrain() {
		return countOfTrain;
	}
	public void setCountOfTrain(int countOfTrain) {
		this.countOfTrain = countOfTrain;
	}
	public boolean isClient() {
		return isClient;
	}
	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}
	public int getNumberCV() {
		return numberCV;
	}
	public void setNumberCV(int numberCV) {
		this.numberCV = numberCV;
	}
	public int getLogo() {
		return logo;
	}
	public void setLogo(int logo) {
		this.logo = logo;
	}
	public int getPrePay() {
		return prePay;
	}
	public void setPrePay(int prePay) {
		this.prePay = prePay;
	}
	public int getPR() {
		return PR;
	}
	public void setPR(int pR) {
		PR = pR;
	}
	public int getOpinion() {
		return opinion;
	}
	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}
	public String getDateCV() {
		return dateCV;
	}
	public void setDateCV(String dateCV) {
		this.dateCV = dateCV;
	}
}
