#Content Grabber

.Net Tool for grabbing and parsing lyrics from Metrolyrics.
Built for Windows x86.

Available Parameters:
* `-tasks` - Int32 value, sets the number of active threads for pulling/parsing lyric data
* `-clean` - Boolean value, when true will recursively delete the grabs directory

Example Usage:
`ContentGrabber -tasks:3 -clean:true`