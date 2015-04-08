select Song.song_id, Song.title, Song.lyrics, Song.release_date, Album.title, Artist.artist_name 
from Song
INNER JOIN Song_Album on Song.song_id = Song_Album.song_id
INNER JOIN Album on Song_Album.album_id = Album.album_id
INNER JOIN Artist_Album on Album.album_id = Artist_Album.album_id
INNER JOIN Artist on Artist_Album.artist_id = Artist.artist_id
group by Song.song_id;