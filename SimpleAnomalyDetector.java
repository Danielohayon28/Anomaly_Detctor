// Created by Daniel Ohayon //  
package test;
import java.util.List; // import just the List interface
import java.util.ArrayList; 
import java.text.ParseException;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {

	private ArrayList<CorrelatedFeatures> cor;
	@Override
	public void learnNormal(TimeSeries ts) {
		cor = new ArrayList<CorrelatedFeatures>();
		int row =-1; 
		int col =-1;
		float correllation;
		int N = ts.getArray().get(0).length;
		for (int i =0;i <N;i++)
		{
			float m=0;
			int c =-1;
			for (int j = i+1 ;j <N;j++) 
			{
				if( ((Math.abs(StatLib.pearson(ts.getColarray(i) , ts.getColarray(j))) > 0.9) &&(Math.abs(StatLib.pearson(ts.getColarray(i) , ts.getColarray(j))) > m ))){
					m= Math.abs(StatLib.pearson(ts.getColarray(i) , ts.getColarray(j))); 
					c=j;
				}
			}
			if(c!= -1)
			{
				row = i;
				col=c; 
				correllation =m;
				String a = ts.getArray().get(0)[row]; // Feature 1
				String b = ts.getArray().get(0)[col]; // Feature 2
				float[] X =  ts.getColarray(row); //get the array of the index (X)
				float[] Y = ts.getColarray(col); // get the array of the index (Y)
				Point[] ps = new Point[X.length]; 
				for (int k=0; k<X.length;k++) // Create array of points
				{
					ps[k] = new Point(X[k],Y[k]);
				}
				Line l = StatLib.linear_reg(ps); // create a new Line by the arrays of x + y 
				float Max =0;
				for (int t =0 ; t< ps.length; t++)
				{
					if (StatLib.dev(ps[t],l) > Max){ // Find the max dist
						Max = StatLib.dev(ps[t],l);
					}
				}
				cor.add (new CorrelatedFeatures(a, b, correllation, l,(float)(Max *1.1))); // add a new object(corerelatedFeatures) to the list 
			}
		}
	}
@Override
public List<AnomalyReport> detect(TimeSeries ts) {
	ArrayList<AnomalyReport> ls = new ArrayList<>();
	for(int i =0; i< cor.size();i++)
	{
		float[] X = ts.getColarray(cor.get(i).feature1); //get the array of the index (X)
		float[] Y = ts.getColarray(cor.get(i).feature2); //get the array of the index (Y)
		for(int j=0;j<X.length  -1 ;j++)
			{
				Point p = new Point(X[j],Y[j]); // Create a new point for check 
				if (StatLib.dev(p, cor.get(i).lin_reg) > cor.get(i).threshold) // Check if it's detect 
				{
					String s1 =new String (cor.get(i).feature1+"-"+cor.get(i).feature2); // e.g: 'A-C' 
					ls.add(new AnomalyReport(s1,(long)j+1)); // Add a new Detect
				}
			} 
	}
	return ls;	// return the list of detect_s
}
	 public List<CorrelatedFeatures> getNormalModel(){ // Return the CorrelatedFeatures Array
	 	return this.cor;
	}
}
