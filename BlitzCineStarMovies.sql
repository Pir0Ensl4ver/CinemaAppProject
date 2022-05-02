CREATE DATABASE [BlitzCineStarMovies]
GO
USE [BlitzCineStarMovies]
GO

CREATE TABLE [User] (
	[IDUser] INT IDENTITY(1,1) NOT NULL,
	[Name] NVARCHAR(30),
	[Password] NVARCHAR(64),
	[Type] INT,
	CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED ([IDUser])
)

CREATE TABLE [Person] (
	[IDPerson] int IDENTITY(1,1) NOT NULL,
	[FirstName] nvarchar(30),
	[LastName] nvarchar(30),
	CONSTRAINT [PK_Person] PRIMARY KEY CLUSTERED ([IDPerson])
)

CREATE TABLE [Genre] (
	[IDGenre] INT IDENTITY(1,1) NOT NULL,
	[Name] NVARCHAR(50),
	CONSTRAINT [PK_Genre] PRIMARY KEY CLUSTERED ([IDGenre])
)

CREATE TABLE [Movie] (
	[IDMovie] INT IDENTITY(1,1) NOT NULL,
	[Title] NVARCHAR(150),
	[OriginalTitle] NVARCHAR(150),
	[Link] NVARCHAR(200),
	[Description] NVARCHAR(1024),
	[PicturePath] NVARCHAR(100),
	[PublishedDate] NVARCHAR(30),
	[Duration] INT,
	[Year] INT,
	[GenreID] INT NOT NULL,
	CONSTRAINT [PK_Movie] PRIMARY KEY CLUSTERED ([IDMovie]),
	CONSTRAINT [FK_Movie_Genre] FOREIGN KEY([GenreID]) REFERENCES [Genre]([IDGenre])
)



CREATE TABLE [MovieActor] (
	[IDMovieActor] INT IDENTITY(1,1) NOT NULL,
	[PersonID] INT NOT NULL,
	[MovieID] INT NOT NULL,
	CONSTRAINT [PK_MovieActor] PRIMARY KEY CLUSTERED ([IDMovieActor]),
	CONSTRAINT [FK_MovieActor_Movie] FOREIGN KEY([MovieID]) REFERENCES [Movie]([IDMovie]),
	CONSTRAINT [FK_MovieActor_Person] FOREIGN KEY([PersonID]) REFERENCES [Person]([IDPerson])
)

CREATE TABLE [MovieDirector] (
	[IDMovieDirector] INT IDENTITY(1,1) NOT NULL,
	[PersonID] INT NOT NULL,
	[MovieID] INT NOT NULL,
	CONSTRAINT [PK_MovieDirector] PRIMARY KEY CLUSTERED ([IDMovieDirector]),
	CONSTRAINT [FK_MovieDirector_Movie] FOREIGN KEY([MovieID]) REFERENCES [Movie]([IDMovie]),
	CONSTRAINT [FK_MovieDirector_Person] FOREIGN KEY([PersonID]) REFERENCES [Person]([IDPerson])
)
GO

-- USER PROCEDURES

CREATE PROCEDURE createUser
	@Name NVARCHAR(30),
	@Password NVARCHAR(64),
	@Type INT,
	@ID INT OUTPUT
AS 
BEGIN 
	IF NOT EXISTS (SELECT IDUser FROM [User] WHERE Name = @Name) 
	BEGIN
		INSERT INTO [User] VALUES(@Name, @Password, @Type)
		SET @ID = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @ID=IDUser FROM [User] WHERE Name = @Name
	END
END
GO


CREATE PROCEDURE updateUser
	@Name NVARCHAR(30),
	@Password NVARCHAR(64),
	@Type INT,
	@IDUser INT
	 
AS 
BEGIN 
	UPDATE [User] SET 
		Name = @Name,
		Password = @Password,
		Type = @Type
	WHERE 
		IDUser = @IDUser
END
GO


CREATE PROCEDURE deleteUser
	@IDUser INT	 
AS 
BEGIN 
	DELETE  
	FROM 
			[User]
	WHERE 
		IDUser = @IDUser
END
GO

CREATE PROCEDURE selectUser
	@IDUser INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		[User]
	WHERE 
		IDUser = @IDUser
END
GO

CREATE PROCEDURE selectUsers
AS 
BEGIN 
	SELECT * FROM [User]
END
GO
-- ADMIN INSERT

INSERT INTO [User] VALUES('eternal', 'suffering', 2)


