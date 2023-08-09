# Rename reports
A tool to rename report pdfs to the standard
## Setup
Java SE 17 is required to run this program. An installer for Temurin/OpenJDK 17 should be included with the file, running the installer will open a window guiding you through the installation. Leaving everything as default should work perfectly, just click through the pages. If the installer is not included, it can be downloaded from [here](https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.8%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.8_7.msi). After installing Temurin/Java 17, the `.JAR` should run just by double-clicking.
## GUI and How to Use
After double-clicking `Rename-Reports.JAR`, a window will open with a few prompts. The top of the window will have three lines prompting the user to select input and output directories and a location hierarchy spreadsheet. When the select buttons are clicked, they will open a file selection window.

The input directory should be a folder containing many directories titled as a site id followed by the site name, for example, "IE001 Ahfachkee Day School". Site ids must be either IE, IA, or JS followed by 3 numbers. Any folders or files not named to this specification inside the input directory will be ignored. Within each of these site folders should be, among other contents, a folder named EXACTLY "04 Report". If this is not there, the site directory will be skipped. Within that report folder there should be two folders, one called "Draft" and the other called "Final". Final will be checked first, if there is any PDF there it will be used, otherwise Draft will be checked and any PDF there will be used.

The output directory can be any folder. This is where all of the renamed PDFs will be copied to, and where the info text document will be made. Note that if there is a document in output called "rename-info.txt" (immediately within it, not in any sub-directories) it will be deleted and replaced by the info from the current run of this script.

The Location Hierarchy spreadsheet must be a .xlsx file, .xlsb and other similar formats will not work. This .xlsx file can be obtained by opening a .xlsb or other spreadsheet format in excel and exporting it. I don't know where the spreadsheet comes from, it was provided to me when I was writing this code, but it must have, starting in row 7, site ids in column D, site descriptions in column E, location numbers in column F, and Maximo ids in column I.

After all three files have been selected, press the 'Run' button to start the script. If things haven't been selected properly, an info text box will appear at the bottom of the window prompting file selection.
When run, the mentioned info text box will display what the script is currently doing, and, when it's done, it will direct users to the document `rename-info.txt` for more information.

`rename-info.txt` is a brief text document created every time the script is run. If any potential errors arose during the running of the script, they will be mentioned here. For example, if multiple pdfs were found in the same directory, the script will use the first one but will mention that it did so here, or if a site directory doesn't have a "04 Report" folder that will be mentioned here, too.

`MISSING YEARS` is a directory created in the user's output directory if Tesseract can't find a year on the first page of a report. In testing, this was about 10-15% of files. These will be copied to `MISSING YEARS`, renamed with 'YYYY' in place of a year. The user can then go and manually add the years in. Not perfect, but the best I could come up with.
## Troubleshooting
> `Run` isn't doing anything

Ensure that you've selected two directories and a *.xlsx file. Spreadsheets of a different type (.xlsb, .xls, .csv, etc.) will not work.

---
> Certain PDFs are being skipped

Ensure that the file structure matches exactly as described above. Files and folders not placed in the correct places will be ignored. Consult `rename-info.txt` to see what's been skipped.

---
> The year on some files is incorrect

This is possible. Because I'm using Optical Character Recognition (OCR) to pull the year off the first page of the report, the algorithm may not work perfectly and may, very occasionally, read some characters wrong. Ideally, it will catch its error, and you'll be one troubleshooting option down, but it's possible, for example, that it reads '2018' as '2016', and there's no way it can catch that. Hopefully, that never happens. In all the test files I ran this on (~160 or so), it didn't seem to happen.

---
> What's this `MISSING YEARS` folder?

Tesseract, the OCR library I'm using to pull years from the first page of the PDFs, isn't perfect. Sometimes it can't find a year. In this case, the program will copy that file, with the rest of the information it can get, into `MISSING YEARS`, and the user can manually open the PDF, check the first page, and fix that.
## Details of what it does
First, the script runs some initialization steps: creating `rename-info.txt`, opening the spreadsheet, and starting Tesseract. Then, it will navigate through the `input` directory, finding reports in '*siteId siteName*/04 Reports/Final' or '*siteId siteName*/04 Reports/Draft'. For each Report, the script will find the site ID within the location hierarchy spreadsheet and pull, from that row of the spreadsheet, the location number, maximo id, and site name. Then the script will render the first page of the PDF and run Tesseract, an optical character recognition program, on it. That algorithm will find all the text on the first page, from which the year is pulled. With all this information, the script will copy the report to a new file in the format `YEAR_SITE-ID_LOCATION#_MAXIMO-ID_Sub-SiteReport_SITE-NAME.pdf`, for example, `2021_IE009_N32-04_AB900066_Sub-SiteReport_BECLABITO DAY SCHOOL.pdf`.
## Changing the code
The `.JAR` file is compiled and compressed, meaning it is not human-readable code. If you want to change how the program works, add new features, or anything else, I have uploaded the program files to a github repository uncompiled. That github repository will also include this readme.md (and an HTML version in case you can't open the .md file), as well as the compiled `.JAR`.

The mentioned github repo is [here](https://github.com/Jaden-Unruh/Rename-Reports-Tool).
## In the Github
The folder `report.renamer` contains all of the java code as well as associated data and resource files. It does not contain all the dependency folders, though it does have maven references to them within `pom.xml` so they can be installed by other users.

All of the contents of `report.renamer` are bundled within `Report Renamer.JAR`. Users interested only in running the script and not editing it in any way should download just this JAR file.

The other two files within the github are this `README.md` and an equivalent `README.html`. They are identical, just different file types in case a user can't open a .md file.