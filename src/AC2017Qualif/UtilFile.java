package AC2017Qualif;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class UtilFile {

	
	public Scanner ScannerFile(String fileFullpath)
	{
		try
		{
			// Open Text File
			Scanner fileScan = new Scanner(new FileReader(fileFullpath));
			System.out.println("File Found : " + fileFullpath);
			fileScan.useLocale(Common.ScannerLocale);
			
			return fileScan;
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("File not found : " + fileFullpath);
			fnfe.printStackTrace();
			return null;
		}	
		catch (Exception e)
		{
			Common.GeneralCatch(e, "ScannerFile");
			return null;
		}
	}
	
	
	public PrintWriter CreateWriterFile (String fileFullpath, String encoding) // default parameter like in C# ?
	{
		PrintWriter writer;
		try {
			
			String targetEncoding;
			// put default encoding if null			
			targetEncoding = ((encoding == null || encoding.isEmpty()) ? "UTF-8" : encoding);
			
			
			writer = new PrintWriter(fileFullpath, encoding);
			System.out.println("Writer Created " + fileFullpath + " With encoding " + targetEncoding);
			return writer;
		}
		catch(UnsupportedEncodingException uee)
		{
			System.out.println(" encountered unsupportedEncodingException");
			uee.printStackTrace();
			return null;
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println(" encountered FileNotFoundException");
			fnfe.printStackTrace();
			return null;
		}
		catch (Exception e)
		{
			Common.GeneralCatch(e, "CreateWriterFile");
			return null;
		}
	}
	
	

	public void CreateDir(String DirectoryFullPathName)
	{	
		File file = new File(DirectoryFullPathName);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory " + DirectoryFullPathName + " is created!");
			} else {
				System.out.println("Failed to create directory! " + DirectoryFullPathName);
			}
		}
	}
	


	
	
	
	// TODO For later, refactor and use the new UtilSerDes for the BufferedSteam
	
	// all objects must implement Serializable
	public void SerializeObjectToFile (Object objectToSave, String fileFullpath)
	{
	
		try{			
	//		System.out.println("Serializing Object " + objectToSave.toString());
	           
			//use buffering
			OutputStream file = new FileOutputStream(fileFullpath);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try{
				output.writeObject(objectToSave);
			}
			catch (FileNotFoundException fnfe)
			{
				System.out.println(" encountered FileNotFoundException");
				fnfe.printStackTrace();
				return;
			}
			catch (IOException ioe)
			{
				System.out.println(" encountered IOException");
				ioe.printStackTrace();
				return;
			}
			catch (Exception e)
			{
				System.out.println("encountered Exception");
				e.printStackTrace();
				return;
			}
			finally{
				if (output != null)
					output.close();
	//			System.out.println("Finished Serializing Object " + objectToSave.toString());
		           
			}
		}
		catch (Exception e)
		{
			System.out.println("encountered Exception");
			e.printStackTrace();
			return;
		}				
	}
	
	
	// all objects must implement Serializable
	public Object DeserializeFileToObject(String fileFullpath)
	{
	      try
	        {	    	  

				System.out.println("Deserializing Object " + fileFullpath);
		           	    	  
	            FileInputStream fis = new FileInputStream(fileFullpath);
	          
	            ObjectInputStream iis = new ObjectInputStream(fis);
	            Object obj = null;
	            try
	            {
	            	obj = iis.readObject();
	            }
	            catch(Exception e)
	            {
	            	System.out.println("Exception while reading object iis.readObject()");
	            	System.out.println(e.getMessage());
		            e.printStackTrace();
	            }
	            finally
	            {
	            	if (iis != null)
	            		iis.close();
	            }
	            System.out.println("Done");
	            
	            return obj;
	        }
	      	catch(Exception e)
	        {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	            return null;
	        }
	      
	}
	
	
	
	
	
	
	
	
}
