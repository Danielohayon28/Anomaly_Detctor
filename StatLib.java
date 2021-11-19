package test;

import javax.sql.rowset.spi.XmlReader;

public class StatLib {

	

	// simple average
	public static float avg(float[] x){
	float sum =0;
	for (int i = 0;i<x.length;i++)
	{
	sum+=x[i];
	}
	return (sum/x.length);
	}
	// returns the variance of X and Y
	public static float var(float[] x){
		float u = avg(x);
		float sum=0;
		for (int i = 0;i<x.length;i++)
		{
		sum+=x[i] * x[i]; 
		}
		sum= sum/x.length;
		sum-= (u*u);
		return sum;
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y){

		float avgX = avg(x);
		float sum=0;
		float avgY = avg(y);
		for (int i = 0;i<x.length;i++)
		    {
		    sum+= (x[i] - avgX ) * (y[i] - avgY); 
		 	}
	

		return (sum/x.length);
	}


	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y){
		
		return (cov(x,y) /( (float)Math.sqrt(var(x)*var(y))));
	
	}

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points){
		float[] X = new float[points.length];
		float[] Y = new float[points.length];
		for (int i =0;i<points.length;i++)
		{
			X[i]=points[i].x;
			Y[i]=points[i].y;
		}
		float a = cov(X,Y) / var(X);
		float b = avg(Y) - (a*avg(X));
		Line l = new Line(a, b);
		return l;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p,Point[] points){
		
		Line l = linear_reg(points);
		float result = l.f(p.x) - p.y;
		if (result > 0)
		    return result;
	
		else
			return -result;
	
	}

	// returns the deviation between point p and the line
	public static float dev(Point p,Line l){
		float result = l.f(p.x) - p.y;
		if (result > 0)
		    return result;
	
		else
			return -result;
	}
	
	
}
