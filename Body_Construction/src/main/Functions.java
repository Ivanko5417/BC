package main;

public class Functions {
	public static int getTypeOfTrain(String StringTypeOfTrain)
	{
		if(StringTypeOfTrain.equals("�����")) return Constants.TypesOfTrain.DRYING;
		if(StringTypeOfTrain.equals("�����")) return Constants.TypesOfTrain.MASS;
		return -1;
	}
	public static String getTypeOfTrain(int IntTypeOfTrain)
	{
		if(IntTypeOfTrain == Constants.TypesOfTrain.DRYING) return "�����";
		if(IntTypeOfTrain == Constants.TypesOfTrain.MASS) return "�����";
		return "����";
	}
	
	public static int getStatus(String Status)
	{
		if(Status.equals("����")) return Constants.TypesOfClient.SPAM;
		if(Status.equals("����")) return Constants.TypesOfClient.CALL;
		if(Status.equals("��������")) return Constants.TypesOfClient.DELIVERY;
		if(Status.equals("�������")) return Constants.TypesOfClient.TRIAL;
		if(Status.equals("�������")) return Constants.TypesOfClient.PAY;
		if(Status.equals("����������")) return Constants.TypesOfClient.CLIENT;
		if(Status.equals("����")) return Constants.TypesOfClient.SINK;
		return -1;
	}	
	public static String getStatus(int Status)
	{
		if(Status == Constants.TypesOfClient.SPAM) return "����";
		if(Status == Constants.TypesOfClient.CALL) return "����";
		if(Status == Constants.TypesOfClient.DELIVERY) return "��������";
		if(Status == Constants.TypesOfClient.SUCCESSFUL_DELIVERY) return "��������� �������";
		if(Status == Constants.TypesOfClient.TRIAL) return "�������";
		if(Status == Constants.TypesOfClient.PAY) return "�������";
		if(Status == Constants.TypesOfClient.CLIENT) return "����������";
		if(Status == Constants.TypesOfClient.SINK) return "����";
		return "";
	}
}
