package AC2018Qualif;

import java.util.Scanner;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;

public class FullProcess {

	/**
	 * @param args
	 *            Main function
	 */
	public static void main(String[] args) {

		boolean RELOAD = true;
	//	 Common.InputFileName = "kittens.in";  Problem.smallOffset = 150;//100=> 1024782; 70 => 1024619 50 => 1024364 0 => 1021680
	//	 Common.InputFileName = "videos_worth_spreading.in";  Problem.smallOffset = 20; //20 => 608442
	//	 Common.InputFileName = "trending_today.in"; Problem.smallOffset =20; //RELOAD = false;//20 => 499984
	//	Common.InputFileName = "me_at_the_zoo.in";Problem.smallOffset = 30;  RELOAD = false; // 10 => 507906
		
		Common.InputFileName = "example.in";

	
		Common.OutputGeneratedFileName = Common.InputFileName + ".out.txt";
		Common.InputFilePath = Common.ACFileFolderPath + Common.InputFileName;
		Common.OutputGeneratedFullPath = Common.ACFileFolderPath + Common.OutputGeneratedFileName;

		// PARAMETRES !!

		//// Initialization of our Tool Classes
		AlgoInputToOutput Alg = null;// new AlgoInputToOutput();

		// ****** Get Problem Model from file

		ReadInput ri = new ReadInput();
		Scanner scanInput = ri.ScannerInputFile(Common.InputFilePath);
		Problem pbMod = ri.ProcessReadInputToModel(scanInput);

		// TODO : find solution

		// ****** Process Algorithm to find Solution
		SplittableRandom rand = new SplittableRandom(42);//System.currentTimeMillis());
		Solution sol;

		if (!RELOAD) {
			Solution startSol = new Solution(pbMod);
			MutableDouble BestScoreOut = new MutableDouble(0);
			BestSolutionSynchro BestSolSynch = new BestSolutionSynchro(startSol);
			Alg = new AlgoInputToOutput(pbMod, rand, startSol, BestSolSynch, 0, 50);

			sol = Alg.AlgoInit(pbMod, rand);

			// return;//TODO

			ProcessAllBackupOfSolutionToFolder(sol);
		} else {
			// sol =
			// (Solution)(Common.FU.DeserializeFileToObject(Common.ACFileFolderPath
			// + Common.InputFileName +
			// "_BestSolutionInProcess.ser"));//0000671212_0330-2224\\SolutionSerialized.ser"
			// ));

			// REPROCESS INIT PROBLEM
			Scanner scanIn = ri.ScannerInputFile(Common.InputFilePath);
			pbMod = ri.ProcessReadInputToModel(scanIn);

			// GET LATEST SOLUTION FROM OUTPUT
			OutputBackToSol outputToSol = new OutputBackToSol();
			Scanner scanOutput = outputToSol.ScannerOutputFile(Common.OutputGeneratedFullPath);
			sol = outputToSol.ProcessReadOutputBackToSolution(scanOutput, pbMod);

			Sys.disp("recreated solution from output");
			// sol.pb = pbMod;
		}

		// Todo : do DeepCopy if needed

		Sys.disp(sol.GetScore());

		// ****** Solution Improver ATTENTION AUX PARAMETRES;
		BestSolutionSynchro BestSolSynch = new BestSolutionSynchro(sol);
		
		while (true) {
			int NIT = 50;
			int NPROC = 1;

			Thread[] thL = new Thread[NPROC];
			for (int i = 0; i < NPROC; i++) {
				SplittableRandom indivrand = new SplittableRandom(rand.nextInt());
				Solution indivSol = BestSolSynch.gestBestSolution();

				indivSol.pb = pbMod;

				Runnable indivAlg = new AlgoInputToOutput(pbMod, indivrand, indivSol, BestSolSynch, i, NIT);
				Thread thread = new Thread(indivAlg);
				thread.start();
				thL[i] = thread;

			}
			boolean finished = false;
			while (!finished) {
				finished = true;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (Thread t : thL) {
					if (t.isAlive()) {
						finished = false;
					}

				}

			}
		}

	}

	/*
	public static void VerifySerializeDeserializeSolution(Solution sol) {
		//// Serialize Solution class
		sol.SaveSolutionAsRaw(Common.SaveSerialFileName);

		//// De-serialize to verify there is no problem
		Solution testdes = (Solution) (Common.FU
				.DeserializeFileToObject(Common.ACFileFolderPath + Common.SaveSerialFileName));

	}
	*/

	public static String getDirectoryNameWithScoreAndDate(int scorePrefix, String prefixDate) {
		String resPath = Common.InputFileName + "_" + String.format("%010d", scorePrefix);// +
																							// "_"
																							// +
																							// prefixDate;
		return resPath;
	}

	public static void ProcessAllBackupOfSolutionToFolder(Solution sol) {
		// CheckSolution
		// CheckSolution(sol);

		// Initialize Tool
		GenerateOutput genOut = new GenerateOutput();

		////////// FOR BACK-UP ////////////
		String datePrefix = Common.getTimeAsPrefixDateFormatString();
		String TargetBKPfolderName = getDirectoryNameWithScoreAndDate((int) Math.round(sol.GetScore()), datePrefix);

		String targetBKPFolderPath = Common.ACFileFolderPath + TargetBKPfolderName;
		Common.FU.CreateDir(targetBKPFolderPath);

		// Backup serialized solution class to BKP folder
		//// sol.SaveSolutionAsRawToFullPath(targetBKPFolderPath+"\\"+
		//// Common.SaveSerialFileName);

		// Backup generated output
		genOut.GenerateOutputFileFromOutputModel(sol, targetBKPFolderPath + "\\" + Common.OutputGeneratedFileName);

		// same output file in main PROBLEM folder
		genOut.GenerateOutputFileFromOutputModel(sol, Common.OutputGeneratedFullPath);

		// Zip Sources for BKP
		UtilSrcZip appZip = new UtilSrcZip();
		appZip.ZipSourceOfProject(targetBKPFolderPath + "\\" + Common.OUTPUT_ZIP_FULLPROC_FILE_NAME);
	}

	public static void CheckSolution(Solution sol) {

		// assert( (NT>=sol.pb.L && NM>=sol.pb.L));

	}
}
