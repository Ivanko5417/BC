package main;
import com.sun.org.apache.xerces.internal.util.URI;


public class Client implements Comparable<Client> {
	private int id,
	numberDates;//номер перезвона, больше 3-х в слив 
	private String source, // откуда спам 
	accountSpam, // ссылка на акк, с которого был спам
	name, // имя человека, которого отспамили
	spam, // имя спамщика
	call, // имя звонильщика
	trainer, // имя тренера 
	number, // номер телефона  
	accountClient,// ссылка на акк клиента 
	date, //дата спама
	address, // адресс клиента
	comment, // комментарий
	gym, // зал, в кот. хочет заниматься
	courier, // имя доставщика
	status,
	dateCall; // статус клиента(на обзвоне, готов к пробной и т.д.)
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
	public String[] toStringSpam()
	{
		String[] s = {
				call,
				courier,
				trainer,
				gym,
				accountSpam,
				name,
				number,
				accountClient,
				date,
				address,
				comment,
				status
		};
		
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
		return getDateCall()+" " + getDate();
		
	}
	public int compareTo(Client o) 
	{ 
	   /* if(this.toString().compareTo(o.toString()) > 0)
	    {
	    	return -1;
	    }   
	    else if(this.toString().compareTo(o.toString()) <  0)
	    {
	      return 1;
	    }
	    return 0;  */
		if(this.status.equals("10")) return 99999999; else
			if(o.status.equals("10")) return -99999999; else
		return this.toString().compareTo(o.toString());
	  }

    @Override
    public int hashCode() {
        return id;
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
        if (this.id != other.id) {
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
	
}
