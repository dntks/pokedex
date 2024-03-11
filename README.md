Pokedex - A simple app listing pokemons.

Programming Journal.
I started first with learning about the PokeApi, where I realized that the amount of Pokemons isn't too large.
I've decided not to add paginating, as the total results are less than 1500, but also realized,
that the simple /pokemon endpoint won't be enough for the overview page, as it also needs the pictures in addition to the id and name.
I've decided to get the url-s on the fly, when the user scroll to the Pokémon.
It's not ideal, but all the data arrives only once this way, as I planned to create a Room db for all the retrieved data.
This means that scrolling through the list basically saves all the data to the phone, and then the app can work completely offline.
After deciding on the basics, I've created the Repository, the mapped the model from the API into data classes.
This took some time, to figure out, but it seemed I'll have all the data with 2 types of API calls.
At this point I opted for storing some data as json String in the database, as it made development quicker at the moment.
After setting up the DB I started to add UI elements along with the OverviewScreen, and also realized I'll need to
create a NavGraph for easier usage of the BottomNavigation later on.
After the LazyGrid was ready to show data, I was testing around the UI and the saved data, fixing smaller issues.
Then I spent some time with the search field, I decided on using a simple TextField for the search.
Setting up the Theme and typography took some time as well, as I wasn't that familiar with how to set it up for a new project.
Up to this point I was several hours in the project, so had to hurry up, and focus on the details page, and favorites.
I decided to create a separate table for storing the favorites locally, and using the Flow of the favorites.
I only realized at this point, that I won't have enough time for the Evolution tab on the details, I wanted to finish the 
favorites first, which seemed to work after a few tweaks.
This was already towards the end of the proposed time frame, so I added a not so elegant connectivity check for the splash screen,
the menu button for the grid cards, and wrote unit tests for the Repository and the viewmodels, however I couldn't wrap everything perfectly up, 
as I already was past hours.
Room for improvement: Better internet connectivity checks could be added, error handling on network calls is definitely a must to do, some Unit tests are missing, and the missing feature of 
Evolution tab on the details page, and an overall clean up of unused or ugly code. As the time limit was pretty tight I couldn't finish all, but I hope this shows what issues I'm aware of.
