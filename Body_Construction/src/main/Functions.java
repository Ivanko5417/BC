package main;

public class Functions {
	public static int getTypeOfTrain(String StringTypeOfTrain)
	{
		if(StringTypeOfTrain.equals("�����")) return 0;
		if(StringTypeOfTrain.equals("�����")) return 1;
		return -1;
	}
	public static String getTypeOfTrain(int IntTypeOfTrain)
	{
		if(IntTypeOfTrain == 0) return "�����";
		if(IntTypeOfTrain == 1) return "�����";
		return "����";
	}
	
	public static int getStatus(String Status)
	{
		if(Status.equals("����")) return 0;
		if(Status.equals("����")) return 1;
		if(Status.equals("��������")) return 2;
		if(Status.equals("�������")) return 3;
		if(Status.equals("�������")) return 4;
		if(Status.equals("����")) return 10;
		return -1;
	}	
	public static String getStatus(int Status)
	{
		if(Status == 0) return "����";
		if(Status == 1) return "����";
		if(Status == 2) return "��������";
		if(Status == 3) return "�������";
		if(Status == 4) return "�������";
		if(Status == 10) return "����";
		return "";
	}
}
