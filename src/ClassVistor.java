import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

class ClassVisitor extends VoidVisitorAdapter {
    
	List<String> classorinterfaceused=new ArrayList<String>();
	List<String> classorinterfacetype=new ArrayList<String>();
    Parser p=new Parser();
    String implementedorassociated="false", classimplementing="false" ;
    
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
       
    	if(arg!=null)
    	{
    		//System.out.println("Simplevar"+Simplevar.size());
    		implementedorassociated="false";
    		classimplementing="false"; 
    		
    	String s=null;
       
    	if(n.isInterface()==true)
    	{
    	/*	s="\n() '"+n.getName()+"'";
    		try {
	   			p.append(s);
	   		} catch (IOException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		}*/
    	}
    	else
    	{
    		//System.out.println(n.getExtends());
    	if(n.getExtends()!=null)
        {
    		System.out.println("extendss----------------------------"+n.getExtends());
    		System.out.println("extendinggg----------------------------"+n.getName());
    		String inherited=n.getExtends().toString().replace("[", "").replace("]", "");
    			System.out.println("helloloooooooooooooooooooooooo"+n.getName());
        	s="\n"+inherited+" <|-- "+n.getName();
        
        	try {
       			p.append(s);
       		} catch (IOException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
        	
        }
    	
    	
    	if(n.getImplements()!=null)
    	{
    		 List<ClassOrInterfaceType> implementList=n.getImplements();
    		for(int i=0;i<implementList.size();i++)
    		{
    			s="\ninterface "+implementList.get(i)+"<|.. "+n.getName();
    			try {
    	   			p.append(s);
    	   		} catch (IOException e) {
    	   			// TODO Auto-generated catch block
    	   			e.printStackTrace();
    	   		}
    		}
    	}
    	
       
        if(n.getMembers().isEmpty()==false)
        {	
        	//MethodDeclaration method=n;
        	
        	
        			MethodVisitor m=new MethodVisitor();
        			m.classorinterfacetype=classorinterfacetype;
        			m.classorinterfaceused=classorinterfaceused;
          			m.visit(n, n.getName());
          			
          		//	p.displayprivatevariables();
        				
        }
        else
        {
     	
        } 
        
    	}
    	}
    	else
    	{
    		//System.out.println("in else of class"+n.getName().toString());
    		classorinterfaceused.add(n.getName());
    		if(n.isInterface())
    		{
    			classorinterfacetype.add("false");
    		}
    		else
    		{
    			classorinterfacetype.add("true");
    		}
    		
    		
    	}
      
    
    	 }
    }
