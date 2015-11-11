import java.io.BufferedWriter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import net.sourceforge.plantuml.SourceStringReader;

public class Parser {
	 static String output;
	 static ClassVisitor c=new ClassVisitor();
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
        
    	// createFile();
         output="@startuml";
         output+="\nskinparam classAttributeIconSize 0";
        
         
         
    	File folder = new File(args[0]);
        File[] listOfFiles = folder.listFiles();
       
        int arrsize=listOfFiles.length; 
        CompilationUnit[] cu=new CompilationUnit[arrsize];
        for (int i = 0; i < listOfFiles.length; i++)
        {
        	if(listOfFiles[i].getName().endsWith(".java"))
        	{
    	FileInputStream in = new FileInputStream(listOfFiles[i]);
    	 try
    	 {
    		 cu[i] = JavaParser.parse(in);
    	 }
    	 catch(Exception e)
    	 {
    		 
    	 }
    	 finally{
    		 in.close();
    		 
             c.visit(cu[i],null);
             
             
    	 }
        	}
        }
        	for(int i=0;i<listOfFiles.length;i++)
        	{
        		if(listOfFiles[i].getName().endsWith(".java"))
            	{
        		//ClassVisitor c=new ClassVisitor();
                c.visit(cu[i],"2");
            	}
        	}
        	
        	
      checkmultiplicity();
      displayprivatevariables();
        
        output+="\n@enduml";
        //output.close();
        
        createImage(args);
    }
  static List<String> classnameofprivate=new ArrayList<String>(); 
 static List<String> Simplevar=new ArrayList<String>();
 static List<String> vartype=new ArrayList<String>();
 static List<String> rettypeofprivate=new ArrayList<String>();
 
 static String s;
 
 
 
public static void displayprivatevariables()
{
	
	System.out.println("Simplevar"+Simplevar.size());
		for(int i=0;i<Simplevar.size();i++)
		{
			System.out.println("Simplevarinside");
			
			if(vartype.get(i).equals("private"))
			{
			s="\n"+classnameofprivate.get(i)+" : - "+Simplevar.get(i)+" : "+rettypeofprivate.get(i);
			}
			else
			{
				System.out.println("Classnameofprivate"+classnameofprivate.get(i));
			s="\n"+classnameofprivate.get(i)+" : + "+Simplevar.get(i)+" : "+rettypeofprivate.get(i);
			}
			
			
			output+=s;
		
			
		}
		for(int i=0;i<Simplevar.size();i++)
		{
			classnameofprivate.remove(i);
			Simplevar.remove(i);	
			vartype.remove(i);
			rettypeofprivate.remove(i);
		}
	
}
   

