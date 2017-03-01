package AC2017Qualif;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UtilSerDes {
	

	public static ByteArrayOutputStream SerializeObjectToStream(Object objToCopy)
	{		
		ObjectOutputStream oos = null;
		ByteArrayOutputStream bos = null;

		try
		{
			bos = new ByteArrayOutputStream(); 
			oos = new ObjectOutputStream(bos); 
			// serialize and pass the object
			try
			{
				oos.writeObject(objToCopy);   					
			}			
			catch (Exception e)
			{
				Common.GeneralCatch(e, "SerializeObjectToStream, oos.writeObject(objToCopy);");
				return null;
			}
		}
		catch (Exception e)
		{
			Common.GeneralCatch(e, "SerializeObjectToStream");
			return null;
		}		
		finally
		{
			if (oos != null)
			{
				try
				{
					oos.close();
				} 
				catch (Exception e)
				{
					Common.GeneralCatch(e, "SerializeObjectToStream oos.close");
				}    			
			}
		}				
		return bos;
	}
	
	
	
	public static Object DeserializeStreamToObject(ByteArrayOutputStream bos)
	{
		Object newObj = null;
		
		ObjectInputStream ois = null;		
		ByteArrayInputStream bin = null;
		
		try
		{
			bin = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bin); 

			// deserialize by reading
			try
			{
				newObj = ois.readObject(); // On va supposer qu'il n'y aura pas de problem de cast
			}
			catch(Exception e)
			{
				Common.GeneralCatch(e, "DeserializeStreamToObject, ois.readObject();");
			}
			finally
			{		
				try
				{
					ois.close();
				}
				catch (Exception e)
				{
					Common.GeneralCatch(e, "DeserializeStreamToObject, ois.close();");
				}   

			}
		}
		catch (Exception e)
		{
			Common.GeneralCatch(e, "DeserializeStreamToObject");
		}
		return newObj;
	}
	
	
}
