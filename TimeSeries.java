package test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List; // import just the List interface
import java.util.ArrayList; 
public class TimeSeries {
	
	private ArrayList<String[]> array;
	public TimeSeries(String csvFileName) {
		array = new ArrayList<String[]>();
		File file = new File(csvFileName);
		try{
            // -read from filePooped with Scanner class
            Scanner inputStream = new Scanner(file);
            // hashNext() loops line-by-line
            while(inputStream.hasNext()){
                //read single line, put in string
                String data = inputStream.next();
				String[] data2= data.split(",");
               array.add(data2);
            }
            // after loop, close scanner
            inputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
}
public List<Float> getDataCol(String s)
{
	List<Float> a= new ArrayList<>();
	String[] first = array.get(0);
	int index = 0;
	for(int i=0; i<first.length;i++)
	{
		if(first[i] == s)
		{
			index = i;
		}
	}
	for (int i =1; i<size()-1;i++){
		a.add(Float.parseFloat(array.get(i)[index]));
	}
	return a;
}
public float[] getColarray(String s)
{
float[] arr = new float[size()-1];
String[] first = array.get(0);
int index = 0;
for(int i=0; i<first.length;i++)
	{
		if(first[i].equals(s))
		{
			index = i;
		}
	}
for (int i =0 ; i<arr.length;i++)
{
	arr[i]= getFeature(i+1, index);
}
return arr;
}

public float[] getColarray(int s)
{
float[] arr = new float[size()-1];
for (int i =0 ; i<arr.length;i++)
{
	arr[i]= getFeature(i+1, s);
}
return arr;
}

public float getFeature(int x, int y){
	String[] first = array.get(x);
	float a = Float.parseFloat(first[y]);
	return a;
}
public ArrayList<String[]> getArray() {
	return array;
}
public int size() {
	return array.size();
}
}