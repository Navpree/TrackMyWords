CREATE TABLE IF NOT EXISTS Song
(
  song_id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  lyrics TEXT NOT NULL,
  release_date date NOT NULL
) ENGINE = MyISAM;

ALTER TABLE Song ADD INDEX(song_id), ADD FULLTEXT INDEX(lyrics);

CREATE TABLE IF NOT EXISTS Artist
(
	artist_id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    artist_name VARCHAR(255) NOT NULL
) ENGINE MyISAM;

ALTER TABLE Artist ADD INDEX(artist_id);

CREATE TABLE IF NOT EXISTS Album
(
	album_id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    cover BLOB,
    release_date date NOT NULL
) ENGINE MyISAM;

ALTER TABLE Album ADD INDEX(album_id);

CREATE TABLE IF NOT EXISTS Song_Album
(
	song_album_id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    song_id int(5) NOT NULL,
    album_id int(5) NOT NULL,
    CONSTRAINT fk_song_id FOREIGN KEY(song_id) REFERENCES Song(song_id),
    CONSTRAINT fk_album_id FOREIGN KEY(album_id) REFERENCES Album(album_id)
) ENGINE MyISAM;

CREATE TABLE IF NOT EXISTS Artist_Album
(
	artist_album_id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    album_id int(5) NOT NULL,
    artist_id int(5) NOT NULL,
    CONSTRAINT fk_album_id FOREIGN KEY(album_id) REFERENCES Album(album_id),
    CONSTRAINT fk_artist_id FOREIGN KEY(artist_id) REFERENCES Artist(artist_id)
) ENGINE MyISAM;

