# Synthetic Data Generator
This tool the SDG (Synthetic Data Generator). I developed using Java v8.x.x and Maven v3.6.x.
 In this repo you can find the binary which is ready to use. (This binary includes, all required dependencies. So no additional headache for configuration, compilation/built to run it)

### Steps to Build and Run the source code:

Step-1. Checkout the code from my git repo Or download the zip file.

Step-2. Open your terminal/command line and go to the root folder 'SyntheticDataGenerator', where we have pom.xml.

Step-3. Run `mvn clean install’ command

Step-4. Your executable jar file will generate in your SDG folder (path: /out/artifacts/SDG).

Step-5. To run your project use command 'java -jar <file.jar>'

Note: If you found any issue to download the maven dependency. Please check your proxy and internet setting.

### How does the binary work?
Follow below the steps:

Step-1 Inside the SDG folder, you can see a SDG.bat/SDG.sh file.

Step-2 Double click SDG.batch if you are running on Windows. Or double click SDG.sh file if you are running on Mac OS.

Step-3 Terminal/Command line will popup. It will ask you to provide input as number of record you want to generate.

Step-4 After Step-3, it will ask to provide the frequency of bad data record (For example: with each 20 rows)

Step-5 Done. Go back to your SDG folder. You will see a 'output.csv' file and that is your data what you want. 
Additional you can see a file with name (~chearSheetForBadData.csv) which has rows and column numbers, the position of 'bad data' in your actual file.