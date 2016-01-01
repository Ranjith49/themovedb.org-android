

This is a Android Application that uses the REST API exposed by the TheMovieDb Site
https://www.themoviedb.org/ [ TheMovieDb ]

Movies , TV Shows and Persons Info is retrieved


------------------------------------------------- Movie Information -----------------------------------------------------------

Configuration URL :

https://api.themoviedb.org/3/configuration?api_key=57a2fe1fb88623756080330e465f20f7

Certification List :

https://api.themoviedb.org/3/certification/movie/list?api_key=57a2fe1fb88623756080330e465f20f7


Bahubali mve -- telugu
https://api.themoviedb.org/3/search/movie?api_key=57a2fe1fb88623756080330e465f20f7&query=bahubali&language=te


List of English [Us Certified Movies] less than current date.. [All certified movies]
https://api.themoviedb.org/3/discover/movie?api_key=57a2fe1fb88623756080330e465f20f7&certification_country=US&certification.lte=NC-17&release_date.lte=20151228

Disover Movies (API calls):

a) Discover Movies [Popularity -- high and year -- desc (less than current)]

params : -- release_date.lte = <YYYYMMDD>
			sort_by = popularity.desc
			certification_country = ISO Code of Country
			certification.lte = Highest Certification possible
			
Filters : certification.lte -- List all possible certification for country( and let him select)
          include_adult -- Seperate (App level Check)
			
					  
Example :			
https://api.themoviedb.org/3/discover/movie?api_key=57a2fe1fb88623756080330e465f20f7&certification_country=US&certification.lte=NC-17&release_date.lte=20151228&sort_by=vote_average.desc

b) Discover Top Rated Movies [Vote Average high and year -- desc (less than current)]

params : -- release_date.lte = <YYYYMMDD>
			sort_by = vote_average.desc
			certification_country = ISO Code of Country
			certification.lte = Highest Certification possible
			vote_count.gte =100(hardCoded)
			
Filters : certification.lte -- List all possible certification for country( and let him select)
		  include_adult -- Seperate (App level check)
Example :			
https://api.themoviedb.org/3/discover/movie?api_key=57a2fe1fb88623756080330e465f20f7&certification_country=US&certification.lte=NC-17&release_date.lte=20151228&sort_by=vote_average.desc&vote_count.gte=100

c) Discover Upcoming movies :

params : -- sort_by=release_date.desc <YYYYMMDD>
            certification_country = ISO Code of Country
			certification.lte = Highest Certification possible
			
Filters : certification.lte -- List all possible certification for country( and let him select)
		  include_adult -- Seperate (App level check)
		  
Example:
http://api.themoviedb.org/3/discover/movie?api_key=57a2fe1fb88623756080330e465f20f7&certification_country=US&certification.lte=NC-17&sort_by=release_date.desc	

Movies (API Calls) :


a) Popular

params : language (en)

https://api.themoviedb.org/3/movie/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en


b) Now -Playing

params : language (en)

https://api.themoviedb.org/3/movie/now_playing?api_key=57a2fe1fb88623756080330e465f20f7&language=en

c) Top -Rated

params : language (en)

https://api.themoviedb.org/3/movie/top_rated?api_key=57a2fe1fb88623756080330e465f20f7&language=en
d) Upcoming

params : language(en)

https://api.themoviedb.org/3/movie/upcoming?api_key=57a2fe1fb88623756080330e465f20f7&language=en


Movie Basic Details :

https://api.themoviedb.org/3/movie/140607?api_key=57a2fe1fb88623756080330e465f20f7

Movie Add On's

a) Images  -- /movie/<id>/images
b) Videos -- /movie/<id>/videos
c) Similar -- /movie/<id>similar
d) Rating  -- /movie/<id>/rating
e) Reviews -- /movie/<id>/reviews


List of Generes and id's for Movies :

https://api.themoviedb.org/3/genre/movie/list?api_key=57a2fe1fb88623756080330e465f20f7

List of movies with particular genere :

/genre/id/movies 

params : a) id of the genere
         b) language -ISO langauge
		     c) include_all_movies - true/false (i.e logic to exclude 10 below ratings movie)
		     d) include_adult -- true/false

https://api.themoviedb.org/3/genre/28/movies?api_key=57a2fe1fb88623756080330e465f20f7&language=en


Movie Cast and Crew :

/movie/id/credits 

https://api.themoviedb.org/3/movie/140607/credits?api_key=57a2fe1fb88623756080330e465f20f7


Movie Images :

/movie/id/images

params : a) langauge -- ISO langauge

https://api.themoviedb.org/3/movie/140607/images?api_key=57a2fe1fb88623756080330e465f20f7&langauge=en


Movie Videos :

/movie/id/videos 

params : a) langauge -- ISO langauge

https://api.themoviedb.org/3/movie/140607/videos?api_key=57a2fe1fb88623756080330e465f20f7&langauge=en
We will use Youtube for now.


Movie Similar :

params : a) langauge -- ISO langauge
		 b) page -- number

https://api.themoviedb.org/3/movie/140607/similar?api_key=57a2fe1fb88623756080330e465f20f7&langauge=en
Pagination is supported by page number

Movie Reviews :

params : a) langauge -- ISO langauge
		 b) page -- number
		 
		 
https://api.themoviedb.org/3/movie/140607/reviews?api_key=57a2fe1fb88623756080330e465f20f7&langauge=en
Pagination is supported by page number


Appending Example ;

Appending the Movie basic responsw with credits ,images and Videos.

https://api.themoviedb.org/3/movie/140607?api_key=57a2fe1fb88623756080330e465f20f7&append_to_response=credits,images,videos


------------------------------------------------------Tv Shows Information ------------------------------------------------


a) Top Rated Tv Shows

http://api.themoviedb.org/3/tv/top_rated?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1

b) Popular TV Shows

http://api.themoviedb.org/3/tv/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1

c) On the Air SHows 

http://api.themoviedb.org/3/tv/on_the_air?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1
		 
		 
----------------------------------------------------- People Information-----------------------------------------

a)List of People API 

http://api.themoviedb.org/3/person/popular?api_key=57a2fe1fb88623756080330e465f20f7&language=en&page=1


