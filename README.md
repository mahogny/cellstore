CellStore
=========

CellStore is a database for large sets of single-cell RNA-seq data. It is aimed to be a back-end for other
processes, such as visualizing the data on a website, but also to distribute annotation between users. It
can be accessed from Python and R, as well as shell scripts for integration in a sequencing pipeline.

Implementation
--------------

The count data is stored as AnnData HDF5 files. This allows quick access to subsets of the data.
It is stored in compressed chunks which reduces memory requirements for sparse data.

No SQL server is required as we have our own query-optimized storage strategies. In our experience
this uses by far less memory as naive SQL schemas have a large amount of overhead for storing
matrice-like data. It also makes it easy to deploy CellStore on a regular computer.



Running
-------

This software is not ready for non-developers yet. If you want to use it, please let us know: mahogny@areta.org

preliminary for unix:

To compile, first execute:
> make jar

Then to run, execute:
> java -jar cellstore.jar

TODO port windows and OSX



CellStore on your website
-------------------------

With Java WebStart being deprecated in JDK9, we will not be offering such a solution.

We are looking for volunteers who want to make a read-only version of the system, leaving the edit functions to the java client.
It's possible that server-side rendering of the projections will be the best solution.



For eclipse users
-----------------

Something similar to this should be added to VM arguments (run configuration) when you run the code:

-Djava.library.path=/home/..../cellstore/hdf



TODO list
---------


* gradient color panel in 2d viewer. show highest level value as text on it
** histogram on the gradient. low priority

* function to save user database as json
* user tab, add "create user" function
** this is a new JDialog with fields. if written right, can be used to edit user info as well

* tab: cellsets - add delete button

* tab: dimension reductions
** delete button
** double click on dimred to open cluster viewer (needs major cleanup)

* favourite genes. see db/todo/
** load/save in json
** include drop-down menu in projection view
** projection view, in menu, option to add current gene

* violin or jitter (easiest) plots of gene levels, for each cluster, in a panel below in projection view
** may need a horizontal scrollbar
** button to turn on/off
