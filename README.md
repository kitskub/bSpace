## [bSpace on BukkitDev](http://dev.bukkit.org/server-mods/bSpace/pages/)
## [bSpace Jenkins (builds automatically after a commit!)](http://ci.chrisgward.com/job/bSpace/)

NOTE: We'll still keep the target/-directory. Why? You never know when Jenkins takes a nap.

Coding and Pull Request Conventions
-----------

* We generally follow the Sun/Oracle coding standards.
* No tabs; use 4 spaces instead (usually).
* Organize imports properly.
* If you make major changes to a class, add an author-tag.
* No 80 column limit or midstatement newlines (usually).
* No default Eclipse formatting.
* Proper javadoc for each method added/changed to describe what it does.
* The number of commits in a pull request should be kept to a minimum (squish them into one most of the time - use common sense!).
* No merges should be included in pull requests unless the pull request's purpose is a merge.
* Pull requests should be tested (does it compile? AND does it work?) before submission.

Follow the above conventions if you want your pull requests accepted. If your pull request is accepted, you will be added to the plugin.yml authors-list.