GO

-- PERSON PROCEDURES

CREATE PROCEDURE createPerson
	@FirstName NVARCHAR(30),
	@LastName NVARCHAR(30),
	@ID INT OUTPUT
AS 
BEGIN 
	IF NOT EXISTS (SELECT IDPerson FROM Person WHERE FirstName = @FirstName AND LastName = @LastName) 
	BEGIN
		INSERT INTO Person VALUES(@FirstName, @LastName)
		SET @ID = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @ID=IDPerson FROM Person WHERE FirstName = @FirstName AND LastName = @LastName
	END
END
GO


CREATE PROCEDURE updatePerson
	@FirstName NVARCHAR(30),
	@LastName NVARCHAR(30),
	@IDPerson INT
	 
AS 
BEGIN 
	UPDATE Person SET 
		FirstName = @FirstName,
		LastName = @LastName
	WHERE 
		IDPerson = @IDPerson
END
GO


CREATE PROCEDURE deletePerson
	@IDPerson INT	 
AS 
BEGIN 
	DELETE  
	FROM 
			Person
	WHERE 
		IDPerson = @IDPerson
END
GO

CREATE PROCEDURE selectPerson
	@IDPerson INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Person
	WHERE 
		IDPerson = @IDPerson
END
GO

CREATE PROCEDURE selectPersons
AS 
BEGIN 
	SELECT * FROM Person
END
GO


-- GENERE PROCEDURES

CREATE PROCEDURE createGenre
	@Name NVARCHAR(50),
	@ID INT OUTPUT
AS 
BEGIN 
	IF NOT EXISTS (SELECT IDGenre FROM Genre WHERE Name = @Name) 
	BEGIN
		INSERT INTO Genre VALUES(@Name)
		SET @ID = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @ID=IDGenre FROM Genre WHERE Name = @Name
	END
END
GO


CREATE PROCEDURE updateGenre
	@Name NVARCHAR(50),
	@IDGenre INT
	 
AS 
BEGIN 
	UPDATE Genre SET 
		Name = @Name	
	WHERE 
		IDGenre = @IDGenre
END
GO


CREATE PROCEDURE deleteGenre
	@IDGenre INT	 
AS 
BEGIN 
	DELETE  
	FROM 
			Genre
	WHERE 
		IDGenre = @IDGenre
END
GO

CREATE PROCEDURE selectGenre
	@IDGenre INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Genre
	WHERE 
		IDGenre = @IDGenre
END
GO

CREATE PROCEDURE selectGenres
AS 
BEGIN 
	SELECT * FROM Genre
END

GO

-- ACTOR PROCEDURES

CREATE PROCEDURE createActor
	@PersonID INT,
	@MovieID INT,
	@ID INT OUTPUT
AS 
BEGIN 
	IF NOT EXISTS (SELECT IDMovieActor FROM MovieActor WHERE PersonID = @PersonID AND MovieID = @MovieID) 
	BEGIN
		INSERT INTO MovieActor VALUES(@PersonID, @MovieID)
		SET @ID = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @ID=IDMovieActor FROM MovieActor WHERE PersonID = @PersonID AND MovieID = @MovieID
	END
END
GO


CREATE PROCEDURE updateActor
	@PersonID INT,
	@MovieID INT,
	@IDActor INT OUTPUT
	 
AS 
BEGIN 
	UPDATE MovieActor SET 
		PersonID = @PersonID,
		MovieID = @MovieID
	WHERE 
		IDMovieActor = @IDActor
END
GO


CREATE PROCEDURE deleteActor
	@IDActor INT	 
AS 
BEGIN 
	DELETE  
	FROM 
			MovieActor
	WHERE 
		IDMovieActor = @IDActor
END
GO

CREATE PROCEDURE selectActor
	@IDActor INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		MovieActor
	WHERE 
		IDMovieActor = @IDActor
END
GO

CREATE PROCEDURE selectActors
AS 
BEGIN 
	SELECT * FROM MovieActor
END
GO

CREATE PROCEDURE selectActorByMovie
	@IDMovie INT
AS 
BEGIN 
	SELECT p.* FROM MovieActor AS m JOIN Person as p on p.IDPerson = m.PersonID 
		WHERE m.MovieID = @IDMovie
END
GO

-- DIRECTOR PROCEDURES

CREATE PROCEDURE createDirector
	@PersonID INT,
	@MovieID INT,
	@ID INT OUTPUT
