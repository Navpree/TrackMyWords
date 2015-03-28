
	function validateForm() {
	    var artist = document.forms["songForm"]["artistName"].value.trim();
	    var title = document.forms["songForm"]["songTitle"].value.trim();
	    var album = document.forms["songForm"]["albumName"].value.trim();
	    var year = document.forms["songForm"]["releaseDate"].value.trim();
	    var lyrics = document.forms["songForm"]["songLyrics"].value.trim();

	    if (artist == null || artist == ""||
	    	title == null || title == "" || 
	    	album == null || album == "" || 
	    	year == null || year == "" || 
	    	lyrics== null || lyrics == "") {
	        alert("All fields must be filled out");
	        return false;
	    }
	}