public static void createImage(String args[])
{
	SourceStringReader reader = new SourceStringReader(output);
	// Write the first image to "png"

	OutputStream png = null;
	try {
		png = new FileOutputStream(args[1]+"output.png");
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	;

	try {
		String desc = reader.generateImage(png);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
    
    
    
    
/*public static void createFile()
{
	
		try {
				//output = new BufferedWriter(new OutputStreamWriter(
					//new FileOutputStream("/users/sainapatel/Desktop/saina.txt"), "utf-8"));
				//output.write("hello");
			
			//output+=
				
			} catch (IOException ex) {

			}
		
}*/
static List<String> collectionclasses=new ArrayList<String>();
static List<String> arrayclasses=new ArrayList<String>();
static List<String> associationclasses=new ArrayList<String>();
public static void checkmultiplicity() throws IOException
{
	
	//collection
	int i,j;
	
	

	//System.out.println("-----------Collection Classes--------");
	for(i=0;i<collectionclasses.size();i++)
	{
		//System.out.println(collectionclasses.get(i));
	}
	
	//System.out.println("-----------Association Classes--------");
	for(i=0;i<associationclasses.size();i++)
	{
		//System.out.println(associationclasses.get(i));
	}
	
	
	
	
	
	
	
	LinkedList<Integer> done=new LinkedList<Integer>();
	for(i=0;i<collectionclasses.size();i++)
	{
		int last=0;
		String compareclasses[]=null;
		int completed = 0;
		String classes[]=collectionclasses.get(i).split(" ");
		for(j=i+1;j<collectionclasses.size();j++)
		{
			completed=0;
			 compareclasses=collectionclasses.get(j).split(" ");
		
			if((classes[0].equals(compareclasses[1]))&&(classes[1].equals(compareclasses[0])))
			{
				done.add(j);
				String s="\n"+classes[0]+"\"*\" - \"*\""+classes[1];
				output+=s;
			//	collectionclasses.remove(i);
				//collectionclasses.remove(j);
				completed=1;
				break;
			}
			
		}
		
	}
		
	//System.out.println("-----------Association Classes- after collection-------");
	for(i=0;i<associationclasses.size();i++)
	{
		//System.out.println(associationclasses.get(i));
	}
		
	
	
	
	
	
	
	
	
	//System.out.println("-----------Association Classes- after array-------");
	for(i=0;i<associationclasses.size();i++)
	{
		//System.out.println(associationclasses.get(i));
	}
	//associations
	
	for(i=0;i<associationclasses.size();i++)
	{
		int completed = 0;
		String classes[]=associationclasses.get(i).split(" ");
		//System.out.println("associationclasses outsde"+classes[0]+" "+classes[1]);
		for(j=i+1;j<associationclasses.size();j++)
		{
			
			completed=0;
			//List<integer> done=new ArrayList<int>();
			String compareclasses[]=associationclasses.get(j).split(" ");
			//System.out.println("associationclasses inside"+compareclasses[0]+" "+compareclasses[1]);
		//System.out.println(compareclasses[1]);
			if((classes[0].equals(compareclasses[1]))&&(classes[1].equals(compareclasses[0])))
			{
				
				String s="\n"+classes[0]+"--"+classes[1];
				output+=s;
				completed=1;
				//System.out.println(associationclasses.get(j));
				associationclasses.remove(i);
				associationclasses.remove(j-1);
				//System.out.println("--------------"+associationclasses.get(i)+"  "+associationclasses.size());
				break;
			}
		}
				
	}
		
	//System.out.println("-----------Association Classes- after association-------");
	for(i=0;i<associationclasses.size();i++)
	{
		//System.out.println(associationclasses.get(i));
	}
	

	
	int removed=0;
	for(i=0;i<collectionclasses.size();i++)
	{
	
		String classes[]=collectionclasses.get(i).split(" ");
	
		//System.out.println("class"+classes[0]+" "+classes[1]);
		for(j=0;j<associationclasses.size();j++)
		{
			
		//	System.out.println(associationclasses.get(j));
			String compareclasses[]=associationclasses.get(j).split(" ");
			//System.out.println("compareclass"+compareclasses[0]+" "+compareclasses[1]);
			if((classes[0].equals(compareclasses[1]))&&(classes[1].equals(compareclasses[0])))
			{
				//System.out.println("After Removing"+associationclasses.get(j));

				String s="\n"+classes[0]+" \"1\" - \"*\""+classes[1];
				output+=s;
				collectionclasses.remove(i);
				
				associationclasses.remove(j);
				
			//	System.out.println("After Removing"+associationclasses.get(j)+"  "+associationclasses.size()+"-----"+i);
				removed=1;
				
				break;
			}
			
			
		}
		if(removed==1)
		{
			i--;
		}
		
		
	}	
	
	
	
	
	if(collectionclasses.size()>0)
	{
		for(i=0;i<collectionclasses.size();i++)
		{
		String classes[]=collectionclasses.get(i).split(" ");
		
			String s="\n"+classes[0]+" - \"*\""+classes[1];
			output+=s;
			collectionclasses.remove(i);
			
		}
	}
	
	
	
	
	if(associationclasses.size()>0)
	{
		
		for(i=0;i<associationclasses.size();i++)
		{
		String classes[]=associationclasses.get(i).split(" ");
		
			String s="\n"+classes[0]+" -- "+classes[1];
			output+=s;
			associationclasses.remove(i);
		}
	}
	
}

public void append(String s) throws IOException
{
	output+=s;	
}
}