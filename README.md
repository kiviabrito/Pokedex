# Pokédex Android App em Kotlin

## Functionality

The Pokédex is an electronic device designed to catalogue and provide information regarding the various species of Pokémon.
With this app you can search a Pokemon by name or id and see details of the Pokemon as description, photos, abilities, types, category and stats.

## Structure:
Some os the components used are:

- MVVM Architecture.
- Room DataBase - for local caching.
- RxJava - for tasks on the background, as network calls and access to database.
- Retrofit - for the network calls.
- Gson - for the serialization.
- Glide - for image loading and cache.
- RecyclerView - for displaying list of Pokemon and Photos.
- ConstraintLayout - for the layout design.
- CardView - for the layout design.
- SearchView with AutoComplete - for the search of the Pokemon by name or id.


## Models:
Three different models were used in this project for the API calls:

- [ ] https://pokeapi.co/api/v2/pokemon/{id}/ - >
- PokemonInfo - with high level information about the Pokemon with the id = {id}

- [ ] https://pokeapi.co/api/v2/pokemon-species/{id}/ - >
- SpeciesInfo - with the information about the specie of Pokemon with the id = {id}

- [ ] https://pokeapi.co/api/v2/ability/{path} ->
- AbilityInfo - with the information about the ability with the path = {path}

Retrofit + Gson are working together to serialize the Json, response from the API call, to those 3 models described above.
For the database cashing it is using another model called "PokemonEntity", which contain relevante information to run the app on the offline mode.

## Views:
For the view it is using one Activity, which is hosting two fragments (MainFragment and DetailsFragment).
- MainFragment : 

It is displaying the list of Pokemon(picture, types, id and name) and a search button.


![alt text](https://github.com/kiviabrito/Pokedex/blob/master/Screenshot_MainFragment.png) 

- DetailsFragment :

It is displaying the Pokemon information.


![alt text](https://github.com/kiviabrito/Pokedex/blob/master/Screenshot_DetailsFragment.png) 

## Executar o app

No update is necessary, you can just clone the project and run it.
#### Note: For the app work on the offiline mode it is necessary that the user has had at least one access with the internet connected, so it can fetch the data from the network and store it on the local data base.

