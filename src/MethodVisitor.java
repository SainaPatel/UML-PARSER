import java.awt.Window.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.ConstructorDeclaration;

class MethodVisitor extends VoidVisitorAdapter {

	List<String> classorinterfaceused=new ArrayList<String>();
	List<String> classorinterfacetype=new ArrayList<String>();
	boolean isconnected=false;
	Parser p1=new Parser();
	Parser p=new Parser();
	String s;
	int firstclass=0;
	   int secondclass=0;
	   List varname=new ArrayList<String>();
	   
	   
	   int changed=0;
	   
	   ClassVisitor c=new ClassVisitor();
    @Override
    public void visit(MethodDeclaration n, Object arg){
    	changed=0;
    	if(arg!=null)
    	{
    		if(!p.Simplevar.isEmpty())
    		{
    		for(int i=0;i<varname.size();i++)
    		{
    			//char var=Simplevar.get(i);
    			
    			//System.out.println("varname   get"+p.Simplevar.get(i));
    	
    			if(n.getName().equalsIgnoreCase("get"+p.Simplevar.get(i))||n.getName().equalsIgnoreCase("set"+p.Simplevar.get(i)))
    			{
    				p.vartype.set(i,"public");
    				
    				changed=1;
    				//break;
    			}
    		}}
    		
    	if(changed==0)
    	{
    		
    		System.out.println("changed---"+changed);
    	boolean objectusedasparameter=false;
    	int location=0;
    	
    	int accessspecifier=n.getModifiers();
    	com.github.javaparser.ast.type.Type returntypes=n.getType();
    	
    	if(!n.getParameters().isEmpty())
    	{
    		String Parameter=n.getParameters().toString().replace("[", "").replace("]", "");
    		String param[]=Parameter.split(" ");
    	
    		for(int i=0;i<classorinterfacetype.size();i++)
    		{
    				if(param[0].equals(classorinterfaceused.get(i)))
    				{
    					objectusedasparameter=true;
    					location=i;
    				}
    		}
    		System.out.println(n.getName()+" "+n.getModifiers()+" "+accessspecifier);
    		if(objectusedasparameter)
    		{
    		
    			if(accessspecifier==1)
    			{
    				String s="\n"+arg+" : +"+n.getName()+"("+param[1]+" : "+param[0]+") : "+returntypes.toString();
    				
    				try {
    					p.append(s);
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    	
    				if(classorinterfacetype.get(location)=="false")
    				{
    					if(c.implementedorassociated.equals(classorinterfaceused.get(location))&&c.classimplementing.equals(arg.toString()))
    					{
    					
    					}else
    					{	
    						s="\ninterface "+classorinterfaceused.get(location)+" <.. "+arg.toString()+" : uses";
        					c.implementedorassociated=classorinterfaceused.get(location).toString();
        					c.classimplementing=arg.toString();
    					}
    				}
    				else
    				{
    					if(c.implementedorassociated.equals(classorinterfaceused.get(location))&&c.classimplementing.equals(arg.toString()))
    					{
    					
    					}else
    					{
    						s="\n"+classorinterfaceused.get(location)+" -- "+arg.toString();
    						c.implementedorassociated=classorinterfaceused.get(location).toString();
        					c.classimplementing=arg.toString();
    					}
    				}
    				try {
    					p.append(s);
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    				
    				
				}
    		}else{
    				if(n.getName().equals("main")&&n.getModifiers()==9)
					{
    					if(n.getBody()!=null)
    					{
    						String body=n.getBody().toStringWithoutComments();
			        	  
    						int j;
					
    							for(j=0;j<classorinterfaceused.size();j++)
    							{
    								if(body.contains(classorinterfaceused.get(j))&&classorinterfacetype.get(j).equals("false"))
    								{
    									s="\ninterface "+classorinterfaceused.get(j)+" <.. "+arg.toString();
    									break;
    								}
    								else if(body.contains(classorinterfaceused.get(j))&&classorinterfacetype.get(j).equals("true"))
    								{
							//s="\n"+classorinterfaceused.get(j)+" <-- "+arg.toString();
    								}	
    							}
						try {
	    					p.append(s);
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
    			
    				} 
    		}else if(accessspecifier==1)
    		{
    			String s="\n"+arg+" : +"+n.getName()+"("+param[1]+" : "+param[0]+") : "+returntypes.toString();
				
				try {
					p.append(s);
				} catch (IOException e) {
					e.printStackTrace();
				}	
    		}
    				
    	}
    		
   }else
    	{
    		if(accessspecifier==1)
			{
			
				String s="\n"+arg+" : +"+n.getName()+"() : "+returntypes.toString();
				Parser p=new Parser();
				try {
					p.append(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
			}
    	}
    	}}
    }
    
    
    public void visit(FieldDeclaration n, Object arg) {
    	if(arg!=null)
    	{
    	  
    		varname=n.getVariables();
    	    
    	    com.github.javaparser.ast.type.Type types=n.getType();
    	    	    
    	    int mod=n.getModifiers();
	    	int collection=0;
	    	int array=0;
	    	int association=0;
	    	
	    	if(mod==1||mod==2)
	    	{
	    	for(int i=0;i<varname.size();i++)
	    	{
	    				for(int j=0;j<classorinterfaceused.size();j++)
	    				{
	    						    					
	    				if(types.toString().equals("Collection<"+classorinterfaceused.get(j)+">"))
	    				{
	    					//System.out.println("collection");
	    					p1.collectionclasses.add(arg+" "+classorinterfaceused.get(j));
	    					collection=1;
	    					break;
	    				}else if(types.toString().equals(classorinterfaceused.get(j)+"[]"))
	    						{
	    					//System.out.println("array");
	    					p1.arrayclasses.add(arg+" "+classorinterfaceused.get(j));
	    					array=1;
	    					break;
	    						}
	    				else if(types.toString().equals(classorinterfaceused.get(j)))
	    				{
	    					//System.out.println("variable");
	    					p1.associationclasses.add(arg+" "+classorinterfaceused.get(j));	
	    					association=1;
	    					break;
	    				}
	    				}
	    				if(collection!=1&&array!=1&&association!=1)
	    				{
	    					if(mod==1)
	    	    			{
	    	    			
	    				String s="\n"+arg+" : + "+varname.get(i)+" : "+types;
    				
	    				Parser p=new Parser();
	    				try {
	    					p.append(s);
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
	    				}else if(mod==2)
	    	    		{
	    					System.out.println("Simplevar in methodvisitor");
		    				p.Simplevar.add(varname.get(i).toString());
		    				System.out.println("in mod2 displaying content-------------------"+p.Simplevar.get(i));
		    				p.vartype.add("private");
		    				p.rettypeofprivate.add(types.toString());
		    				p.classnameofprivate.add(arg.toString());
		    				//String s="\n"+arg+" : - "+varname.get(i)+" : "+types;
	    	    		
		    					    					
	    				}
	    			}
	    	}
	    	}
    	    	
    	    		}
	    			
	    		}
		    
	    	
	    	
	    
   
   //super.visit(n, arg);
	
    
public void visit(ConstructorDeclaration n, Object arg) {

if(arg!=null)
{
	boolean objectusedasparameter=false;
	int location=0;
	
	int accessspecifier=n.getModifiers();
	
	if(!n.getParameters().isEmpty())
	{
		String Parameter=n.getParameters().toString().replace("[", "").replace("]", "");
		String param[]=Parameter.split(" ");
	
	
		for(int i=0;i<classorinterfacetype.size();i++)
		{
				if(param[0].equals(classorinterfaceused.get(i)))
				{
					objectusedasparameter=true;
					location=i;
				}
		}
	    	
		if(objectusedasparameter)
		{
		
			if(accessspecifier==1)
			{
				
				String s="\n"+arg+" : +"+n.getName()+"("+param[1]+" : "+param[0]+")";
				Parser p=new Parser();
				try {
					p.append(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				if(classorinterfacetype.get(location)=="false")
				{
					s="\ninterface "+classorinterfaceused.get(location)+" <.. "+arg.toString()+" : uses";
				}
				else
				{
					s="\n"+classorinterfaceused.get(location)+"--"+arg.toString();
				}
				try {
					p.append(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
			
			}
		
		}else
	{
			if(accessspecifier==1)
			{    		String s="\n"+arg+" : + "+n.getName()+"("+Parameter+")";
					Parser p=new Parser();
					try {
						p.append(s);
					} catch (IOException e) {
						e.printStackTrace();
						}
			}
	}

	}
	else
	{
		if(accessspecifier==1)
		{
		
			String s="\n"+arg+" : +"+n.getName()+"()";
			Parser p=new Parser();
			try {
				p.append(s);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}



}
}  
    
}
    
    
    
    
