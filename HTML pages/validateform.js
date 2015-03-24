
	function validateForm() {
	    var artist = document.forms["addSong"]["artistName"].value.trim();
	    var title = document.forms["addSong"]["songTitle"].value.trim();
	    var album = document.forms["addSong"]["albumName"].value.trim();
	    var year = document.forms["addSong"]["releaseDate"].value.trim();
	    var lyrics = document.forms["addSong"]["songLyrics"].value.trim();

	    if (artist == null || artist == ""||
	    	title == null || title == "" || 
	    	album == null || album == "" || 
	    	year == null || year == "" || 
	    	lyrics== null || lyrics == "") {
	        alert("All fields must be filled out");
	        return false;
	    }
	}