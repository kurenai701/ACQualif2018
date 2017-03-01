package AC2017Qualif;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Common {
	public final static int DEBUG = 0		;//DEBUG symbol
	
	
	// FileUtil
	public static final UtilFile FU = new UtilFile();


	public static final String ACBaseFolderPath = "C:\\ACFile\\H2017Qualif\\";
	public static final String ACFileFolderPath = ACBaseFolderPath+"PROBLEM\\";


	/////////////// ZIP
	public static final String OUTPUT_ZIP_FILE = ACFileFolderPath+"FolderSourceMEGEClemAlex.zip"; 
	public static final String OUTPUT_ZIP_FULLPROC_FILE_NAME = "FullSourceMEGEClemAlex.zip"; 

	public static final String SOURCE_FOLDER = ACBaseFolderPath+"src";
	
	/////////////////////////////////
	/////////////// INPUT ///////////
	/////////////////////////////////
	public static final String InputEncoding = "UTF-8";
	public static final Locale ScannerLocale = Locale.US;


	public static  String InputFileName = null;

	
	
	public static  String InputFilePath =null;// = ACFileFolderPath+InputFileName;

	
	public static final String InputFileNameUnitTest = InputFileName;//"example.in";
	public static final String InputFilePathUnitTest =  ACFileFolderPath+InputFileNameUnitTest;

	// ForInputVerification
	public static final String InputFileVerifFileName = "verifInputFromInputModel.txt";
	public static final String InputFileVerifPath = ACFileFolderPath+InputFileVerifFileName;	

	//Input of Full processing GeneratedFromOutput
	public static final String VerifInputFromOutputReadFileName = "verifInputFromReadOutput.txt";
	public static final String InputFileVerifPathFromOutputRead = ACFileFolderPath+VerifInputFromOutputReadFileName;

	/////////////////////////////////
	////////////// OUTPUT	/////////
	/////////////////////////////////
	public static final String OutputEncoding = "UTF-8";

	public static final String OutputGeneratedFileNameUnitTest = "UNITTESTOutputGenerated.txt";
	public static final String OutputGeneratedFullPathUnitTest = ACFileFolderPath+OutputGeneratedFileNameUnitTest;

	public static final String OutputGeneratedFileName = "OutputGenerated.txt";
	public static final String OutputGeneratedFullPath = ACFileFolderPath+OutputGeneratedFileName; 	



	// quand on aura généré le output generated, et qu'on veut le tester pour vérifier que c'est plus ou moins conforme avec l'input

	//	public static final String OutputTestFileName = "TESTEXEMPLEOUTPUT.txt";
	public static final String OutputTestFileName = "OutputGenerated.txt";
	public static final String OutputTestFileFullPath = ACFileFolderPath+OutputTestFileName;

	public static final String OutputTestFileNameUnitTest="TESTEXEMPLEOUTPUT.txt";
	public static final String OutputTestFileFullPathUnitTest= ACFileFolderPath+OutputTestFileNameUnitTest;


	// SAVE SERIALIZED SOLUTION
	public static final String SaveSerialFileName = "SolutionSerialized.ser";	


	public static final DateFormat FilePrefixdateFormat = new SimpleDateFormat("MMdd-HHmm");	
	//MMdd-hhmm
	public static String getTimeAsPrefixDateFormatString()
	{
		// Format Date For Prefix
		Date currentDate = new Date();		
		String prefixDate = Common.FilePrefixdateFormat.format(currentDate);
		return prefixDate;	
	}


	// Where Clause sur List
	/**
	 * @param list
	 * @param pred
	 * @return

	 *
	 */
	
	// C'est tellement bien LINQ... que recode quelque chose de similaire en Java :-)
	
	public static <T> List<T> Where(List<T> list, Predicate<T> pred)
	{
		List<T> resultList = 
				list.stream()
				.filter(pred)
				.collect(Collectors.toList());

		return resultList;		
	}


	public static <T> T First(List<T> list, Predicate<T> pred)
	{		
		List<T> resultList = Where(list, pred);
		if (resultList != null && resultList.size() > 0) 
		{
			T firstRes = resultList.get(0);
			return firstRes;
		}
		else
			return null;

	}


	public static <T> int Count(List<T> list, Predicate<T> pred)
	{		
		List<T> resultList = Where(list, pred);
		if (resultList == null)
			return 0;
		else
			return resultList.size();
	}

	
	
	

	public static <T> T DeepCopy(T objToCopy)
	{
		T newObj = null;
		
		try
		{
		newObj = (T) (UtilSerDes.DeserializeStreamToObject(
				UtilSerDes.SerializeObjectToStream(objToCopy)
				)); // on va supposer que le cast fonctionne
		}
		catch (Exception e)
		{
			Common.GeneralCatch(e, "DeepCopy");
		}

		return newObj;

	}
	
	public static void GeneralCatch(Exception e, String extraInfo)
	{
		System.out.println("Exception encountered  : " + extraInfo);
		e.printStackTrace();
	}
	
	



}