AS 
BEGIN 
	IF NOT EXISTS (SELECT IDMovieDirector FROM MovieDirector WHERE PersonID = @PersonID AND MovieID = @MovieID) 
	BEGIN
		INSERT INTO MovieDirector VALUES(@PersonID, @MovieID)
		SET @ID = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @ID=IDMovieDirector FROM MovieDirector WHERE PersonID = @PersonID AND MovieID = @MovieID
	END
END
GO


CREATE PROCEDURE updateDirector
	@PersonID INT,
	@MovieID INT,
	@IDDirector INT OUTPUT
	 
AS 
BEGIN 
	UPDATE MovieDirector SET 
		PersonID = @PersonID,
		MovieID = @MovieID
	WHERE 
		IDMovieDirector = @IDDirector
END
GO


CREATE PROCEDURE deleteDirector
	@IDDirector INT	 
AS 
BEGIN 
	DELETE  
	FROM 
			MovieDirector
	WHERE 
		IDMovieDirector = @IDDirector
END
GO

CREATE PROCEDURE selectDirector
	@IDDirector INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		MovieDirector
	WHERE 
		IDMovieDirector = @IDDirector
END
GO

CREATE PROCEDURE selectDirectors
AS 
BEGIN 
	SELECT * FROM MovieDirector
END
GO

CREATE PROCEDURE selectDirectorByMovie
	@IDMovie INT
AS 
BEGIN 
	SELECT p.* FROM MovieDirector AS m JOIN Person as p on p.IDPerson = m.PersonID 
		WHERE m.MovieID = @IDMovie
END
GO

-- MOVIE PROCEDURES

CREATE PROCEDURE createMovie
	@Title NVARCHAR(150),
	@OriginalTitle NVARCHAR(150),
	@Link NVARCHAR(200),
	@Description NVARCHAR(1024),
	@PicturePath NVARCHAR(100),
	@PublishedDate NVARCHAR(30),
	@Duration INT,
	@Year INT,
	@GenreID INT,
	@ID INT OUTPUT
AS 
BEGIN 
		INSERT INTO Movie VALUES(@Title,@OriginalTitle,@Link,@Description,@PicturePath,@PublishedDate,@Duration,@Year,@GenreID)
		SET @ID = SCOPE_IDENTITY()
END
GO


CREATE PROCEDURE updateMovie
	@Title NVARCHAR(150),
	@OriginalTitle NVARCHAR(150),
	@Link NVARCHAR(200),
	@Description NVARCHAR(1024),
	@PicturePath NVARCHAR(100),
	@PublishedDate NVARCHAR(30),
	@Duration INT,
	@Year INT,
	@GenreID INT,
	@IDMovie INT OUTPUT
	 
AS 
BEGIN 
	UPDATE Movie SET 
		Title = @Title,
		OriginalTitle = @OriginalTitle,
		Link = @Link,
		[Description] = @Description,
		PicturePath = @PicturePath,
		PublishedDate = @PublishedDate,
		Duration = @Duration,
		[Year] = @Year,
		GenreID = @GenreID
	WHERE 
		IDMovie = @IDMovie
END
GO


CREATE PROCEDURE deleteMovie
	@IDMovie INT	 
AS 
BEGIN 
	DELETE FROM MovieActor
	WHERE MovieID = @IDMovie

	DELETE FROM MovieDirector
	WHERE MovieID = @IDMovie
		
	DELETE FROM Movie
	WHERE IDMovie = @IDMovie
END
GO

CREATE PROCEDURE selectMovie
	@IDMovie INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Movie
	WHERE 
		IDMovie = @IDMovie
END
GO

CREATE PROCEDURE selectMovies
AS 
BEGIN 
	SELECT * FROM Movie
END
GO

CREATE PROCEDURE deleteMovies
AS 
BEGIN 
	DELETE FROM MovieActor
	DELETE FROM MovieDirector
	DELETE FROM Person
	DELETE FROM Movie
	DELETE FROM Genre
END
GO


CREATE PROCEDURE deleteDirectorsByMovie
	@IDMovie INT	 
AS 
BEGIN 
	DELETE FROM MovieDirector
	WHERE MovieID = @IDMovie
END
GO

CREATE PROCEDURE deleteActorsByMovie
	@IDMovie INT	 
AS 
BEGIN 
	DELETE FROM MovieActor
	WHERE MovieID = @IDMovie
END


