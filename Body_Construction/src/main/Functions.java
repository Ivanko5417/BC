package main;

public class Functions {
	public static int getTypeOfTrain(String StringTypeOfTrain)
	{
		if(StringTypeOfTrain.equals("Сушка")) return 0;
		if(StringTypeOfTrain.equals("Масса")) return 1;
		return -1;
	}
	public static String getTypeOfTrain(int IntTypeOfTrain)
	{
		if(IntTypeOfTrain == 0) return "Сушка";
		if(IntTypeOfTrain == 1) return "Масса";
		return "НИЧО";
	}
	
	public static int getStatus(String Status)
	{
		if(Status.equals("Спам")) return 0;
		if(Status.equals("Звон")) return 1;
		if(Status.equals("Доставка")) return 2;
		if(Status.equals("Пробная")) return 3;
		if(Status.equals("Платная")) return 4;
		if(Status.equals("Слив")) return 10;
		return -1;
	}	
	public static String getStatus(int Status)
	{
		if(Status == 0) return "Спам";
		if(Status == 1) return "Звон";
		if(Status == 2) return "Доставка";
		if(Status == 3) return "Пробная";
		if(Status == 4) return "Платная";
		if(Status == 10) return "Слив";
		return "";
	}
}
