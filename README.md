CellStore
=========

CellStore is a database for large sets of single-cell RNA-seq data. It is aimed to be a backend for other processes, such as visualizing the
data on a website, but also to distribute annotation between users. It can be accessed from Python and R, as well as shell scripts for
integration in a sequencing pipeline.

Implementation
--------------

The count data is stored as AnnData HDF5 files. This allows quick access to subsets of the data. It is stored in compressed chunks which
reduces memory requirements for sparse data.



Running
-------

This software is not ready for non-developers yet.

For eclipse users
-----------------

Something similar to this should be added to VM arguments when you run the code:

-Djava.library.path=/home/mahogny/javaproj/cellstore/hdf



TODO list
---------


* gradient color panel in 2d viewer. show highest level value as text on it
** histogram on the gradient. low priority

* function to save user database as json
* user tab, add "create user" function
** this is a new JDialog with fields. if written right, can be used to edit user info as well

* tab: cellsets - add delete button

* add tab: clusterings
**	delete button

* add tab: dimension reductions
** delete button
** double click on dimred to open cluster viewer (needs major cleanup)
