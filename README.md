# HTMLContentCreator
I needed a way to automate pulling content from spreadsheets and generate HTML from the spreadsheet data. I wrote this simple java console app that reads formatted .XLSX spreadsheets and injects their values into HTML templates to generate translated blocks of HTML content.

Right now it just runs through an IDE, next phase will create an executable or jar to just run on an end-user's machine to process their own templates.

To use the app, simply create: ContentConfigJSON.txt based off of the ContentConfigSampleJSON.txt and include a comma separated list of excel spreadsheets in the files section:

{
    "files": [
        "PathToSomeFile.xlsx",
        "PathToSomeFile.xlsx",
        "PathToSomeFile.xlsx"
    ]
}

Make sure you're following the format of ContentSample.xlsx to ensure that the app will pick up your languages and related them to pieces of content and their respective placeholder template token (%%placeholder%%).

Then include your HTML Templates with the following convention:
PathToSomeFileTemplate.html

In your templates put whatever HTML you'd want to translate with placeholders matching the first column of any data rows, this will be used to inject the translated cell data into the correct spot.

Once you run the app it'll generate whatever files are necessary to translate each template into all of the languages it has content for.
