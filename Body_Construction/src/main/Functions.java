package main;

public class Functions {
	public static int getTypeOfTrain(String StringTypeOfTrain)
	{
		if(StringTypeOfTrain.equals("Сушка")) return Constants.TypesOfTrain.DRYING;
		if(StringTypeOfTrain.equals("Масса")) return Constants.TypesOfTrain.MASS;
		return -1;
	}
	public static String getTypeOfTrain(int IntTypeOfTrain)
	{
		if(IntTypeOfTrain == Constants.TypesOfTrain.DRYING) return "Сушка";
		if(IntTypeOfTrain == Constants.TypesOfTrain.MASS) return "Масса";
		return "НИЧО";
	}
	
	public static int getStatus(String Status)
	{
		if(Status.equals("Спам")) return Constants.TypesOfClient.SPAM;
		if(Status.equals("Звон")) return Constants.TypesOfClient.CALL;
		if(Status.equals("Доставка")) return Constants.TypesOfClient.DELIVERY;
		if(Status.equals("Пробная")) return Constants.TypesOfClient.TRIAL;
		if(Status.equals("Платная")) return Constants.TypesOfClient.PAY;
		if(Status.equals("Расписание")) return Constants.TypesOfClient.CLIENT;
		if(Status.equals("Слив")) return Constants.TypesOfClient.SINK;
		return -1;
	}	
	public static String getStatus(int Status)
	{
		if(Status == Constants.TypesOfClient.SPAM) return "Спам";
		if(Status == Constants.TypesOfClient.CALL) return "Звон";
		if(Status == Constants.TypesOfClient.DELIVERY) return "Доставка";
		if(Status == Constants.TypesOfClient.SUCCESSFUL_DELIVERY) return "Назначить пробную";
		if(Status == Constants.TypesOfClient.TRIAL) return "Пробная";
		if(Status == Constants.TypesOfClient.PAY) return "Платная";
		if(Status == Constants.TypesOfClient.CLIENT) return "Расписание";
		if(Status == Constants.TypesOfClient.SINK) return "Слив";
		return "";
	}
}
